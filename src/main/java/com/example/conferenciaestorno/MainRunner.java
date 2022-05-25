package com.example.conferenciaestorno;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainRunner implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Main Runner");		
	}

}
