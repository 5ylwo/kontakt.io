package io.kontak.apps.anomaly.detector.kafka;

import io.kontak.apps.anomaly.detector.AnomalyDetectorCoordinator;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.function.Function;
import org.apache.kafka.streams.kstream.KStream;

public class TemperatureMeasurementsListener implements
    Function<KStream<String, TemperatureReading>, KStream<String, Anomaly>> {

    AnomalyDetectorCoordinator anomalyDetectorCoordinator;

    public TemperatureMeasurementsListener(AnomalyDetectorCoordinator anomalyDetectorCoordinator) {
        this.anomalyDetectorCoordinator = anomalyDetectorCoordinator;
    }

    @Override
    public KStream<String, Anomaly> apply(KStream<String, TemperatureReading> events) {

        return events
                .flatMapValues((temperatureReading)
                    -> anomalyDetectorCoordinator.detectAnomaliesForOneThermometer(temperatureReading.thermometerId(), temperatureReading));
    }
}
