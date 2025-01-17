package helloworld.secured_rest_api.quarkus.demo.service;

import helloworld.secured_rest_api.quarkus.demo.dto.GreetingDTO;

/**
 * This is an example of service layer
 */
public interface GreetingService {

    /**
     * Greeting
     * 
     * @return a greeting message
     */
    GreetingDTO greeting();

    /**
     * Greeting a person
     * 
     * @param name name of the person
     * @return a greeting message for that person
     */
    GreetingDTO greeting(String name);
}
