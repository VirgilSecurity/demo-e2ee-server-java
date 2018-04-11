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
public enum ResponseType {

    USERS_LIST("usersList"),
    MESSAGE("message");

    private final String type;

    ResponseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
