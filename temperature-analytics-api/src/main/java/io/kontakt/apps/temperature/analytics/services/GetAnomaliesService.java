package io.kontakt.apps.temperature.analytics.services;

import io.kontak.apps.detector.AnomalyDetectorType;
import io.kontakt.apps.anomaly.storage.db.AnomalyDetectorTypeRepository;
import io.kontakt.apps.anomaly.storage.db.AnomalyEntityRepository;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyDetectorTypeEntity;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyEntity;
import java.util.List;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class GetAnomaliesService {
  private final AnomalyDetectorTypeRepository anomalyDetectorTypeRepository;
  private final AnomalyEntityRepository anomalyEntityRepository;

  public GetAnomaliesService(AnomalyDetectorTypeRepository anomalyDetectorTypeRepository, AnomalyEntityRepository anomalyEntityRepository) {
    this.anomalyEntityRepository = anomalyEntityRepository;
    this.anomalyDetectorTypeRepository = anomalyDetectorTypeRepository;
  }


  public List<String> getThermometersAboveThreshold(Long threshold) {
    return anomalyEntityRepository.getThermometersAboveThreshold(threshold);
  }

  public List<String> getThermometersForDetectorAboveThreshold(
      AnomalyDetectorType anomalyDetectorType, Long threshold) {
    return anomalyEntityRepository.getThermometersForDetectorAboveThreshold(anomalyDetectorType, threshold);
  }

  public List<AnomalyEntity> getAnomaliesByRoomId(String roomId) {
    return anomalyEntityRepository.findAll(hasRoomId(roomId));
  }

  public List<AnomalyEntity> getDetectorTypeAnomaliesByRoomId(
      AnomalyDetectorType anomalyDetectorType, String roomId) {

    Specification<AnomalyEntity> specification = hasRoomId(roomId)
        .and(isDetectedBy(anomalyDetectorType));

    return anomalyEntityRepository.findAll(specification);
  }

  public List<AnomalyEntity> getAnomaliesByThermometerId(String thermometerId) {
    return anomalyEntityRepository.findAll(hasThermometerId(thermometerId));
  }

  public List<AnomalyEntity> getDetectorTypeAnomaliesByThermometerId(
      AnomalyDetectorType anomalyDetectorType, String thermometerId) {
    Specification<AnomalyEntity> specification = hasThermometerId(thermometerId)
        .and(isDetectedBy(anomalyDetectorType));

    return anomalyEntityRepository.findAll(specification);
  }

  public static Specification<AnomalyEntity> hasRoomId(String roomId) {
    return (root, query, cb) ->
        cb.equal(root.get("anomalyEntityId").<String>get("roomId"), roomId);
  }

  public static Specification<AnomalyEntity> hasThermometerId(String thermometerId) {
    return (root, query, cb) ->
        cb.equal(root.get("anomalyEntityId").<String>get("thermometerId"), thermometerId);
  }

  public static Specification<AnomalyEntity> isDetectedBy(AnomalyDetectorType anomalyDetectorType) {
    return (root, query, criteriaBuilder) -> {
      Join<AnomalyDetectorTypeEntity, AnomalyEntity> anomalyToDetectorType = root.join("detectedBy");
      return criteriaBuilder.equal(anomalyToDetectorType.get("detectorType"), anomalyDetectorType);
    };
  }

  public List<AnomalyDetectorTypeEntity> getAllDetectors() {
    return anomalyDetectorTypeRepository.findAll();
  }

  public List<AnomalyEntity> getAllAnomalies() {
    return anomalyEntityRepository.findAll();
  }
}
