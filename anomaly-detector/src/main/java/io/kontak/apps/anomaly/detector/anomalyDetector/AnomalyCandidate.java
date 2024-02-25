package io.kontak.apps.anomaly.detector.anomalyDetector;

import io.kontak.apps.event.TemperatureReading;

public class AnomalyCandidate {
  private TemperatureReading temperatureReading;
  private boolean isDetectedAnomaly = false;

  public AnomalyCandidate(TemperatureReading temperatureReading) {
    this.temperatureReading = temperatureReading;
  }

  public TemperatureReading getTemperatureReading() {
    return temperatureReading;
  }

  public boolean wasNotDetected() {
    return !isDetectedAnomaly;
  }
  
  public void setAsDetectedAnomaly() {
    isDetectedAnomaly = true;
  }
}
