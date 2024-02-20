package io.kontakt.apps.anomaly.storage.kafka;


import io.kontak.apps.event.Anomaly;
import io.kontakt.apps.anomaly.storage.services.CreateAnomalyService;
import java.util.function.Consumer;
import org.apache.kafka.streams.kstream.KStream;

public class AnomaliesListener implements Consumer<KStream<String, Anomaly>> {

    private final CreateAnomalyService createAnomalyService;

    public AnomaliesListener(CreateAnomalyService createAnomalyService) {
        this.createAnomalyService = createAnomalyService;
    }

    @Override
    public void accept(KStream<String, Anomaly> events) {
        events.foreach((id, anomaly) -> createAnomalyService.saveAnomaly(anomaly));
    }
}
