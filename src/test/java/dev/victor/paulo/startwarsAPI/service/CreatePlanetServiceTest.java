package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.external.dto.ExternalPlanet;
import dev.victor.paulo.startwarsAPI.service.collections.AllExternalPlanets;
import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreatePlanetServiceTest {

    public static final String PLANET_ID = "5399aba6e4b0ae375bfdca88";
    public static final String PLANET_NAME = "Tatooine";
    public static final String PLANET_CLIMATE = "Arid";
    public static final String PLANET_TERRAIN = "Desert";
    public static final PlanetRequest PLANET_REQUEST = new PlanetRequest(PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    public static final List<String> FILM_APPEARANCES = Arrays.asList(
            "http://swapi.dev/api/films/1/",
            "http://swapi.dev/api/films/3/",
            "http://swapi.dev/api/films/4/",
            "http://swapi.dev/api/films/5/",
            "http://swapi.dev/api/films/6/"
    );
    public static final int NUMBER_OF_FILM_APPEARANCES = FILM_APPEARANCES.size();

    @MockBean
    private AllPlanets allPlanets;

    @MockBean
    private AllExternalPlanets allExternalPlanets;

    @Autowired
    private CreatePlanetService createPlanet;

    @Test
    public void when_exists_external_planet_should_create_planet_with_film_appearances_and_return_planet_response() {
        when(allExternalPlanets.byName(PLANET_NAME))
                .thenReturn(externalPlanetWithAppearances(FILM_APPEARANCES));
        when(allPlanets.add(planetWithIdAndNumberOfFilmAppearances(null, NUMBER_OF_FILM_APPEARANCES)))
                .thenReturn(planetWithIdAndNumberOfFilmAppearances(PLANET_ID, NUMBER_OF_FILM_APPEARANCES));

        PlanetResponse planetResponse = createPlanet.forData(PLANET_REQUEST);


        verify(allExternalPlanets).byName(PLANET_NAME);
        verify(allPlanets).add(planetWithIdAndNumberOfFilmAppearances(null, NUMBER_OF_FILM_APPEARANCES));
        assertThat(planetResponse).isEqualTo(responseWithNumberOfFilmAppearances(NUMBER_OF_FILM_APPEARANCES));
    }

    @Test
    public void when_external_planet_not_exists_should_create_planet_without_film_appearances_and_return_planet_response() {
        when(allExternalPlanets.byName(PLANET_NAME))
                .thenReturn(Optional.empty());
        when(allPlanets.add(planetWithIdAndNumberOfFilmAppearances(null, null)))
                .thenReturn(planetWithIdAndNumberOfFilmAppearances(PLANET_ID, null));


        PlanetResponse planetResponse = createPlanet.forData(PLANET_REQUEST);


        verify(allExternalPlanets).byName(PLANET_NAME);
        verify(allPlanets).add(planetWithIdAndNumberOfFilmAppearances(null, null));
        assertThat(planetResponse).isEqualTo(responseWithNumberOfFilmAppearances(null));
    }

   @Test
   public void when_external_planet_has_no_films_should_create_planet_with_zero_film_appearances_and_return_planet_response() {
        when(allExternalPlanets.byName(PLANET_NAME))
                .thenReturn(externalPlanetWithAppearances(Collections.emptyList()));
        when(allPlanets.add(planetWithIdAndNumberOfFilmAppearances(null, 0)))
                .thenReturn(planetWithIdAndNumberOfFilmAppearances(PLANET_ID, 0));


        PlanetResponse planetResponse = createPlanet.forData(PLANET_REQUEST);


        verify(allExternalPlanets).byName(PLANET_NAME);
        verify(allPlanets).add(planetWithIdAndNumberOfFilmAppearances(null, 0));
        assertThat(planetResponse).isEqualTo(responseWithNumberOfFilmAppearances(0));
    }

    private PlanetResponse responseWithNumberOfFilmAppearances(Integer numberOfFilmAppearances) {
        return new PlanetResponse(PLANET_ID, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN, numberOfFilmAppearances);
    }

    private Planet planetWithIdAndNumberOfFilmAppearances(String id, Integer numberOfFilmAppearances) {
        return new Planet(id, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN, numberOfFilmAppearances);
    }

    private Optional<ExternalPlanet> externalPlanetWithAppearances(List<String> filmAppearances) {
        return Optional.of(new ExternalPlanet(PLANET_NAME, filmAppearances));
    }
}