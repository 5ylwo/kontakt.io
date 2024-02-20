package io.kontakt.apps.anomaly.storage.db;

import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyEntity;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyEntityId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnomalyEntityRepository extends
    PagingAndSortingRepository<AnomalyEntity, AnomalyEntityId>, JpaSpecificationExecutor<AnomalyEntity> {


  List<AnomalyEntity> findAll();

  @Query("SELECT ae.anomalyEntityId.thermometerId FROM AnomalyEntity ae GROUP BY ae.anomalyEntityId.thermometerId HAVING count(*) > ?1")
  List<String> getThermometersAboveThreshold(Long threshold);

  @Query("SELECT da.anomalyEntityId.thermometerId FROM AnomalyDetectorTypeEntity adt inner join adt.detectedAnomalies da WHERE adt.detectorType = ?1 GROUP BY da.anomalyEntityId.thermometerId HAVING count(*) > ?2")
  List<String> getThermometersForDetectorAboveThreshold(ANOMALY_DETECTOR_TYPE anomalyDetectorType, Long threshold);

}