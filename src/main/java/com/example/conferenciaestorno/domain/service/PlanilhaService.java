package com.example.conferenciaestorno.domain.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;	
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.model.Resultado;
import com.example.conferenciaestorno.domain.model.TipoLancamentoEnum;
import com.example.conferenciaestorno.util.Utils;

@Service
public class PlanilhaService {
	
	@Autowired
	LancamentoService lancamentoService;
	
	@Autowired
	ResultadoService resultadoService;
	
	///Arquivo.xlsx
	public void importarPlanilha(String arquivo) throws IOException {
		InputStream input = getClass().getResourceAsStream(arquivo);
		Workbook wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> linhaIterator = sheet.iterator();
		int contador = 1;
		while (linhaIterator.hasNext()) {
			Row row = linhaIterator.next();
			if (row.getRowNum() >= 1) {
				importarLinha(row);
				contador++;
			}
		}
		System.out.println("Total de Registros: " + contador);
		wb.close();
	}
	
	public void importarLinha(Row row) {
		String mesReferencia = "";
		LocalDate dataReferencia = LocalDate.of(1900, 1, 1);
		String pedido = "";
		String plano = "";
		String CnpjCpf = "";
		String motivoObservacao = "";
		BigDecimal valor = BigDecimal.ZERO;
		String mercadoContrato = "";
		String indicador = "";
		TipoLancamentoEnum tipoLancamento = TipoLancamentoEnum.CONTRATO;
		Cell cell = row.getCell(0);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC)
				mesReferencia = Utils.formatarData(cell.getDateCellValue());

			if (type == CellType.STRING)
				mesReferencia = cell.getStringCellValue();

			dataReferencia = Utils.tratarMesReferencia(mesReferencia);
		}
		cell = row.getCell(5);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC) {
				Long result = (Long) Math.round(cell.getNumericCellValue());
				pedido = String.valueOf(result);
			} else {
				String valorString = cell.getStringCellValue();
				valorString = valorString.replaceAll("[\\D]", "");
				Long result = Long.parseLong(valorString);
				pedido = String.valueOf(result);
			}
		}
		cell = row.getCell(7);
		if (cell != null) {
			plano = cell.toString();
		}
		cell = row.getCell(9);
		if (cell != null) {
			CellType type = cell.getCellType();
			if (type == CellType.NUMERIC) {
				Long result = (Long) Math.round(cell.getNumericCellValue());
				CnpjCpf = String.valueOf(result);
			} else {
				String valorString = cell.getStringCellValue();
				valorString = valorString.replaceAll("[\\D]", "");
				Long result = Long.parseLong(valorString);
				CnpjCpf = String.valueOf(result);
			}
		}
		cell = row.getCell(11);
		if (cell != null) {
			motivoObservacao = cell.toString();
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
				Long result = (Long) Math.round(cell.getNumericCellValue());
				mercadoContrato = String.valueOf(result);
			} else {
				mercadoContrato = cell.getStringCellValue();
			}
		}
		cell = row.getCell(17);
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
		} else {
			System.out.println("campo indicador não encontrado");
		}
		int linhaPlanilha = row.getRowNum() + 1;
		System.out.println("Linha: " + linhaPlanilha + " Mês Referencia:" + mesReferencia + " Pedido:" + pedido
				+ " Plano:" + plano + " CNPJ:" + CnpjCpf + " Motivo/Observacao:" + motivoObservacao + " Valor:" + valor
				+ " Mercado/Contrato: " + mercadoContrato);
		Lancamento lancamento = new Lancamento();
		lancamento.setLinhaPlanilha(linhaPlanilha);
		lancamento.setMesReferencia(mesReferencia);
		lancamento.setDataReferencia(dataReferencia);
		lancamento.setPedido(pedido.trim());
		lancamento.setPlano(plano.trim());
		lancamento.setCnpjCpf(CnpjCpf);
		lancamento.setMotivoObservacao(motivoObservacao.trim());
		lancamento.setValor(valor);
		lancamento.setMercadoContrato(mercadoContrato.trim());
		lancamento.setTipoLancamento(tipoLancamento);
		lancamentoService.salvar(lancamento);
	}
	
	public void exportarResultadoCruzamento() throws IOException {
		// create a new file
				
		Workbook planilha = new XSSFWorkbook();		
		Row linha;		
		Cell celula;		
		Sheet aba = planilha.createSheet();		
		List<Resultado> resultados = resultadoService.buscarTodos();		
		
		int linhaAtual = 1;
		for (Resultado resultado : resultados) {
						
			List<Lancamento> lancamentos = lancamentoService.buscarTodosPorPedido(resultado.getPedido());
			if (lancamentos.isEmpty()) {
				System.out.println("GRAVE! Lancamentos não encontrado!!!!");
				linhaAtual++;
				continue;
			}
			
			for (Lancamento lancamento: lancamentos) {
				linha = aba.createRow(linhaAtual);
				System.out.println("Criando linha "+linhaAtual);				
				System.out.println("Incluindo o seguinte lancamento:");
				System.out.println(lancamento);
				
				celula = linha.createCell(0, CellType.STRING);
				celula.setCellValue(lancamento.getTipoLancamento().toString());
				
				celula = linha.createCell(1, CellType.NUMERIC);
				celula.setCellValue(lancamento.getLinhaPlanilha());
				
				celula = linha.createCell(2, CellType.STRING);
				celula.setCellValue(lancamento.getMesReferencia());
				
				celula = linha.createCell(3, CellType.STRING);
				celula.setCellValue(lancamento.getPedido());
				
				celula = linha.createCell(4, CellType.STRING);
				celula.setCellValue(lancamento.getPlano());
				
				celula = linha.createCell(5, CellType.STRING);
				celula.setCellValue(lancamento.getCnpjCpf());
				
				celula = linha.createCell(6, CellType.STRING);
				celula.setCellValue(lancamento.getMotivoObservacao());
				
				celula = linha.createCell(7, CellType.NUMERIC);
				celula.setCellValue(lancamento.getValor().doubleValue());
				
				linhaAtual++;				
			}
			linha = aba.createRow(linhaAtual);
			System.out.println("Criando linha "+linhaAtual);				
			System.out.println("Incluindo o resultado:");
			System.out.println(resultado);
			
			celula = linha.createCell(8, CellType.NUMERIC);
			celula.setCellValue(resultado.getDiferencaData());
			
			celula = linha.createCell(9, CellType.NUMERIC);
			celula.setCellValue(resultado.getTotalContrato().doubleValue());
			
			celula = linha.createCell(10, CellType.NUMERIC);
			celula.setCellValue(resultado.getTotalEstorno().doubleValue());
			
			celula = linha.createCell(11, CellType.NUMERIC);
			celula.setCellValue(resultado.getDiferencaValor().doubleValue());	
			
			linhaAtual++;					
		}		
		
		FileOutputStream arquivoResultado = new FileOutputStream("resultado.xlsx");
		planilha.write(arquivoResultado);
		arquivoResultado.close();
		
	}
	//Fórmula excel para pesquisar segunda coluna de valor , caso a primeira esteja vazia - =SUMIF(N$2:N$13741;"";O$2:O$13741) 

}
