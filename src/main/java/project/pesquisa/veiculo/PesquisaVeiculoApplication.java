package project.pesquisa.veiculo;

import project.pesquisa.veiculo.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PesquisaVeiculoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PesquisaVeiculoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
