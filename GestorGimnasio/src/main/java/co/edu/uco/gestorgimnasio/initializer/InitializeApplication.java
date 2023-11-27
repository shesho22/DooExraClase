package co.edu.uco.gestorgimnasio.initializer;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan (basePackages="co.edu.uco.gestorgimnasio.controller")
public class InitializeApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(InitializeApplication.class, args);
	
	}

}
