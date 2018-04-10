package data.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public final class GetTokenRequest {

    @JsonProperty("google_token")
    private String googleToken;

    public GetTokenRequest() {
    }

    public GetTokenRequest(String googleToken) {
        this.googleToken = googleToken;
    }

    public String getGoogleToken() {
        return googleToken;
    }
}
