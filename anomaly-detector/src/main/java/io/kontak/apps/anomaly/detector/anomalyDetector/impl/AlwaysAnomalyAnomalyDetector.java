package io.kontak.apps.anomaly.detector.anomalyDetector.impl;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.anomaly.detector.config.detectorTypes.AnomalyDetectorTypeConfig;
import io.kontak.apps.detector.AnomalyDetectorType;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class AlwaysAnomalyAnomalyDetector implements AnomalyDetector {

    public AlwaysAnomalyAnomalyDetector(AnomalyDetectorTypeConfig anomalyDetectorTypeConfig) {
    }

    @Override
    public Stream<Anomaly> apply(TemperatureReading temperatureReading) {

        return List.of(
            new Anomaly(
                new TemperatureReading(
                    temperatureReading.temperature(),
                    temperatureReading.roomId(),
                    temperatureReading.thermometerId(),
                    temperatureReading.timestamp()),
                AnomalyDetectorType.ALWAYS_ANOMALY
            )
        ).stream();
    }
}
