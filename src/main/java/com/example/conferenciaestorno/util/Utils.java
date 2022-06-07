package com.example.conferenciaestorno.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Utils {
	
	public static LocalDate transformarDataStringEmLocalDate(String entrada) {
		String[] separado = entrada.split("/");
		return LocalDate.of( Integer.valueOf(separado[2]), Integer.valueOf(separado[1]), Integer.valueOf(separado[0]));		
	}
	
	public static String formatarData(java.util.Date dataFimPlaca) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return formato.format(dataFimPlaca);
	}
	
	public static LocalDate tratarMesReferencia(String entrada) {
		String saida = entrada;
		if (entrada.contains("_")) {
			System.out.println("Tratando mês de referencia "+ entrada);
			String[] separado = entrada.split("_");
			System.out.println(separado[0]);
			switch (separado[0]) {
			case "JANEIRO":
				saida = "01/01/"+separado[1];
				break;
			case "FEVEREIRO":
				saida = "01/02/"+separado[1];
				break;
			case "MARÇO":
				saida = "01/03/"+separado[1];
				break;
			case "ABRIL":
				saida = "01/04/"+separado[1];
				break;
			case "MAIO":
				saida = "01/05/"+separado[1];
				break;
			case "JUNHO":
				saida = "01/06/"+separado[1];
				break;
			case "JULHO":
				saida = "01/07/"+separado[1];
				break;
			case "AGOSTO":
				saida = "01/08/"+separado[1];
				break;
			case "SETEMBRO":
				saida = "01/09/"+separado[1];
				break;
			case "OUTUBRO":
				saida = "01/10/"+separado[1];
				break;
			case "NOVEMBRO":
				saida = "01/11/"+separado[1];
				break;
			case "DEZEMBRO":
				saida = "01/12/"+separado[1];
				break;	
			default:
				saida = entrada;
			}
		}
		return transformarDataStringEmLocalDate(saida);
	}

}
