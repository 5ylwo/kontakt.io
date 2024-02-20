package io.kontakt.apps.anomaly.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class AnomalyStorageApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnomalyStorageApplication.class, args);
	}
}
