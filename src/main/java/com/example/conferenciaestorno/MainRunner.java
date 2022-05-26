package com.example.conferenciaestorno;

import java.io.FileInputStream;
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
		testeAbrirPlanilha();
	}
		//Ler planilha e importar no banco de dados
		
		//iniciando informando qual o arquivo será utilizado, 
		//criar uma forma de já duplicar o arquivo e trabalhar com o arquivo recem duplicado. 
		//Assim nao preciso ficar manualmente apagando e renomeando o arquivo. 
		
		//gerar 1ª aba contendo apenas os pedidos (contratos)
		//gerar 2ª aba contendo apenas os estornos
		//gerar 3ª aba contendo os agrupamentos de pedidos e cnpj_cpf
		//gerar 4ª aba contendo os pedidos sem estorno
		//gerar 5ª aba contendo os estornos sem pedido	}

	private void testeAmbiente() {

		Optional<Lancamento> result = lancamentoRepository.findById(1L);

		System.out.println("Buscando Lancamento com id = 1");
		if (result.isPresent()) {
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

	private void testeAbrirPlanilha() throws FileNotFoundException, IOException {		
		InputStream input = getClass().getResourceAsStream("/teste.xlsx");
		Workbook wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				System.out.println(cell.getStringCellValue());
			}
		}
	}

	/*
	 * for (Row row : sheet) { for (Cell cell : row) { // Do something here } }
	 */

}
