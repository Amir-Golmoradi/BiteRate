package dev.amirgol.biterate.exception;

public class BiteRateException extends RuntimeException {
    public BiteRateException(String message) {
        super(message);
    }

    public BiteRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
