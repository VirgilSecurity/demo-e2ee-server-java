package data.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import data.model.User;

import java.util.List;

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
public final class UsersResponse {

    @JsonProperty("users")
    private final List<User> users;

    public UsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
