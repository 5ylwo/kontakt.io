package io.kontak.apps.anomaly.detector.config;

import io.kontak.apps.anomaly.detector.anomalyDetector.impl.AlwaysAnomalyAnomalyDetector;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClassConverter implements Converter<String, Class> {
  @Override
  public Class convert(String from) {
    try {
      return Class.forName(from);
    } catch (ClassNotFoundException e) {
      /* TODO exception and logging */
      return AlwaysAnomalyAnomalyDetector.class;
    }
  }
}