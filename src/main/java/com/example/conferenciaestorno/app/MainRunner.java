package com.example.conferenciaestorno.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.conferenciaestorno.domain.service.LancamentoService;
import com.example.conferenciaestorno.domain.service.PlanilhaService;

@Configuration
public class MainRunner implements CommandLineRunner {

	@Autowired
	LancamentoService lancamentoService;
	
	@Autowired
	PlanilhaService planilhaService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Main Runner");
		
		//Será executado apenas uma vez, para criar o banco de dados.
		//planilhaService.importarPlanilha("/Arquivo.xlsx");
		
		//Será executado apenas uma vez, para gerar os resultados conforme dados do banco de dados;
		//lancamentoService.calcularResultadoCruzamento();
		
		//Gerar agora funcionalidades que realiza o seguinte:
		//Buscar os resultados, ordenados por diferença de data (crescente)		
		//Para cada codigo de pedido, 
		//buscar todos os lançamentos do tipo contrato e imprimir na planilha
		//Buscar todos os lancamentos do tipo estorno e imprimir na planilha
		//Imprimir na planilha o resultado nas colunas I,J e K (totais apenas) em uma linha em branco;
		
		//Relembrar: Como gerar um arquivo xlsx, criar uma aba e ir criando uma linha para cada resultado carregado (os 921)
		
	}

}
