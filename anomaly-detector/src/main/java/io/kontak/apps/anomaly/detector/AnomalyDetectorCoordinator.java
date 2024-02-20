package io.kontak.apps.anomaly.detector;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.anomaly.detector.config.Config;
import io.kontak.apps.anomaly.detector.config.Config.AnomalyDetectorType;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class AnomalyDetectorCoordinator {
  private final HashMap<AnomalyDetectorType, HashMap<String, AnomalyDetector>> anomalyDetectorMap = new HashMap();

  public AnomalyDetectorCoordinator(Config config) {

    config.anomalyDetectorTypes().stream()
        .filter(anomalyDetectorConfig -> anomalyDetectorConfig.enabled())
        .forEach(anomalyDetectorConfig -> anomalyDetectorMap.put(anomalyDetectorConfig, new HashMap<>()));
  }

  public List<Anomaly> detectAnomaliesForOneThermometer(String thermometerId, TemperatureReading temperatureReading) {

    return anomalyDetectorMap.entrySet().stream()
        .flatMap(anomalyDetectorEntry -> detectAnomaliesForOneAnomalyDetector(anomalyDetectorEntry.getKey(), anomalyDetectorEntry.getValue(), thermometerId, temperatureReading))
        .collect(Collectors.toList());
  }


  private Stream<Anomaly> detectAnomaliesForOneAnomalyDetector(AnomalyDetectorType anomalyDetectorConfig, HashMap<String, AnomalyDetector> thermometerMap, String thermometerId, TemperatureReading temperatureReading) {

    AnomalyDetector anomalyDetector = getOrCreateAnomalyDetector(anomalyDetectorConfig, temperatureReading.thermometerId(), thermometerMap);

    return anomalyDetector.apply(temperatureReading);
  }

  private AnomalyDetector getOrCreateAnomalyDetector(AnomalyDetectorType anomalyDetectorConfig, String thermometerId, HashMap<String, AnomalyDetector> thermometerMap) {

    AnomalyDetector anomalyDetector = thermometerMap.get(thermometerId);

    if (anomalyDetector == null) {
      anomalyDetector = AnomalyDetectorFactory.buildAnomalyDetector(anomalyDetectorConfig);
      thermometerMap.put(thermometerId, anomalyDetector);
    }

    return anomalyDetector;
  }
}
