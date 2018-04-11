package data.model;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/10/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public final class DefaultUser implements User {

    private final String name;

    public DefaultUser(String name) {
        this.name = name;
    }

    @Override public String getName() {
        return name;
    }
}
