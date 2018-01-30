package red.mockers.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationData {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    private String token;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("signed_request")
    private String signedRequest;
    @JsonProperty("user_id")
    private String userId;
    
    public AuthenticationData() {
        
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getSignedRequest() {
        return signedRequest;
    }

    public void setSignedRequest(String signedRequest) {
        this.signedRequest = signedRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
}
