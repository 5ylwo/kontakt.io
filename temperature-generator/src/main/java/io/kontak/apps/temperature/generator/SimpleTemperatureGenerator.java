package io.kontak.apps.temperature.generator;

import io.kontak.apps.event.TemperatureReading;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SimpleTemperatureGenerator implements TemperatureGenerator {

    private final Random random = new Random();

    @Override
    public List<TemperatureReading> generate() {
        return List.of(generateSingleReading());
    }

    private TemperatureReading generateSingleReading() {

        String randomThermometerId = UUID.randomUUID().toString().substring(0, 2);

        return new TemperatureReading(
                random.nextDouble(10d, 30d),
                randomThermometerId.substring(0, 1),
                randomThermometerId,
                Instant.now()
        );
    }
}
