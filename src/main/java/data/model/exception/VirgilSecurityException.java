package data.model.exception;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/11/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public final class VirgilSecurityException extends RuntimeException {

    public VirgilSecurityException() {
    }

    public VirgilSecurityException(String message) {
        super(message);
    }

    public VirgilSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public VirgilSecurityException(Throwable cause) {
        super(cause);
    }

    public VirgilSecurityException(String message,
                                   Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
