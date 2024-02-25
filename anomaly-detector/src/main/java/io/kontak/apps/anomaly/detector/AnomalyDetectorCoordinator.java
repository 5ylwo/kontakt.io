package io.kontak.apps.anomaly.detector;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.anomaly.detector.config.detectorTypes.AnomalyDetectorTypeConfig;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AnomalyDetectorCoordinator {

  private final AnomalyDetectorTypeConfig anomalyDetectorTypeConfig;
  private final HashMap<String, AnomalyDetector> thermometerIdToAnomalyDetector = new HashMap<>();
  public AnomalyDetectorCoordinator(AnomalyDetectorTypeConfig anomalyDetectorTypeConfig) {

    this.anomalyDetectorTypeConfig = anomalyDetectorTypeConfig;
  }

  public List<Anomaly> detectAnomaliesForOneThermometer(TemperatureReading temperatureReading) {
    AnomalyDetector anomalyDetector = getOrCreateAnomalyDetector(temperatureReading.thermometerId());

    return anomalyDetector.apply(temperatureReading).collect(Collectors.toList());
  }

  private AnomalyDetector getOrCreateAnomalyDetector(String thermometerId) {
    AnomalyDetector anomalyDetector = thermometerIdToAnomalyDetector.get(thermometerId);

    if (anomalyDetector == null) {
      anomalyDetector = AnomalyDetectorFactory.buildAnomalyDetector(anomalyDetectorTypeConfig);
      thermometerIdToAnomalyDetector.put(thermometerId, anomalyDetector);
    }

    return anomalyDetector;
  }
}
