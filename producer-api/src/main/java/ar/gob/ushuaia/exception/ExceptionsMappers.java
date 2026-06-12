package ar.gob.ushuaia.exception;

import ar.gob.ushuaia.transferible.TransferibleException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import static jakarta.ws.rs.core.Response.Status.*;

public class ExceptionsMappers {

    @Inject
    Logger auditor;

    @ServerExceptionMapper
    public Response mapHttpNoContentException(HttpNoContentException x, @Context UriInfo uriInfo) {
        auditor.debug("mapHttpNoContentException");
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @ServerExceptionMapper
    public Response mapHttpConflictException(HttpConflictException x, @Context UriInfo uriInfo) {

        auditor.warn(" CONFLICT (409) - Mensaje: " + x.getMessage() + " - Path: " + uriInfo.getPath());

        TransferibleException body = new TransferibleException();
        body.setStatus(409);
        body.setTitle("Conflicto");
        body.setDetail(x.getMessage());
        body.setInstance(uriInfo.getPath());

        return Response.status(CONFLICT).type("application/json").header("warning", x.getMessage()).entity(body).build();

    }

    @ServerExceptionMapper
    public Response mapHttpForbiddenException(HttpForbiddenException x, @Context UriInfo uriInfo) {

        auditor.debug("mapHttpForbiddenExceptionNuevo: " + x.getMessage());
        TransferibleException body = new TransferibleException();
        body.setStatus(403);
        body.setTitle("Acceso denegado");
        body.setDetail(x.getMessage());
        body.setInstance(uriInfo.getPath());

        return Response.status(FORBIDDEN).type("application/json").header("warning", x.getMessage()).entity(body).build();

    }

    @ServerExceptionMapper
    public Response mapHttpBadRequestException(HttpBadRequestException x, @Context UriInfo uriInfo) {

        auditor.debug("mapHttpBadRequestException: " + x.getMessage());
        TransferibleException body = new TransferibleException();
        body.setStatus(400);
        body.setTitle("Solicitud incorrecta");
        body.setDetail(x.getMessage());
        body.setInstance(uriInfo.getPath());

        return Response.status(BAD_REQUEST)
                .header("warning", x.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

    @ServerExceptionMapper
    public Response mapHttpNotFoundException(HttpNotFoundException x, @Context UriInfo uriInfo) {

        auditor.debug("mapHttpNotFoundException: " + x.getMessage());
        TransferibleException body = new TransferibleException();
        body.setStatus(404);
        body.setTitle("No encontrado");
        body.setDetail(x.getMessage());
        body.setInstance(uriInfo.getPath());

        return Response.status(NOT_FOUND)
                .type("application/json")
                .header("warning", "No encontrado")
                .entity(body)
                .build();
    }

    @ServerExceptionMapper
    public Response mapHttpInternalServerErrorException(HttpInternalServerErrorException x, @Context UriInfo uriInfo) {
        auditor.error("Error interno del servidor: " + x.getMessage(), x);

        TransferibleException body = new TransferibleException();
        body.setStatus(500);
        body.setTitle("Error interno del servidor");
        body.setDetail(x.getMessage());
        body.setInstance(uriInfo.getPath());
        body.setType("about:blank");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type("application/json")
                .header("warning", "Error interno del servidor")
                .entity(body)
                .build();
    }

    @ServerExceptionMapper
    public Response mapHttpUnauthorizedException(HttpUnauthorizedException x, @Context UriInfo uriInfo) {
        auditor.error("No autorizado: " + x.getMessage(), x);

        TransferibleException body = new TransferibleException();
        body.setStatus(401);
        body.setTitle("No autorizado");
        body.setDetail(x.getMessage());
        body.setInstance(uriInfo.getPath());
        body.setType("about:blank");

        return Response.status(Response.Status.UNAUTHORIZED)
                .type("application/json")
                .header("Warning", "No autorizado")
                .entity(body)
                .build();
    }
}
