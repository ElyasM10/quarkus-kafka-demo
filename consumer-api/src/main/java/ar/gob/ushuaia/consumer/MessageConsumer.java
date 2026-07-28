package ar.gob.ushuaia.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MessageConsumer {

    private static final Logger LOG = Logger.getLogger(MessageConsumer.class);

    private final List<ReceivedMessage> messages = new ArrayList<>();

    // Escucha el canal "messages-in" (ver application.properties), mapeado al topic Kafka "test-topic", y guarda cada mensaje recibido
    @Incoming("messages-in")
    public void consume(Record<String, String> record) {
        LOG.debugf("Mensaje recibido desde Kafka — key: %s | payload: %s", record.key(), record.value());

        ReceivedMessage msg = new ReceivedMessage(record.key(), record.value(), Instant.now().toString());
        messages.add(msg);

        LOG.infof("Mensaje procesado y almacenado — key: %s | total acumulado: %d", record.key(), messages.size());
    }

    // Devuelve una copia inmutable de todos los mensajes acumulados desde Kafka
    public List<ReceivedMessage> getMessages() {
        LOG.debugf("Consultando mensajes acumulados — total: %d", messages.size());
        return List.copyOf(messages);
    }
}
