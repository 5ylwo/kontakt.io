package io.kontak.apps.anomaly.detector.config;

import io.kontak.apps.anomaly.detector.anomalyDetector.impl.AlwaysAnomalyAnomalyDetector;
import io.kontak.apps.anomaly.detector.anomalyDetector.impl.ConsecutiveAnomalyDetector;
import io.kontak.apps.anomaly.detector.anomalyDetector.impl.TimeframeAnomalyDetector;
import io.kontak.apps.anomaly.detector.config.detectorTypes.AnomalyDetectorTypeConfig;
import io.kontak.apps.anomaly.detector.config.detectorTypes.ConsecutiveAnomalyDetectorTypeConfig;
import io.kontak.apps.anomaly.detector.config.detectorTypes.TimeframeAnomalyDetectorTypeConfig;
import java.sql.Time;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  public AlwaysAnomalyAnomalyDetector alwaysAnomalyAnomalyDetector(AnomalyDetectorTypeConfig anomalyDetectorTypeConfig) {
    return new AlwaysAnomalyAnomalyDetector(anomalyDetectorTypeConfig);
  }

  @Bean
  public ConsecutiveAnomalyDetector consecutiveAnomalyDetector(ConsecutiveAnomalyDetectorTypeConfig consecutiveAnomalyDetectorTypeConfig) {
    return new ConsecutiveAnomalyDetector(consecutiveAnomalyDetectorTypeConfig);
  }

  @Bean
  public TimeframeAnomalyDetector timeframeAnomalyDetector(TimeframeAnomalyDetectorTypeConfig timeframeAnomalyDetectorTypeConfig) {
    return new TimeframeAnomalyDetector(timeframeAnomalyDetectorTypeConfig);
  }

}
