package io.kontak.apps.anomaly.detector.config.detectorTypes;

import io.kontak.apps.detector.AnomalyDetectorType;

public class AnomalyDetectorTypeConfig {
  private AnomalyDetectorType anomalyDetectorType;
  private boolean enabled;

  public AnomalyDetectorType getAnomalyDetectorType() {
    return anomalyDetectorType;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
