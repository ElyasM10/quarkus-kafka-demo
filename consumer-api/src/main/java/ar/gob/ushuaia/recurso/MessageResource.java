package ar.gob.ushuaia.recurso;

import ar.gob.ushuaia.consumer.MessageConsumer;
import ar.gob.ushuaia.consumer.ReceivedMessage;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    MessageConsumer consumer;

    @Inject
    Logger log;

    @GET
    public List<ReceivedMessage> list() {
        List<ReceivedMessage> messages = consumer.getMessages();
        log.infof("GET /messages — devolviendo %d mensajes", messages.size());
        return messages;
    }
}
