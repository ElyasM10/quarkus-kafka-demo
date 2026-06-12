package ar.gob.ushuaia.exception;

public class HttpConflictException extends RuntimeException{

    public HttpConflictException(String mensaje) {
        super(mensaje);
    }
}
