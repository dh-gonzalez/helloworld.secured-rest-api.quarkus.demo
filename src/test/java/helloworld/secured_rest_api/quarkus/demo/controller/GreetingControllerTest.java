package helloworld.secured_rest_api.quarkus.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import helloworld.secured_rest_api.quarkus.demo.dto.GreetingDTO;
import helloworld.secured_rest_api.quarkus.demo.service.GreetingService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

/**
 * Unit test class of controller GreetingController
 */
@QuarkusTest
public class GreetingControllerTest {

    // The services to be mocked
    @InjectMock
    private GreetingService greetingService;

    // The controller to be tested
    @Inject
    private GreetingController greetingController;

    @Test
    public void test_anonymousGreeting_Nominal() {

        // Test data
        GreetingDTO greetingDTO = new GreetingDTO();
        greetingDTO.setGreeting("Hello, World!");

        // Mocks behaviour
        when(greetingService.greeting()).thenReturn(greetingDTO);

        // Call service method to test
        Uni<GreetingDTO> response = greetingController.greeting();
        // Retrieve asynchronous result and check expected result
        GreetingDTO responseGreetingDTO = response.await().indefinitely();
        assertEquals(greetingDTO.getGreeting(), responseGreetingDTO.getGreeting());

        // Check mocks have been correctly called
        verify(greetingService).greeting();
    }

    @Test
    public void test_securedGreeting_Nominal() {

        // Test data
        GreetingDTO greetingDTO = new GreetingDTO();
        greetingDTO.setGreeting("Hello, David!");

        // Mocks behaviour
        when(greetingService.greeting("David")).thenReturn(greetingDTO);

        // Call service method to test
        Uni<GreetingDTO> response = greetingController.greeting("David");
        // Retrieve asynchronous result and check expected result
        GreetingDTO responseGreetingDTO = response.await().indefinitely();
        assertEquals(greetingDTO.getGreeting(), responseGreetingDTO.getGreeting());

        // Check mocks have been correctly called
        verify(greetingService).greeting("David");
    }
}
