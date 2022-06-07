package com.example.conferenciaestorno.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resultado")
public class Resultado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String pedido;

	private BigDecimal totalContrato;

	private LocalDate dataContrato;

	private BigDecimal totalEstorno;

	private LocalDate dataEstorno;

	private BigDecimal diferencaValor;

	private Integer diferencaData;
	
	public Resultado() {
		
	}

	public Resultado(String pedido, BigDecimal totalContrato, LocalDate dataContrato, BigDecimal totalEstorno,
			LocalDate dataEstorno, BigDecimal diferencaValor, Integer diferencaData) {
		super();
		this.pedido = pedido;
		this.totalContrato = totalContrato;
		this.dataContrato = dataContrato;
		this.totalEstorno = totalEstorno;
		this.dataEstorno = dataEstorno;
		this.diferencaValor = diferencaValor;
		this.diferencaData = diferencaData;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getTotalContrato() {
		return totalContrato;
	}

	public void setTotalContrato(BigDecimal totalContrato) {
		this.totalContrato = totalContrato;
	}

	public LocalDate getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(LocalDate dataContrato) {
		this.dataContrato = dataContrato;
	}

	public BigDecimal getTotalEstorno() {
		return totalEstorno;
	}

	public void setTotalEstorno(BigDecimal totalEstorno) {
		this.totalEstorno = totalEstorno;
	}

	public LocalDate getDataEstorno() {
		return dataEstorno;
	}

	public void setDataEstorno(LocalDate dataEstorno) {
		this.dataEstorno = dataEstorno;
	}

	public BigDecimal getDiferencaValor() {
		return diferencaValor;
	}

	public void setDiferencaValor(BigDecimal diferencaValor) {
		this.diferencaValor = diferencaValor;
	}

	public Integer getDiferencaData() {
		return diferencaData;
	}

	public void setDiferencaData(Integer diferencaData) {
		this.diferencaData = diferencaData;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pedido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resultado other = (Resultado) obj;
		return Objects.equals(pedido, other.pedido);
	}

	@Override
	public String toString() {
		return "Resultado [pedido=" + pedido + ", totalContrato=" + totalContrato + ", dataContrato=" + dataContrato
				+ ", totalEstorno=" + totalEstorno + ", dataEstorno=" + dataEstorno + ", diferencaValor="
				+ diferencaValor + ", diferencaData=" + diferencaData + "]";
	}

}
