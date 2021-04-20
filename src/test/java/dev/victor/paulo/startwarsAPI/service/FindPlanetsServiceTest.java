package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.exception.PlanetNotFoundException;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.PlanetFilters;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindPlanetsServiceTest {

    public static final Planet PLANET_1 = new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
    public static final Planet PLANET_2 = new Planet("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final Planet PLANET_3 = new Planet("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
    public static final PlanetResponse EXPECTED_PLANET_RESPONSE_1 = new PlanetResponse("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
    public static final PlanetResponse EXPECTED_PLANET_RESPONSE_2 = new PlanetResponse("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final PlanetResponse EXPECTED_PLANET_RESPONSE_3 = new PlanetResponse("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");

    @MockBean
    private AllPlanets allPlanets;

    @Autowired
    private FindPlanetsService findPlanets;

    @Test
    public void when_exists_data_byFilters_should_return_all_planets() {
        PlanetFilters filters = filters(null, null, null);
        when(allPlanets.byFilters(filters)).thenReturn(Arrays.asList(PLANET_1, PLANET_2, PLANET_3));

        List<PlanetResponse> planets = findPlanets.byFilters(filters);

        verify(allPlanets).byFilters(filters);
        assertThat(planets).containsExactly(EXPECTED_PLANET_RESPONSE_1, EXPECTED_PLANET_RESPONSE_2, EXPECTED_PLANET_RESPONSE_3);
    }

    @Test
    public void when_no_data_byFilters_should_return_empty_list() {
        PlanetFilters filters = filters("Tatooine", "Temperate", "Grasslands, Mountains");

        when(allPlanets.byFilters(filters)).thenReturn(Collections.emptyList());

        List<PlanetResponse> planets = findPlanets.byFilters(filters);

        verify(allPlanets).byFilters(filters);
        assertThat(planets).isEmpty();
    }

    @Test
    public void when_planet_exists_byId_should_return_planet(){
        when(allPlanets.byId(PLANET_1.id())).thenReturn(Optional.of(PLANET_1));

        PlanetResponse planet = findPlanets.byId(PLANET_1.id());

        assertThat(planet).isEqualTo(EXPECTED_PLANET_RESPONSE_1);
    }

    @Test
    public void when_planet_not_exists_byId_should_rise_exception(){
        String nonExistingId = "607a89fa7135a8d2cf3af7dd";
        when(allPlanets.byId(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(PlanetNotFoundException.class,
                () -> findPlanets.byId(nonExistingId));
    }

    private PlanetFilters filters(String name, String climate, String terrain) {
        return new PlanetFilters(name, climate, terrain);
    }
}
