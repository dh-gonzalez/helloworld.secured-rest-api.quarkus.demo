package helloworld.secured_rest_api.quarkus.demo.dao;

/**
 * This is an example of DAO layer
 */
public interface GreetingDAO {

    /**
     * Get greeting template
     * 
     * @return the greeting template
     */
    String getGreetingTemplate();
}
