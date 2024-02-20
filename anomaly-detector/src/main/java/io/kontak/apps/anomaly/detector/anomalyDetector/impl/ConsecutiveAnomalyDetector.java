package io.kontak.apps.anomaly.detector.anomalyDetector.impl;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyCandidate;
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

    Queue<AnomalyCandidate> anomalyCandidates = new LinkedList<>();
    int consecutiveReadingsAmount = 10;
    double anomalyThreshold = 5d;

    @Override
    public Stream<Anomaly> apply(TemperatureReading temperatureReading) {

        selectConsecutiveReadings(new AnomalyCandidate(temperatureReading));

        if(anomalyCandidates.size() == consecutiveReadingsAmount) {
            return detectNewAnomalies();
        } else {
            return Stream.of();
        }
    }

    private Stream<Anomaly> detectNewAnomalies() {

        double sumOfConsecutiveReadings = anomalyCandidates.stream()
            .collect(Collectors.summingDouble(anomalyCandidate -> anomalyCandidate.getTemperatureReading().temperature()));

        return anomalyCandidates.stream()
            .filter(anomalyCandidate -> !anomalyCandidate.isDetectedAnomaly())
            .filter(anomalyCandidate -> isAnomaly(anomalyCandidate, sumOfConsecutiveReadings))
            .map(anomalyCandidate -> anomalyCandidate.setAsDetectedAnomaly())
            .map(anomalyCandidate -> new Anomaly(anomalyCandidate.getTemperatureReading(),
                ANOMALY_DETECTOR_TYPE.CONSECUTIVE_ANOMALY));
    }


    private void selectConsecutiveReadings(AnomalyCandidate anomalyCandidate) {
        anomalyCandidates.add(anomalyCandidate);

        if(anomalyCandidates.size() > consecutiveReadingsAmount) {
            anomalyCandidates.remove();
        }
    }

    private boolean isAnomaly(AnomalyCandidate anomalyCandidate, double sumOfConsecutiveReadings) {

        double temperatureReadingTemperature = anomalyCandidate.getTemperatureReading().temperature();

        double averageOfTheRemainingReadings = (sumOfConsecutiveReadings - temperatureReadingTemperature) / (consecutiveReadingsAmount - 1);
        return Math.abs(temperatureReadingTemperature - averageOfTheRemainingReadings) >= anomalyThreshold;
    }
}
