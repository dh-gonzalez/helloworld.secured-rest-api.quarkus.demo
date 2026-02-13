package helloworld.secured_rest_api.quarkus.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.dao.GreetingDAO;
import helloworld.secured_rest_api.quarkus.demo.dto.GreetingDTO;
import helloworld.secured_rest_api.quarkus.demo.service.GreetingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * This is an implementation example of service
 */
@ApplicationScoped
public class GreetingServiceImpl implements GreetingService {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** Greeting DAO service */
    @Inject
    private GreetingDAO _greetingDAO;

    @Override
    public GreetingDTO greeting() {
        return greeting("World");
    }

    @Override
    public GreetingDTO greeting(String name) {
        LOGGER.debug("Greeting has been requested for {}", name);

        String greetingTemplate = _greetingDAO.getGreetingTemplate();
        return new GreetingDTO(String.format(greetingTemplate, name));
    }
}
