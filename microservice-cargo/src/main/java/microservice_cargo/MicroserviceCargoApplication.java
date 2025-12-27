package microservice_cargo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceCargoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCargoApplication.class, args);
	}

}
