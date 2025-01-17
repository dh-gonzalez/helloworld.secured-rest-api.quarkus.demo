package helloworld.secured_rest_api.quarkus.demo.dto;

/**
 * Representation of a generated token
 */
public class TokenDTO {

    /** Token value */
    private String token;

    /** Token type */
    private String type;

    /** Number of seconds token is valid */
    private long expiresInSeconds;

    /**
     * Constructor
     * 
     * @param token token value
     * @param type token type
     * @param expiresInSeconds Number of seconds token is valid
     */
    public TokenDTO(String token, String type, long expiresInSeconds) {
        this.token = token;
        this.type = type;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExpiresInSeconds() {
        return this.expiresInSeconds;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }
}
