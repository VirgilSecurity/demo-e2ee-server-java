package data.model.response;

import data.model.NetworkError;
import data.model.NetworkErrorType;

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
public final class NetworkErrorResponse implements NetworkError {

    private final int code;
    private final String message;

    public NetworkErrorResponse(NetworkErrorType errorType) {
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
