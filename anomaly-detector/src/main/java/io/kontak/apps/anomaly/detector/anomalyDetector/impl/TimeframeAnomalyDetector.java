package io.kontak.apps.anomaly.detector.anomalyDetector.impl;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyCandidate;
import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.anomaly.detector.config.detectorTypes.TimeframeAnomalyDetectorTypeConfig;
import io.kontak.apps.detector.AnomalyDetectorType;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class TimeframeAnomalyDetector implements AnomalyDetector {

    private Queue<AnomalyCandidate> anomalyCandidatesQueue = new LinkedList<>();

    private final AnomalyDetectorType anomalyDetectorType;
    private final Duration timeframeDuration;
    private final double anomalyThreshold;

    public TimeframeAnomalyDetector(TimeframeAnomalyDetectorTypeConfig timeframeAnomalyDetectorTypeConfig) {
        this.anomalyDetectorType = timeframeAnomalyDetectorTypeConfig.getAnomalyDetectorType();
        this.timeframeDuration = timeframeAnomalyDetectorTypeConfig.getTimeframeDuration();
        this.anomalyThreshold = timeframeAnomalyDetectorTypeConfig.getAnomalyThreshold();
    }

    @Override
    public Stream<Anomaly> apply(TemperatureReading temperatureReading) {

        selectAnomalyCandidatesInTimeframe(new AnomalyCandidate(temperatureReading));

        double timeframeReadingsAverage  = anomalyCandidatesQueue.stream()
            .collect(Collectors.averagingDouble(anomalyCandidate -> anomalyCandidate.getTemperatureReading().temperature()));

        return anomalyCandidatesQueue.stream()
            .filter(AnomalyCandidate::wasNotDetected) //Anomaly candidate was already detected in previous runs
            .filter(anomalyCandidate -> isAnomaly(anomalyCandidate, timeframeReadingsAverage))
            .peek(AnomalyCandidate::setAsDetectedAnomaly)
            .map(anomalyCandidate -> new Anomaly(anomalyCandidate.getTemperatureReading(), anomalyDetectorType));
    }

    private void selectAnomalyCandidatesInTimeframe(AnomalyCandidate newAnomalyCandidate) {
        anomalyCandidatesQueue.add(newAnomalyCandidate);

        while(anomalyCandidateOlderThanThanTimeframeDuration(anomalyCandidatesQueue.element(), newAnomalyCandidate)) {
            anomalyCandidatesQueue.remove();
        }
    }

    private boolean anomalyCandidateOlderThanThanTimeframeDuration(AnomalyCandidate anomalyCandidate, AnomalyCandidate newAnomalyCandidate) {               
        Duration readingsDuration = Duration.between(
            anomalyCandidate.getTemperatureReading().timestamp(), 
            newAnomalyCandidate.getTemperatureReading().timestamp());
        
        return readingsDuration.compareTo(timeframeDuration) > 0;
    }

    /** The function checks if {@code anomalyCandidate} is an anomaly.
     * {@code anomalyCandidate} is an anomaly if it differs from the average of all measurements by {@link TimeframeAnomalyDetector#anomalyThreshold} or more.
     *
     * @param anomalyCandidate anomaly candidate to check
     * @param timeframeReadingsAverage average of all measurements
     * @return true if {@code anomalyCandidate} is anomaly, false if not
     */
    private boolean isAnomaly(AnomalyCandidate anomalyCandidate, double timeframeReadingsAverage) {
        return Math.abs(anomalyCandidate.getTemperatureReading().temperature() - timeframeReadingsAverage) >= anomalyThreshold;
    }
}
