package com.example.conferenciaestorno;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
		// testeAmbiente();
		// testeAbrirPlanilha();
		lerPlanilha();

	}
	// Ler planilha e importar no banco de dados

	// iniciando informando qual o arquivo será utilizado,
	// criar uma forma de já duplicar o arquivo e trabalhar com o arquivo recem
	// duplicado.
	// Assim nao preciso ficar manualmente apagando e renomeando o arquivo.

	// gerar 1ª aba contendo apenas os pedidos (contratos)
	// gerar 2ª aba contendo apenas os estornos
	// gerar 3ª aba contendo os agrupamentos de pedidos e cnpj_cpf
	// gerar 4ª aba contendo os pedidos sem estorno
	// gerar 5ª aba contendo os estornos sem pedido }

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

	public void lerPlanilha() throws IOException {
		InputStream input = getClass().getResourceAsStream("/Arquivo.xlsx");
		Workbook wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> linhaIterator = sheet.iterator();
		int contador = 1;
		while (linhaIterator.hasNext()) {
			Row row = linhaIterator.next();
			if (row.getRowNum() >= 1) {
				lerLinha(row);
				contador++;
			}
		}
		System.out.println("Total de Registros: " + contador);
		wb.close();
	}

	private void lerLinha(Row row) {
		String mesReferencia = "";
		String pedido = "";
		String plano = "";
		String CnpjCpf = "";
		String motivoObservacao = "";
		BigDecimal valor = BigDecimal.ZERO;
		String mercadoContrato = "";
		String indicador = "";
		TipoLancamentoEnum tipoLancamento;
		Cell cell = row.getCell(0);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC)
				mesReferencia = formatarData(cell.getDateCellValue());
			if (type == CellType.STRING)
				mesReferencia = cell.getStringCellValue();
		}
		cell = row.getCell(5);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC) {
				int result = (int) Math.round(cell.getNumericCellValue());
				pedido = String.valueOf(result);
			} else {
				pedido = cell.getStringCellValue();
			}
		}
		cell = row.getCell(7);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC)
				plano = String.valueOf(cell.getNumericCellValue());
			if (type == CellType.STRING)
				plano = cell.getStringCellValue();
		}
		cell = row.getCell(9);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC)
				CnpjCpf = String.valueOf(cell.getNumericCellValue());
			if (type == CellType.STRING)
				CnpjCpf = cell.getStringCellValue();
		}
		cell = row.getCell(11);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC)
				motivoObservacao = String.valueOf(cell.getNumericCellValue());
			if (type == CellType.STRING)
				motivoObservacao = cell.getStringCellValue();
		}
		cell = row.getCell(13);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC)
				valor = BigDecimal.valueOf(cell.getNumericCellValue());
		}
		if (valor == BigDecimal.ZERO) {
			cell = row.getCell(14);
			if (cell != null) {
				CellType type = cell.getCellType();
				if (type == CellType.NUMERIC)
					valor = BigDecimal.valueOf(cell.getNumericCellValue());
			}
			if (valor == BigDecimal.ZERO) {
				System.out.println("Valor não encontrado");
			}
		}
		cell = row.getCell(15);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC) {
				int result = (int) Math.round(cell.getNumericCellValue());
				mercadoContrato = String.valueOf(result);
			} else {
				mercadoContrato = cell.getStringCellValue();
			}
		}

		cell = row.getCell(18);
		if (cell != null) {
			CellType type = cell.getCellType();
			System.out.println("Tipo do Indicador:" + type);
			if (type == CellType.STRING) {
				indicador = cell.getStringCellValue();
				if (indicador.equals("A")) {
					tipoLancamento = TipoLancamentoEnum.ESTORNO;
				}
			} else {
				tipoLancamento = TipoLancamentoEnum.CONTRATO;
			}
		}
		int linhaPlanilha = row.getRowNum()+1;
		System.out.println("Linha: " + linhaPlanilha + " Mês Referencia:" + mesReferencia + " Pedido:" + pedido
				+ " Plano:" + plano + " CNPJ:" + CnpjCpf + " Motivo/Observacao:" + motivoObservacao + " Valor:" + valor
				+ " Mercado/Contrato: " + mercadoContrato);
		Lancamento lancamento = new Lancamento();
		lancamento.setLinhaPlanilha(linhaPlanilha);
		lancamento.setMesReferencia(mesReferencia);
		lancamento.setPedido(pedido);
		lancamento.setPlano(plano);
		lancamento.setCnpjCpf(CnpjCpf);
		lancamento.setMotivoObservacao(motivoObservacao);
		lancamento.setValor(valor);
		lancamento.setMercadoContrato(mercadoContrato);
		lancamentoRepository.save(lancamento);
	}

	private static String formatarData(java.util.Date dataFimPlaca) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return formato.format(dataFimPlaca);
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
