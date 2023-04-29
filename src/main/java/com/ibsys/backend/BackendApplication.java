package com.ibsys.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Access SwaggerUI: http://localhost:8080/swagger
 */
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "IBSYS2 Backend Webservice",
				version = "1.0.0",
				description = "This is the webservice which provides the endpoints for a production plan"
		)
)
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
