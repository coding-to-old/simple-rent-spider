package cto.github.rent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import simple.reat.bast.UtilPlugin;

@SpringBootApplication
@org.springframework.context.annotation.Import({UtilPlugin.class})
public class RentSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentSpiderApplication.class, args);
	}

}
