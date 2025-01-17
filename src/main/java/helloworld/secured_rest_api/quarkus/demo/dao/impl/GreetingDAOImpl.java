package helloworld.secured_rest_api.quarkus.demo.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.dao.GreetingDAO;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * This is an dummy example of DAO implementation
 */
@ApplicationScoped
public class GreetingDAOImpl implements GreetingDAO {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getGreetingTemplate() {
        LOGGER.debug("Greeting template has been requested");

        return "Hello, %s!";
    }
}
