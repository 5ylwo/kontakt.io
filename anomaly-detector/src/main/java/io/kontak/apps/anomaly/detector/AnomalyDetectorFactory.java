package io.kontak.apps.anomaly.detector;

import io.kontak.apps.anomaly.detector.anomalyDetector.AnomalyDetector;
import io.kontak.apps.anomaly.detector.config.Config.AnomalyDetectorType;

public class AnomalyDetectorFactory {
  public static AnomalyDetector buildAnomalyDetector(AnomalyDetectorType anomalyDetectorConfig) {

    try {
      return anomalyDetectorConfig.clazz().getConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
