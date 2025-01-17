package helloworld.secured_rest_api.quarkus.demo.dto;

/**
 * User login and password for authentication
 */
public class AuthenticationRequestDTO {

    /** user name */
    private String username;

    /** user password */
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
