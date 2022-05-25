package com.example.conferenciaestorno;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.model.TipoLancamentoEnum;
import com.example.conferenciaestorno.domain.repository.LancamentoRepository;

@Configuration
public class MainRunner implements CommandLineRunner {

	@Autowired
	LancamentoRepository lancamentoRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Main Runner");
		testeAmbiente();

	}

	private void testeAmbiente() {

		Optional<Lancamento> result = lancamentoRepository.findById(1L);

		System.out.println("Buscando Lancamento com id = 1");
		if (result.isPresent()){
			System.out.println(result.get());
		} else {
			System.out.println("Lançamento id não encontrado");
		}

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

		Lancamento novoLancamento = new Lancamento();

		novoLancamento.setCnpjCpf("teste");
		novoLancamento.setPedido("teste");
		novoLancamento.setTipoLancamento(TipoLancamentoEnum.ESTORNO);

		lancamentoRepository.save(novoLancamento);

		System.out.println("Buscando Lancamento com Pedido = teste");
		List<Lancamento> results3 = lancamentoRepository.findAllByPedido("teste");
		results3.stream().forEach((Lancamento lancamento) -> {
			System.out.println(lancamento);
		});

	}

}
