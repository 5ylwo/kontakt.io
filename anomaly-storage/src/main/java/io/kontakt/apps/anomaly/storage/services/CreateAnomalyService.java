package io.kontakt.apps.anomaly.storage.services;

import io.kontak.apps.detector.AnomalyDetectorType;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import io.kontakt.apps.anomaly.storage.db.AnomalyDetectorTypeRepository;
import io.kontakt.apps.anomaly.storage.db.AnomalyEntityRepository;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyDetectorTypeEntity;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyEntity;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyEntityId;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateAnomalyService {
  private final AnomalyDetectorTypeRepository anomalyDetectorTypeRepository;
  private final AnomalyEntityRepository anomalyEntityRepository;

  public CreateAnomalyService(AnomalyDetectorTypeRepository anomalyDetectorTypeRepository, AnomalyEntityRepository anomalyEntityRepository) {
    this.anomalyEntityRepository = anomalyEntityRepository;
    this.anomalyDetectorTypeRepository = anomalyDetectorTypeRepository;
  }

  @Transactional
  public void saveAnomaly(Anomaly anomaly) {
    AnomalyDetectorTypeEntity anomalyDetectorTypeEntity = findOrCreateDetectorType(anomaly.anomalyDetectorType());
    AnomalyEntity anomalyEntity = findOrCreateAnomaly(anomaly.temperatureReading());

    anomalyEntityRepository.save(anomalyEntity);

    anomalyDetectorTypeEntity.addDetectedAnomaly(anomalyEntity);
    anomalyDetectorTypeRepository.save(anomalyDetectorTypeEntity);
  }

  private AnomalyDetectorTypeEntity findOrCreateDetectorType (
      AnomalyDetectorType anomalyDetectorType) {
    return anomalyDetectorTypeRepository.findById(anomalyDetectorType)
        .orElseGet(() -> new AnomalyDetectorTypeEntity(anomalyDetectorType));
  }

  private AnomalyEntity findOrCreateAnomaly (TemperatureReading temperatureReading) {
    return anomalyEntityRepository
        .findById(new AnomalyEntityId(temperatureReading.thermometerId(), temperatureReading.timestamp()))
        .orElseGet(() ->
            new AnomalyEntity(temperatureReading.thermometerId(), temperatureReading.timestamp(), temperatureReading.temperature(), temperatureReading.roomId()));
  }
}
