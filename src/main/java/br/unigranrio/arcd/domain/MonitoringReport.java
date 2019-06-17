package br.unigranrio.arcd.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MonitoringReport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private double somaCorrente;
	private double somaTensao;
	private double custoTotal;
	
	public MonitoringReport() {

	}

	public MonitoringReport(double somaCorrente, double somaTensao, double custoTotal) {
		super();
		this.somaCorrente = somaCorrente;
		this.somaTensao = somaTensao;
		custoTotal = Math.round(custoTotal);
		this.custoTotal = custoTotal;

	}

	public double getSomaCorrente() {
		return somaCorrente;
	}

	public void setSomaCorrente(double somaCorrente) {
		this.somaCorrente = somaCorrente;
	}

	public double getSomaTensao() {
		return somaTensao;
	}

	public void setSomaTensao(double somaTensao) {
		this.somaTensao = somaTensao;
	}

	public double getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(double custoTotal) {
		this.custoTotal = custoTotal;
	}

}
