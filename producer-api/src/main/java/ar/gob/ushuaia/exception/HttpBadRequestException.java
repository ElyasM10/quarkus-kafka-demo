package ar.gob.ushuaia.exception;

public class HttpBadRequestException extends RuntimeException{

    public HttpBadRequestException(String mensaje) {
        super(mensaje);
    }
}
