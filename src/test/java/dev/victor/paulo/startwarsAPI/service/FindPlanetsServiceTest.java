package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindPlanetsServiceTest {

    public static final Planet PLANET_1 = new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
    public static final Planet PLANET_2 = new Planet("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final Planet PLANET_3 = new Planet("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
    public static final PlanetResponse EXPECTED_RESPONSE_1 = new PlanetResponse("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
    public static final PlanetResponse EXPECTED_RESPONSE_2 = new PlanetResponse("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final PlanetResponse EXPECTED_RESPONSE_3 = new PlanetResponse("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");

    @MockBean
    private AllPlanets allPlanets;

    @Autowired
    private FindPlanetsService service;

    @Test
    public void when_exists_data_findAll_should_return_all_planets() {
        when(allPlanets.getAll()).thenReturn(Arrays.asList(PLANET_1, PLANET_2, PLANET_3));

        List<PlanetResponse> allPlanets = service.findAll();


        assertThat(allPlanets).containsExactly(EXPECTED_RESPONSE_1, EXPECTED_RESPONSE_2, EXPECTED_RESPONSE_3);
    }

    @Test
    public void when_no_data_findAll_should_return_empty_list() {
        when(allPlanets.getAll()).thenReturn(Collections.emptyList());

        List<PlanetResponse> allPlanets = service.findAll();

        assertThat(allPlanets).isEmpty();
    }
}
