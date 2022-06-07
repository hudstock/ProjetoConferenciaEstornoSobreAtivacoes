package com.example.conferenciaestorno.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conferenciaestorno.domain.model.Lancamento;
import com.example.conferenciaestorno.domain.model.Resultado;
import com.example.conferenciaestorno.domain.model.TipoLancamentoEnum;
import com.example.conferenciaestorno.domain.repository.LancamentoRepository;
import com.example.conferenciaestorno.domain.repository.ResultadoProjection;
import com.example.conferenciaestorno.util.Utils;

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
		lancamentoRepository.save(lancamento);
	}
}
