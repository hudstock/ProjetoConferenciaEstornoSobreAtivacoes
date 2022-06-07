package com.example.conferenciaestorno.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.example.conferenciaestorno.domain.model.Resultado;
import com.example.conferenciaestorno.domain.model.TipoLancamentoEnum;
import com.example.conferenciaestorno.domain.repository.LancamentoRepository;
import com.example.conferenciaestorno.domain.repository.ResultadoProjection;
import com.example.conferenciaestorno.domain.repository.ResultadoRepository;
import com.example.conferenciaestorno.domain.service.LancamentoService;
import com.example.conferenciaestorno.util.Utils;

@Configuration
public class MainRunner implements CommandLineRunner {

	@Autowired
	LancamentoService lancamentoService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Main Runner");
		// TESTES
		// testeAmbiente();
		// testeAbrirPlanilha();
		// testeBuscarLancamentosPorTipo();

		// EXECUÇÕES
		// importarPlanilha();

		lancamentoService.calcularResultadoCruzamento();
		// CRIAR METODO PARA NOVA ABA DE APENAS CONTRATOS
		// CRIAR MÉTODO PARA NOVA ABA APENAS ESTORNOS
		// CRIAR MÉTODO PARA NOVA ABA AGRUPAMENTO CONTRATOS E ESTORNOS E TOTAIS
		// CRIAR METODO PARA NOVA ABA CONTRATOS SEM ESTORNOS
		// CRIAR METODO PARA NOVA ABA ESTORNOS SEM CONTRATOS

	}

	/*
	 * FINALIZADOS Ler planilha e gravar em banco de dados; Consulta de todos os
	 * contratos e todos os estornos conferidos;
	 * 
	 * PENDENTES gerar 1ª aba contendo apenas os pedidos (contratos) gerar; 2ª aba
	 * contendo apenas os estornos gerar; 3ª aba contendo os agrupamentos de pedidos
	 * e cnpj_cpf gerar; 4ª aba contendo os pedidos sem estorno gerar; 5ª aba
	 * contendo os estornos sem pedido
	 * 
	 */

	

	

}
