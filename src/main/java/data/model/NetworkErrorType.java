package data.model;

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
public enum NetworkErrorType {

    GET_TOKEN_REQUEST(new Pair<>(1400, "Format of get token request you've sent is wrong")),
    GOOGLE_TOKEN_FORMAT(new Pair<>(1400, "Format of google token you've sent is wrong")),
    GOOGLE_TOKEN_VERIFICATION(new Pair<>(1401, "Verification of google token you've sent has been failed"));

    private final Pair<Integer, String> error;

    NetworkErrorType(Pair<Integer, String> error) {
        this.error = error;
    }

    public Pair<Integer, String> getError() {
        return error;
    }

    public int getCode() {
        return error.getLeft();
    }

    public String getMessage() {
        return error.getRight();
    }
}
