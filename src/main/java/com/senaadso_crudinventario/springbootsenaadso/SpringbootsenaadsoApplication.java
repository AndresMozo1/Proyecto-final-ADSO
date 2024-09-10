
package com.senaadso_crudinventario.springbootsenaadso;

// Importa la clase SpringApplication de Spring Boot, que se utiliza para arrancar la aplicación.
import org.springframework.boot.SpringApplication;
// Importa la anotación SpringBootApplication, que marca la clase principal de la aplicación y activa varias configuraciones automáticas de Spring.
import org.springframework.boot.autoconfigure.SpringBootApplication;

// La anotación @SpringBootApplication es una combinación de varias anotaciones:
// - @Configuration: Indica que esta clase puede usarse para definir beans en el contexto de Spring.
// - @EnableAutoConfiguration: Habilita la configuración automática de Spring Boot.
// - @ComponentScan: Permite escanear los paquetes en busca de componentes de Spring (como @Controller, @Service, etc.).
@SpringBootApplication
public class SpringbootsenaadsoApplication {

    // El método main es el punto de entrada de la aplicación.
    // SpringApplication.run() arranca la aplicación de Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(SpringbootsenaadsoApplication.class, args);
	}

}


