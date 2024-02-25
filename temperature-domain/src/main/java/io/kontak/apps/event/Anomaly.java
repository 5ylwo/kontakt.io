package io.kontak.apps.event;

import io.kontak.apps.detector.AnomalyDetectorType;
import java.util.Objects;

public record Anomaly(TemperatureReading temperatureReading, AnomalyDetectorType anomalyDetectorType) {
  public Anomaly {
    Objects.requireNonNull(temperatureReading, "temperature reading cannot be null");
    Objects.requireNonNull(anomalyDetectorType, "anomaly detector type cannot be null");
  }
}
