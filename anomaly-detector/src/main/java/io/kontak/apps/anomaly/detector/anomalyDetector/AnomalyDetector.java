package io.kontak.apps.anomaly.detector.anomalyDetector;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.function.Function;
import java.util.stream.Stream;

public interface AnomalyDetector extends Function<TemperatureReading, Stream<Anomaly>> {



}
