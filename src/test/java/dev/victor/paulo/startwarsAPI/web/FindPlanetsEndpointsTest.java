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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindPlanetsEndpointsTest {

    public static final PlanetResponse EXPECTED_RESPONSE_1 = new PlanetResponse("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
    public static final PlanetResponse EXPECTED_RESPONSE_2 = new PlanetResponse("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final PlanetResponse EXPECTED_RESPONSE_3 = new PlanetResponse("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
    @MockBean
    private FindPlanetsService find;

    @Autowired
    private FindPlanetsEndpoints findPlanetsEndpoints;

    @Test
    public void when_exists_data_findByFilters_should_return_all_planets_and_status_code_200() {
        PlanetFilters filters = filters(null, null, null);

        when(find.byFilters(filters)).thenReturn(Arrays.asList(EXPECTED_RESPONSE_1, EXPECTED_RESPONSE_2, EXPECTED_RESPONSE_3));

        ResponseEntity<List<PlanetResponse>> response = findPlanetsEndpoints.findByFilters(null, null, null);

        verify(find).byFilters(filters);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(EXPECTED_RESPONSE_1, EXPECTED_RESPONSE_2, EXPECTED_RESPONSE_3);
    }

    @Test
    public void when_no_data_findByFilters_should_return_empty_list_and_status_code_200() {
        PlanetFilters filters = filters("Tatooine", "Temperate", "Grasslands, Mountains");
        when(find.byFilters(filters)).thenReturn(Collections.emptyList());

        ResponseEntity<List<PlanetResponse>> response = findPlanetsEndpoints.findByFilters("Tatooine", "Temperate", "Grasslands, Mountains");

        verify(find).byFilters(filters);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void when_planet_exists_findById_should_return_planet_and_status_code_200() {
        when(find.byId(EXPECTED_RESPONSE_1.id())).thenReturn(Optional.of(EXPECTED_RESPONSE_1));

        ResponseEntity<PlanetResponse> response = findPlanetsEndpoints.findById(EXPECTED_RESPONSE_1.id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(EXPECTED_RESPONSE_1);
    }

    @Test
    public void when_planet_not_exists_findById_should_return_status_code_404() {
        String nonExistingId = "607a89fa7135a8d2cf3af7dd";
        when(find.byId(nonExistingId)).thenReturn(Optional.empty());

        ResponseEntity<PlanetResponse> response = findPlanetsEndpoints.findById(nonExistingId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private PlanetFilters filters(String name, String climate, String terrain) {
        return new PlanetFilters(name, climate, terrain);
    }
}