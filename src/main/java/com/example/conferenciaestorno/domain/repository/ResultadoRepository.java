package com.example.conferenciaestorno.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.conferenciaestorno.domain.model.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, String> {

	@Query(value = "select * from resultado order by diferenca_data", nativeQuery = true)
	List<Resultado> buscarTodosOrdenadosPorDiferencaData();
}
