package data.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/16/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public class TokenResponse {

    @JsonProperty("access_token")
    private final String token;

    public TokenResponse(String token) {

        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
