package ar.gob.ushuaia.exception;

public class HttpInternalServerErrorException extends RuntimeException {

    public HttpInternalServerErrorException(String mensaje) {
        super(mensaje);
    }

    public HttpInternalServerErrorException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
