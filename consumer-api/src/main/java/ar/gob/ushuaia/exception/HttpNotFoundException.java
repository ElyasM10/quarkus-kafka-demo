package ar.gob.ushuaia.exception;

public class HttpNotFoundException extends RuntimeException{

    public HttpNotFoundException(String mensaje) {
        super(mensaje);
    }
}
