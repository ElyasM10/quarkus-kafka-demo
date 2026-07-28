package ar.gob.ushuaia.recurso;

import ar.gob.ushuaia.producer.MessageRequest;
import ar.gob.ushuaia.producer.MessageResponse;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    // Emitter conectado al canal "messages-out" (ver application.properties), que lo mapea al topic Kafka "test-topic"
    @Inject
    @Channel("messages-out")
    Emitter<Record<String, String>> emitter;

    @Inject
    Logger auditor;

    // Publica el mensaje recibido por POST como un Record key/value en Kafka a través del emitter
    @POST
    public Response send(MessageRequest request) {
        auditor.debugf("Request recibido — key: %s | payload: %s", request.key(), request.payload());

        try {
            auditor.debugf("Enviando a Kafka — topic: test-topic | key: %s", request.key());
            emitter.send(Record.of(request.key(), request.payload()));
            auditor.infof("Mensaje enviado exitosamente — key: %s | payload: %s", request.key(), request.payload());
        } catch (Exception e) {
            auditor.errorf(e, "Error al enviar mensaje a Kafka — key: %s", request.key());
            return Response.serverError().entity(new MessageResponse("error", request.key(), e.getMessage())).build();
        }

        return Response.accepted().entity(new MessageResponse("sent", request.key(), request.payload())).build();
    }
}
