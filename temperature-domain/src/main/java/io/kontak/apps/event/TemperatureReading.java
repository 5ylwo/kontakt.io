package io.kontak.apps.event;

import java.time.Instant;
import java.util.Objects;

public record TemperatureReading(double temperature, String roomId, String thermometerId, Instant timestamp) {
  public TemperatureReading {
    Objects.requireNonNull(temperature, "temperature cannot be null");
    Objects.requireNonNull(roomId, "roomId cannot be null");
    Objects.requireNonNull(thermometerId, "thermometerId cannot be null");
    Objects.requireNonNull(timestamp, "timestamp cannot be null");
  }
}
