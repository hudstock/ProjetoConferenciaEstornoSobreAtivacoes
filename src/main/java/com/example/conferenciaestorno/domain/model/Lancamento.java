package com.example.conferenciaestorno.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer linhaPlanilha;

	private TipoLancamentoEnum tipoLancamento;

	private String mesReferencia;

	private LocalDate dataReferencia;

	private String pedido;

	private String plano;

	private String cnpjCpf;

	@Column(length = 1000)
	private String motivoObservacao;

	private BigDecimal valor;

	private String mercadoContrato;

	public Lancamento() {

	}

	public Lancamento(Long id, Integer linhaPlanilha, TipoLancamentoEnum tipoLancamento, String mesReferencia,
			LocalDate dataReferencia, String pedido, String plano, String cnpjCpf, String motivoObservacao,
			BigDecimal valor, String mercadoContrato) {
		super();
		this.id = id;
		this.linhaPlanilha = linhaPlanilha;
		this.tipoLancamento = tipoLancamento;
		this.mesReferencia = mesReferencia;
		this.dataReferencia = dataReferencia;
		this.pedido = pedido;
		this.plano = plano;
		this.cnpjCpf = cnpjCpf;
		this.motivoObservacao = motivoObservacao;
		this.valor = valor;
		this.mercadoContrato = mercadoContrato;
	}

	public Integer getLinhaPlanilha() {
		return linhaPlanilha;
	}

	public void setLinhaPlanilha(Integer linhaPlanilha) {
		this.linhaPlanilha = linhaPlanilha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoLancamentoEnum getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamentoEnum tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public String getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getMotivoObservacao() {
		return motivoObservacao;
	}

	public void setMotivoObservacao(String motivoObservacao) {
		this.motivoObservacao = motivoObservacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getMercadoContrato() {
		return mercadoContrato;
	}

	public void setMercadoContrato(String mercadoContrato) {
		this.mercadoContrato = mercadoContrato;
	}

	public LocalDate getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(LocalDate dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", linhaPlanilha=" + linhaPlanilha + ", tipoLancamento=" + tipoLancamento
				+ ", mesReferencia=" + mesReferencia + ", dataReferencia=" + dataReferencia + ", pedido=" + pedido
				+ ", plano=" + plano + ", cnpjCpf=" + cnpjCpf + ", motivoObservacao=" + motivoObservacao + ", valor="
				+ valor + ", mercadoContrato=" + mercadoContrato + "]";
	}
}
