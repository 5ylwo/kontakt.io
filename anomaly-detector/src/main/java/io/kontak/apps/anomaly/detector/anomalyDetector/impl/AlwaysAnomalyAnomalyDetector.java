package io.kontak.apps.anomaly.detector.anomalyDetector.impl;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class AlwaysAnomalyAnomalyDetector implements AnomalyDetector {

    @Override
    public Stream<Anomaly> apply(TemperatureReading temperatureReading) {

        return List.of(
            new Anomaly(
                new TemperatureReading(
                    temperatureReading.temperature(),
                    temperatureReading.roomId(),
                    temperatureReading.thermometerId(),
                    temperatureReading.timestamp()),
                ANOMALY_DETECTOR_TYPE.ALWAYS_ANOMALY
            )
        ).stream();
    }
}
