package com.example.conferenciaestorno;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.repository.LancamentoRepository;

@Configuration
public class MainRunner implements CommandLineRunner{
	
	@Autowired
	LancamentoRepository lancamentoRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Main Runner");
		
		Optional<Lancamento> result = lancamentoRepository.findById(1L);
		
		System.out.println("Buscando Lancamento com id = 1");
		System.out.println(result.get());
		
		System.out.println("Buscando Lancamento com Pedido = 1");
		List<Lancamento> results = lancamentoRepository.findAllByPedido("1");		
		results.stream().forEach((Lancamento lancamento) -> {
		    System.out.println(lancamento);
		});
		
		System.out.println("Buscando Lancamento com Pedido = 1");
		List<Lancamento> results2 = lancamentoRepository.findAllByCnpjCpf("456");		
		results2.stream().forEach((Lancamento lancamento) -> {
		    System.out.println(lancamento);
		});

	}

}
