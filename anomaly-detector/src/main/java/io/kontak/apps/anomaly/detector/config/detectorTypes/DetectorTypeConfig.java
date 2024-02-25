package io.kontak.apps.anomaly.detector.config.detectorTypes;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "config")
@ConstructorBinding
public record DetectorTypeConfig(AnomalyDetectorTypeConfig alwaysAnomalyV1,
                                 ConsecutiveAnomalyDetectorTypeConfig consecutiveAnomalyV1,
                                 TimeframeAnomalyDetectorTypeConfig timeframeAnomalyV1) {}
