package io.kontakt.apps.anomaly.storage.config;

import io.kontak.apps.event.Anomaly;
import io.kontakt.apps.anomaly.storage.kafka.AnomaliesListener;
import io.kontakt.apps.anomaly.storage.services.CreateAnomalyService;
import java.util.function.Consumer;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public Consumer<KStream<String, Anomaly>> anomalyStorage(
        CreateAnomalyService createAnomalyService) {
        return new AnomaliesListener(createAnomalyService);
    }

}
