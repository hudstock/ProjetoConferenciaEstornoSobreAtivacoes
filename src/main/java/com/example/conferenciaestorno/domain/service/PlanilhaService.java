package com.example.conferenciaestorno.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.model.TipoLancamentoEnum;
import com.example.conferenciaestorno.util.Utils;

@Service
public class PlanilhaService {
	
	@Autowired
	LancamentoService lancamentoService;
	
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
				lancamentoService.importarLinha(row);
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

}
