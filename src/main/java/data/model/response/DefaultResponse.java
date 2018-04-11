package data.model.response;

import data.model.ResponseType;

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
public final class DefaultResponse<T> implements Response {

    private String type;
    private T responseObject;

    public DefaultResponse(ResponseType type, T responseObject) {
        this.type = type.getType();
        this.responseObject = responseObject;
    }

    @Override public String getType() {
        return type;
    }

    @Override public T getResponseObject() {
        return responseObject;
    }
}
