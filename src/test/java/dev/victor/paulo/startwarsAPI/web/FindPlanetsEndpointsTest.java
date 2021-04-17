package dev.victor.paulo.startwarsAPI.web;

import dev.victor.paulo.startwarsAPI.service.FindPlanetsService;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindPlanetsEndpointsTest {

    @MockBean
    private FindPlanetsService find;

    @Autowired
    private FindPlanetsEndpoints findPlanetsEndpoints;

    @Test
    public void when_exists_data_findAll_should_return_all_planets_and_status_code_200() {
        PlanetResponse expectedResponse1 = new PlanetResponse("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
        PlanetResponse expectedResponse2 = new PlanetResponse("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
        PlanetResponse expectedResponse3 = new PlanetResponse("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
        when(find.findAll()).thenReturn(Arrays.asList(expectedResponse1, expectedResponse2, expectedResponse3));

        ResponseEntity<List<PlanetResponse>> response = findPlanetsEndpoints.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(expectedResponse1, expectedResponse2, expectedResponse3);
    }

    @Test
    public void when_no_data_findAll_should_return_empty_list_and_status_code_200() {
        when(find.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<PlanetResponse>> response = findPlanetsEndpoints.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }
}