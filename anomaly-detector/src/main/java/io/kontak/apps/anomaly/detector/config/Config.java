package io.kontak.apps.anomaly.detector.config;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "config")
@ConstructorBinding
public record Config(List<AnomalyDetectorType> anomalyDetectorTypes) {
  public record AnomalyDetectorType(ANOMALY_DETECTOR_TYPE anomalyDetectorType, Class<AnomalyDetector> clazz, boolean enabled) {}
}
