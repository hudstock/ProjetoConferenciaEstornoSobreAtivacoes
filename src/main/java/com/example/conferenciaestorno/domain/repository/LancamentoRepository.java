package com.example.conferenciaestorno.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.model.TipoLancamentoEnum;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findAllByPedidoOrderByTipoLancamento(String pedido);

	List<Lancamento> findAllByCnpjCpf(String pedido);

	List<Lancamento> findAllByTipoLancamento(TipoLancamentoEnum tipoLancamento);

	@Query(value = "select DISTINCT pedido \n"
			+ "from lancamento where pedido in\n"
			+ "	(select pedido from\n"
			+ "		(select tipo_lancamento,pedido,sum(valor) \n"
			+ "		from lancamento \n"
			+ "		group by pedido, tipo_lancamento \n"
			+ "		order by pedido, tipo_lancamento) tabela\n"
			+ "	group by pedido \n"
			+ "	having COUNT(*)>1)", nativeQuery = true)
	List<String> findDistinctPedidos();
	
	

	@Query(value = "select pedido, contrato.total total_contrato, contrato.data data_contrato, estorno.total total_estorno, estorno.data data_estorno,\n"
			+ "contrato.total-estorno.total as diferenca_valor,\n"
			+ "DATEDIFF (estorno.data, contrato.data) as diferenca_data\n"
			+ "from \n"
			+ "(select pedido, sum(valor) total, MIN(data_referencia) as data \n"
			+ "from lancamento\n"
			+ "where tipo_lancamento = 0 and pedido = ?1) as contrato ,\n"
			+ "(select sum(valor) total, Max(data_referencia) as data \n"
			+ "from lancamento\n"
			+ "where tipo_lancamento = 1 and pedido = ?1) as estorno", nativeQuery = true)
	ResultadoProjection findResultado(String pedido);
}
