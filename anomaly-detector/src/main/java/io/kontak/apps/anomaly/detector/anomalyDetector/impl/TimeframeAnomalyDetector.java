package io.kontak.apps.anomaly.detector.anomalyDetector.impl;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyCandidate;
import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class TimeframeAnomalyDetector implements AnomalyDetector {

    private Queue<AnomalyCandidate> anomalyCandidates = new LinkedList<>();
    private Duration timeframeDuration = Duration.ofSeconds(5);
    private double anomalyThreshold = 5;

    @Override
    public Stream<Anomaly> apply(TemperatureReading temperatureReading) {

        selectAnomalyCandidatesInTimeframe(new AnomalyCandidate(temperatureReading));

        double timeframeReadingsAverage  = anomalyCandidates.stream()
            .collect(Collectors.averagingDouble(anomalyCandidate -> anomalyCandidate.getTemperatureReading().temperature()));

        return anomalyCandidates.stream()
            .filter(anomalyCandidate -> !anomalyCandidate.isDetectedAnomaly())
            .filter(anomalyCandidate -> isAnomaly(anomalyCandidate, timeframeReadingsAverage))
            .map(anomalyCandidate -> anomalyCandidate.setAsDetectedAnomaly())
            .map(anomalyCandidate -> new Anomaly(anomalyCandidate.getTemperatureReading(), ANOMALY_DETECTOR_TYPE.TIMEFRAME_ANOMALY));
    }

    private void selectAnomalyCandidatesInTimeframe(AnomalyCandidate anomalyCandidate) {
        anomalyCandidates.add(anomalyCandidate);

        Instant newAnomalyCandidateTimeStamp = anomalyCandidate.getTemperatureReading().timestamp();

        while(readingsDurationOlderThanTimeframeDuration(anomalyCandidates.element().getTemperatureReading().timestamp(), newAnomalyCandidateTimeStamp)) {
            anomalyCandidates.remove();
        }
    }

    private boolean readingsDurationOlderThanTimeframeDuration(Instant readingToCheck, Instant newReading) {
        Duration readingsDuration = Duration.between(readingToCheck, newReading);
        return readingsDuration.compareTo(timeframeDuration) > 0;
    }

    private boolean isAnomaly(AnomalyCandidate anomalyCandidate, double timeframeReadingsAverage) {
        return Math.abs(anomalyCandidate.getTemperatureReading().temperature() - timeframeReadingsAverage) >= anomalyThreshold;
    }
}
