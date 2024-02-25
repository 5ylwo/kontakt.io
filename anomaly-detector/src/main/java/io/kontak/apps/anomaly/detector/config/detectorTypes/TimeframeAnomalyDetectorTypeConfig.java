package io.kontak.apps.anomaly.detector.config.detectorTypes;

import java.time.Duration;

public class TimeframeAnomalyDetectorTypeConfig extends AnomalyDetectorTypeConfig {
  private Duration timeframeDuration;
  private double anomalyThreshold;

  public Duration getTimeframeDuration() {
    return timeframeDuration;
  }

  public double getAnomalyThreshold() {
    return anomalyThreshold;
  }
}
