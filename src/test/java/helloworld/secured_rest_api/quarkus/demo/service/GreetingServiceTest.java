package helloworld.secured_rest_api.quarkus.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import helloworld.secured_rest_api.quarkus.demo.dao.impl.GreetingDAOImpl;
import helloworld.secured_rest_api.quarkus.demo.service.impl.GreetingServiceImpl;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

/**
 * Unit test class of service GreetingService
 */
@QuarkusTest
public class GreetingServiceTest {

    // The service to be mocked
    @InjectMock
    private GreetingDAOImpl greetingDAO;

    // The service to be tested
    @Inject
    private GreetingServiceImpl greetingService;

    @Test
    public void test_anonymousGreeting_Nominal() {

        // Mock behaviour
        Mockito.when(greetingDAO.getGreetingTemplate()).thenReturn("Bonjour, %s!");

        // Test nominal case
        assertEquals("Bonjour, World!", greetingService.greeting().getGreeting());
    }

    @Test
    public void test_securedGreeting_Nominal() {

        // Mock behaviour
        Mockito.when(greetingDAO.getGreetingTemplate()).thenReturn("Bonjour, %s!");

        // Test nominal case
        assertEquals("Bonjour, David!", greetingService.greeting("David").getGreeting());
    }
}
