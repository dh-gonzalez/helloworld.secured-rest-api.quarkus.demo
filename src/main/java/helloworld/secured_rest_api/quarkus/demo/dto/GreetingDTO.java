package helloworld.secured_rest_api.quarkus.demo.dto;

/**
 * A greeting message
 */
public class GreetingDTO {

    /**
     * Greeting message
     */
    private String greeting;

    /**
     * Constructor
     */
    public GreetingDTO() {
        // nothing to do
    }

    /**
     * Constructor
     * 
     * @param greeting greeting message
     */
    public GreetingDTO(String greeting) {
        this.greeting = greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return this.greeting;
    }
}
