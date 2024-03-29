package io.kontak.apps.temperature.generator;

import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TemperatureGeneratorJob {

    private final TemperatureGenerator generator;
    private final TemperatureStreamPublisher publisher;

    public TemperatureGeneratorJob(TemperatureGenerator generator, TemperatureStreamPublisher publisher) {
        this.generator = generator;
        this.publisher = publisher;
    }

    @Scheduled(fixedRateString = "${temperature-generator.rate.seconds}", timeUnit = TimeUnit.SECONDS)
    public void generateDataAndSend() {
        generator.generate().forEach(publisher::publish);
    }

}
