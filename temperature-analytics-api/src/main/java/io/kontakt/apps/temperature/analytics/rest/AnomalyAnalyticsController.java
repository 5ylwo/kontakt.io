package io.kontakt.apps.temperature.analytics.rest;

import io.kontak.apps.detector.ANOMALY_DETECTOR_TYPE;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyDetectorTypeEntity;
import io.kontakt.apps.anomaly.storage.db.entities.AnomalyEntity;
import io.kontakt.apps.temperature.analytics.services.GetAnomaliesService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("temperature-analytics/")
public class AnomalyAnalyticsController {

  private final GetAnomaliesService getAnomaliesService;

  public AnomalyAnalyticsController(GetAnomaliesService getAnomaliesService) {
    this.getAnomaliesService = getAnomaliesService;
  }


  @GetMapping("/V1/getAllAnomalies")
  @ResponseBody
  public List<AnomalyEntity> getAllAnomalies() {
    return getAnomaliesService.getAllAnomalies();
  }

  @GetMapping("/V1/getAllDetectors")
  @ResponseBody
  public List<AnomalyDetectorTypeEntity> getAllDetectors() {
    return getAnomaliesService.getAllDetectors();
  }

  @GetMapping("/V1/getThermometersAboveThreshold")
  public List<String> getThermometersAboveThreshold(@RequestParam(defaultValue = "0") Long threshold) {
    return getAnomaliesService.getThermometersAboveThreshold(threshold);
  }

  @GetMapping("/V1/{anomalyDetectorType}/getThermometersAboveThreshold")
  public List<String> getThermometersForDetectorAboveThreshold(
      @PathVariable ANOMALY_DETECTOR_TYPE anomalyDetectorType, @RequestParam(defaultValue = "0") Long threshold) {
    return getAnomaliesService.getThermometersForDetectorAboveThreshold(anomalyDetectorType, threshold);
  }

  @GetMapping("/V1/{anomalyDetectorType}/forRoomId/{roomId}")
  public List<AnomalyEntity> getAnomaliesForDetectorTypeAndRoomId(
      @PathVariable ANOMALY_DETECTOR_TYPE anomalyDetectorType, @PathVariable String roomId) {
    return getAnomaliesService.getDetectorTypeAnomaliesByRoomId(anomalyDetectorType, roomId);
  }

  @GetMapping("/V1/{anomalyDetectorType}/forThermometerId/{thermometerId}")
  public List<AnomalyEntity> getAnomaliesForDetectorTypeAndThermometerId(
      @PathVariable ANOMALY_DETECTOR_TYPE anomalyDetectorType, @PathVariable String thermometerId) {
    return getAnomaliesService.getDetectorTypeAnomaliesByThermometerId(anomalyDetectorType, thermometerId);
  }

  @GetMapping("/V1/forRoomId/{roomId}")
  public List<AnomalyEntity> getAnomaliesForRoomId(@PathVariable String roomId) {
    return getAnomaliesService.getAnomaliesByRoomId(roomId);
  }
  @GetMapping("/V1/forThermometerId/{thermometerId}")
  public List<AnomalyEntity> getAnomaliesForThermometerId(@PathVariable String thermometerId) {
    return getAnomaliesService.getAnomaliesByThermometerId(thermometerId);
  }
}


