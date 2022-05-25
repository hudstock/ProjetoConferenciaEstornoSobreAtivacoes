package com.example.conferenciaestorno.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.conferenciaestorno.domain.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	
	List<Lancamento> findAllByPedido(String pedido);
	List<Lancamento> findAllByCnpjCpf(String pedido);

}
