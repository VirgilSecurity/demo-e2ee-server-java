package data.model.response;

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
public class UsersResponse {

    private final List<User> users;

    public UsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
