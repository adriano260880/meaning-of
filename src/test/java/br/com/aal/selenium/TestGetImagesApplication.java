package br.com.aal.selenium;

import org.springframework.boot.SpringApplication;

public class TestGetImagesApplication {

	public static void main(String[] args) {
		SpringApplication.from(GetImagesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
