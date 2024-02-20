package io.kontak.apps.anomaly.detector.anomalyDetector.impl;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class ConsecutiveAnomalyDetector implements AnomalyDetector {

    Queue<TemperatureReading> savedTemperatureReadings = new LinkedList<>();
    int consecutiveReadingsAmount = 10;
    double anomalyThreshold = 5d;

    @Override
    public Stream<Anomaly> apply(TemperatureReading temperatureReading) {

        selectConsecutiveReadings(temperatureReading);

        double sumOfConsecutiveReadings = savedTemperatureReadings.stream().collect(Collectors.summingDouble(reading -> reading.temperature()));

        return savedTemperatureReadings.stream()
            .filter(reading -> isAnomaly(reading, sumOfConsecutiveReadings))
            .map(reading -> new Anomaly(reading, ANOMALY_DETECTOR_TYPE.CONSECUTIVE_ANOMALY));
    }

    private void selectConsecutiveReadings(TemperatureReading temperatureReading) {
        savedTemperatureReadings.add(temperatureReading);

        if(savedTemperatureReadings.size() > consecutiveReadingsAmount) {
            savedTemperatureReadings.remove();
        }
    }

    private boolean isAnomaly(TemperatureReading temperatureReading, double sumOfConsecutiveReadings) {

        double temperatureReadingTemperature = temperatureReading.temperature();

        double averageOfTheRemainingReadings = (sumOfConsecutiveReadings - temperatureReadingTemperature) / (consecutiveReadingsAmount - 1);
        return Math.abs(temperatureReadingTemperature - averageOfTheRemainingReadings) >= anomalyThreshold;
    }
}
