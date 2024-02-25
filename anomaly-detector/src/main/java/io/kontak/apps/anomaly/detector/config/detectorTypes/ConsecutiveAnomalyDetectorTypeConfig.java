package io.kontak.apps.anomaly.detector.config.detectorTypes;

public class ConsecutiveAnomalyDetectorTypeConfig extends AnomalyDetectorTypeConfig {
  private int consecutiveReadingsAmount;
    private double anomalyThreshold;
  public int getConsecutiveReadingsAmount() {
    return consecutiveReadingsAmount;
  }

  public double getAnomalyThreshold() {
    return anomalyThreshold;
  }

}
