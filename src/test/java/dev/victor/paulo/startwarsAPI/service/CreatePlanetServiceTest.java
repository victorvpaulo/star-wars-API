package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreatePlanetServiceTest {

    public static final String PLANET_ID = "5399aba6e4b0ae375bfdca88";
    public static final String PLANET_NAME = "Tatooine";
    public static final String PLANET_CLIMATE = "Arid";
    public static final String PLANET_TERRAIN = "Desert";

    @MockBean
    private AllPlanets allPlanets;

    @Autowired
    private CreatePlanetService createPlanet;

    @Test
    public void should_create_planet_and_return_planet_response() {
        when(allPlanets.add(planetToCreate())).thenReturn(expectedCreatedPlanet());

        PlanetResponse planetResponse = createPlanet.forData(request());

        verify(allPlanets).add(planetToCreate());
        assertThat(planetResponse).isEqualTo(expectedResponse());
    }

    private PlanetResponse expectedResponse() {
        return new PlanetResponse(PLANET_ID, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }

    private PlanetRequest request() {
        return new PlanetRequest(PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }

    private Planet expectedCreatedPlanet() {
        return new Planet(PLANET_ID, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }

    private Planet planetToCreate() {
        return new Planet(null, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }
}