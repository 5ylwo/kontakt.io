package io.kontak.apps.anomaly.detector.anomalyDetector.impl;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyCandidate;
import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.anomaly.detector.config.detectorTypes.ConsecutiveAnomalyDetectorTypeConfig;
import io.kontak.apps.detector.AnomalyDetectorType;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class ConsecutiveAnomalyDetector implements AnomalyDetector {

    private Queue<AnomalyCandidate> anomalyCandidateQueue = new LinkedList<>();

    private final AnomalyDetectorType anomalyDetectorType;
    private final int consecutiveReadingsAmount;
    private final double anomalyThreshold;

    public ConsecutiveAnomalyDetector(ConsecutiveAnomalyDetectorTypeConfig consecutiveAnomalyDetectorTypeConfig) {
        this.anomalyDetectorType = consecutiveAnomalyDetectorTypeConfig.getAnomalyDetectorType();
        consecutiveReadingsAmount = consecutiveAnomalyDetectorTypeConfig.getConsecutiveReadingsAmount();
        anomalyThreshold = consecutiveAnomalyDetectorTypeConfig.getAnomalyThreshold();
    }

    @Override
    public Stream<Anomaly> apply(TemperatureReading temperatureReading) {
        selectConsecutiveReadings(new AnomalyCandidate(temperatureReading));

        if(anomalyCandidateQueue.size() == consecutiveReadingsAmount) {
            return detectNewAnomalies();
        } else {
            return Stream.of();
        }
    }

    private Stream<Anomaly> detectNewAnomalies() {

        double sumOfConsecutiveReadings = anomalyCandidateQueue.stream()
            .collect(Collectors.summingDouble(anomalyCandidate -> anomalyCandidate.getTemperatureReading().temperature()));

        return anomalyCandidateQueue.stream()
            .filter(AnomalyCandidate::wasNotDetected) //Anomaly candidate was already detected in previous runs
            .filter(anomalyCandidate -> isAnomaly(anomalyCandidate, sumOfConsecutiveReadings))
            .peek(AnomalyCandidate::setAsDetectedAnomaly)
            .map(anomalyCandidate -> new Anomaly(anomalyCandidate.getTemperatureReading(), anomalyDetectorType));
    }

    private void selectConsecutiveReadings(AnomalyCandidate anomalyCandidate) {
        anomalyCandidateQueue.add(anomalyCandidate);

        if(anomalyCandidateQueue.size() > consecutiveReadingsAmount) {
            anomalyCandidateQueue.remove();
        }
    }

    /** The function checks if {@code anomalyCandidate} is an anomaly.
     * {@code anomalyCandidate} is an anomaly if it differs from the average remaining measurements by {@link ConsecutiveAnomalyDetector#anomalyThreshold} or more.
     *
     * @param anomalyCandidate anomaly candidate to check
     * @param sumOfConsecutiveReadings sum of the remaining measurements
     * @return true if {@code anomalyCandidate} is anomaly, false if not
     */
    private boolean isAnomaly(AnomalyCandidate anomalyCandidate, double sumOfConsecutiveReadings) {
        double temperatureReadingTemperature = anomalyCandidate.getTemperatureReading().temperature();

        double averageOfTheRemainingReadings = (sumOfConsecutiveReadings - temperatureReadingTemperature) / (consecutiveReadingsAmount - 1);
        return Math.abs(temperatureReadingTemperature - averageOfTheRemainingReadings) >= anomalyThreshold;
    }
}
