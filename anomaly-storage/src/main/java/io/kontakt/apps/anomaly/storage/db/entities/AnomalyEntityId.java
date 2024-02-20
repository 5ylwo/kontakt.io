package io.kontakt.apps.anomaly.storage.db.entities;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Embeddable;

@Embeddable
public class AnomalyEntityId implements Serializable {

  private String thermometerId;
  private Instant timestamp;

  public AnomalyEntityId(){}
  public AnomalyEntityId(String thermometerId, Instant timestamp) {
    this.thermometerId = thermometerId;
    this.timestamp = timestamp;
  }

  public String getThermometerId() {
    return thermometerId;
  }

  public Instant getTimestamp() {
    return timestamp;
  }
}
