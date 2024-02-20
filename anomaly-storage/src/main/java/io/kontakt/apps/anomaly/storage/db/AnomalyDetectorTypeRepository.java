package io.kontakt.apps.anomaly.storage.db;

import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyDetectorTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnomalyDetectorTypeRepository extends
    PagingAndSortingRepository<AnomalyDetectorTypeEntity, ANOMALY_DETECTOR_TYPE>, JpaSpecificationExecutor<AnomalyDetectorTypeEntity> {

  List<AnomalyDetectorTypeEntity> findAll();


}