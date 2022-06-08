package com.example.conferenciaestorno.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.model.Resultado;
import com.example.conferenciaestorno.domain.repository.LancamentoRepository;
import com.example.conferenciaestorno.domain.repository.ResultadoProjection;

@Service
public class LancamentoService {
	
	@Autowired
	LancamentoRepository lancamentoRepository;
	
	@Autowired
	ResultadoService resultadoService;
	
	
	
	public Lancamento salvar (Lancamento lancamento) {		
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento buscarPorId(long id) {
		return lancamentoRepository.findById(null).orElseThrow(()->new RuntimeException("Lancamento não encontrado"));		
	}
	
	public List<Lancamento> buscarTodosPorPedido(String pedido) {
		return lancamentoRepository.findAllByPedidoOrderByTipoLancamento(pedido);
	}
	
	public List<String> buscarDistinctPedidos(){
		return lancamentoRepository.findDistinctPedidos();		
	}
	
	public void calcularResultadoCruzamento() {
		List<String> totalPedidos = lancamentoRepository.findDistinctPedidos();
		System.out.println("Total de registros: " + totalPedidos.size());	

		for (String pedido : totalPedidos) {
			System.out.println("Calculano resultado para o pedido:"+pedido);
			ResultadoProjection resultadoCalculo = lancamentoRepository.findResultado(pedido);

			Resultado resultado = new Resultado();
			resultado.setPedido(resultadoCalculo.getPedido());
			resultado.setTotalContrato(resultadoCalculo.getTotalContrato());
			resultado.setTotalEstorno(resultadoCalculo.getTotalEstorno());
			resultado.setDataContrato(resultadoCalculo.getDataContrato());
			resultado.setDataEstorno(resultadoCalculo.getDataEstorno());
			resultado.setDiferencaData(resultadoCalculo.getDiferencaData());
			resultado.setDiferencaValor(resultadoCalculo.getDiferencaValor());
			System.out.println(resultado);
			resultadoService.salvar(resultado);
		}
	}
	
}
