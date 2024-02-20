package io.kontakt.apps.anomaly.storage.db.entities;

import java.time.Instant;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ANOMALY")
public class AnomalyEntity {

  @EmbeddedId
  private AnomalyEntityId anomalyEntityId;

  @NotNull
  private double temperature;

  @NotNull
  private String roomId;

  public AnomalyEntity(){}
  public AnomalyEntity(String thermometerId, Instant timestamp, double temperature, String roomId) {
    this.anomalyEntityId = new AnomalyEntityId(thermometerId, timestamp);
    this.temperature = temperature;
    this.roomId = roomId;
  }

  public AnomalyEntityId getAnomalyEntityId() {
    return anomalyEntityId;
  }

  public double getTemperature() {
    return temperature;
  }

  public String getRoomId() {
    return roomId;
  }
}
