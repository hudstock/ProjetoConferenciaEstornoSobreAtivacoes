package com.example.conferenciaestorno.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class LancamentoServiceTest {
	
	/*	 
	@InjectMocks
	private ProductService producService;

	@Mock
	private ProductRepository productRepository;	 
	*/
	
	
	/*
	 * Funcionalidades deste service:
	 * criar um lançamento
	 * buscar um lançamento por Pedido
	 * buscar um lancamento por cpf/cnpj	  
	 * 
	 * */
	
	@BeforeEach
	public void setUp() {
		
		/*		
		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

		Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(productRepository.getOne(existingId)).thenReturn(product);
		Mockito.when(productRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(categoryRepository.getOne(existingCategory)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExistingCategory)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
		*/
		
	}
	

}
