package io.kontak.apps.event;

import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import java.util.Objects;

public record Anomaly(TemperatureReading temperatureReading, ANOMALY_DETECTOR_TYPE anomalyDetectorType) {
  public Anomaly {
    Objects.requireNonNull(temperatureReading, "temperature reading cannot be null");
    Objects.requireNonNull(anomalyDetectorType, "anomaly detector type cannot be null");
  }
}
