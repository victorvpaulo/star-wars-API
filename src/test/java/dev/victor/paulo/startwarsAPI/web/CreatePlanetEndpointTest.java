package dev.victor.paulo.startwarsAPI.web;

import dev.victor.paulo.startwarsAPI.service.CreatePlanetService;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreatePlanetEndpointTest {

    @MockBean
    private CreatePlanetService create;

    @Autowired
    private CreatePlanetEndpoint endpoint;

    @Test
    public void should_return_created_planet_and_status_code_201() {
        PlanetRequest planetRequest = new PlanetRequest("Tatooine", "Arid", "Desert");
        PlanetResponse expectedResponse = new PlanetResponse("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert", null);
        when(create.forData(planetRequest)).thenReturn(expectedResponse);

        ResponseEntity<PlanetResponse> response = endpoint.create(planetRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }
}
