package io.kontakt.apps.anomaly.storage.db.entities;

import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ANOMALY_DETECTOR_TYPE")
public class AnomalyDetectorTypeEntity {

  @Id
  @Enumerated(EnumType.STRING)
  private ANOMALY_DETECTOR_TYPE detectorType;

  @ManyToMany
  @JoinTable(
      name = "ANOMALY_TO_DETECTOR_TYPE",
      inverseJoinColumns = {@JoinColumn(name = "thermometerId"), @JoinColumn(name = "timestamp")},
      joinColumns = @JoinColumn(name = "detectorType"))
  Set<AnomalyEntity> detectedAnomalies = new HashSet<>();

  public AnomalyDetectorTypeEntity(){}
  public AnomalyDetectorTypeEntity(ANOMALY_DETECTOR_TYPE detectorType) {
    this.detectorType = detectorType;
  }

  public void addDetectedAnomaly(AnomalyEntity anomalyEntity) {
    detectedAnomalies.add(anomalyEntity);
  }

  public ANOMALY_DETECTOR_TYPE getDetectorType() {
    return detectorType;
  }

  public Set<AnomalyEntity> getDetectedAnomalies() {
    return detectedAnomalies;
  }
}
