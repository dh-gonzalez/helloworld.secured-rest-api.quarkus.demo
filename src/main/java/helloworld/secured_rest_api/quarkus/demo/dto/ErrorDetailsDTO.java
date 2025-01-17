package helloworld.secured_rest_api.quarkus.demo.dto;

/**
 * Provide details about exception thrown
 */
public class ErrorDetailsDTO {

    /**
     * Exception that has been thrown
     */
    private String exception;

    /**
     * Exception message
     */
    private String message;

    /**
     * Constructor
     * 
     * @param exception exception name
     * @param message exception message
     */
    public ErrorDetailsDTO(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return this.exception;
    }

    public String getMessage() {
        return this.message;
    }
}
