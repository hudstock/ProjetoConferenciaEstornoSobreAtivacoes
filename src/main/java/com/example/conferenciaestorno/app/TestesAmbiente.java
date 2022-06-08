package com.example.conferenciaestorno.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.model.TipoLancamentoEnum;
import com.example.conferenciaestorno.domain.repository.LancamentoRepository;

public class TestesAmbiente {
	
	@Autowired
	LancamentoRepository lancamentoRepository;

	
	public void testeAbrirPlanilha() throws FileNotFoundException, IOException {
		InputStream input = getClass().getResourceAsStream("/teste.xlsx");
		Workbook wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				System.out.println(cell.getStringCellValue());
			}
		}
	}
	
	private void testeBuscarLancamentosPorTipo() {
		List<Lancamento> result = lancamentoRepository.findAllByTipoLancamento(TipoLancamentoEnum.CONTRATO);
		System.out.println(result.size());
		result = lancamentoRepository.findAllByTipoLancamento(TipoLancamentoEnum.ESTORNO);
		System.out.println(result.size());
	}
	
	private void testeAmbiente() {

		Optional<Lancamento> result = lancamentoRepository.findById(1L);

		System.out.println("Buscando Lancamento com id = 1");
		if (result.isPresent()) {
			System.out.println(result.get());
		} else {
			System.out.println("Lançamento id não encontrado");
		}

		System.out.println("Buscando Lancamento com Pedido = 1");
		List<Lancamento> results = lancamentoRepository.findAllByPedidoOrderByTipoLancamento("1");
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
		List<Lancamento> results3 = lancamentoRepository.findAllByPedidoOrderByTipoLancamento("teste");
		results3.stream().forEach((Lancamento lancamento) -> {
			System.out.println(lancamento);
		});
	}
}
