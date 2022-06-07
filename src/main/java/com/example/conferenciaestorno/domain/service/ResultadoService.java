package com.example.conferenciaestorno.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conferenciaestorno.domain.model.Resultado;
import com.example.conferenciaestorno.domain.repository.ResultadoRepository;

@Service
public class ResultadoService {
	
	@Autowired
	ResultadoRepository resultadoRepository;
	
	
	public Resultado salvar (Resultado resultado) {
		return resultadoRepository.save(resultado);
	}

}
