package com.example.conferenciaestorno.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	@Autowired
	LancamentoRepository lancamentoRepository;
	
	
	public Lancamento salvar (Lancamento lancamento) {
		
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento buscarPorId(long id) {
		return lancamentoRepository.findById(null).orElseThrow(()->new RuntimeException("Lancamento não encontrado"));		
	}
	
	public List<String> buscarDistinctPedidos(){
		return lancamentoRepository.findDistinctPedidos();		
	}
}
