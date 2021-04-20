package dev.victor.paulo.startwarsAPI.web;

import dev.victor.paulo.startwarsAPI.service.RemovePlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RemovePlanetEndpointTest {

    @MockBean
    private RemovePlanetService remove;

    @Autowired
    private RemovePlanetEndpoint endpoint;

    @Test
    void should_remove_planet_and_return_status_code_204() {
        String planetId = "539a7244d9d5e4dea11d4ffe";

        ResponseEntity<Void> response = endpoint.remove(planetId);

        verify(remove).byId(planetId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}