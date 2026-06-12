package ar.gob.ushuaia.exception;

public class HttpForbiddenException extends RuntimeException {
    public HttpForbiddenException(String message) {
        super(message);
    }

    public HttpForbiddenException(){super();}

}
