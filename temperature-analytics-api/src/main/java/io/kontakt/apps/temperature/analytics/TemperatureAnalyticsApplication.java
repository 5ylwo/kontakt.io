package io.kontakt.apps.temperature.analytics;

import io.kontakt.apps.anomaly.storage.db.AnomalyDetectorTypeRepository;
import io.kontakt.apps.anomaly.storage.db.AnomalyEntityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses={AnomalyDetectorTypeRepository.class, AnomalyEntityRepository.class})
@EnableJpaRepositories
@EntityScan
public class TemperatureAnalyticsApplication {
	public static void main(String[] args) {
		SpringApplication.run(TemperatureAnalyticsApplication.class, args);
	}
}
