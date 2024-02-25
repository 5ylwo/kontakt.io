package io.kontak.apps.anomaly.detector;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.anomaly.detector.config.detectorTypes.AnomalyDetectorTypeConfig;

public class AnomalyDetectorFactory {
  public static AnomalyDetector buildAnomalyDetector(AnomalyDetectorTypeConfig anomalyDetectorConfig) {

    try {
      return anomalyDetectorConfig.clazz().getConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
