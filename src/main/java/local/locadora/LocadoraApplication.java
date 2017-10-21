package local.locadora;

import local.locadora.controller.FilmeFormatter;
import local.locadora.controller.UsuarioFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
public class LocadoraApplication {


	public static void main(String[] args) {
		SpringApplication.run(LocadoraApplication.class, args);
	}
}
