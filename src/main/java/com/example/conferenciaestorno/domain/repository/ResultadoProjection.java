package com.example.conferenciaestorno.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

public interface ResultadoProjection {

	@Value("#{target.pedido}")
	String getPedido();

	@Value("#{target.total_contrato}")
	BigDecimal getTotalContrato();

	@Value("#{target.data_contrato}")
	LocalDate getDataContrato();

	@Value("#{target.total_estorno}")
	BigDecimal getTotalEstorno();

	@Value("#{target.data_estorno}")
	LocalDate getDataEstorno();

	@Value("#{target.diferenca_valor}")
	BigDecimal getDiferencaValor();

	@Value("#{target.diferenca_data}")
	Integer getDiferencaData();

}
