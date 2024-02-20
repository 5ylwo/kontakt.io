package io.kontak.apps.anomaly.detector.anomalyDetector;

import io.kontak.apps.event.TemperatureReading;

public class AnomalyCandidate {
  TemperatureReading temperatureReading;
  boolean isDetectedAnomaly = false;

  public AnomalyCandidate(TemperatureReading temperatureReading) {
    this.temperatureReading = temperatureReading;
  }

  public TemperatureReading getTemperatureReading() {
    return temperatureReading;
  }

  public void setTemperatureReading(TemperatureReading temperatureReading) {
    this.temperatureReading = temperatureReading;
  }

  public boolean isDetectedAnomaly() {
    return isDetectedAnomaly;
  }


  public AnomalyCandidate setAsDetectedAnomaly() {
    isDetectedAnomaly = true;
    return this;
  }



}
