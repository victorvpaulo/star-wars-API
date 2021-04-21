package dev.victor.paulo.startwarsAPI.service.converter;

import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PlanetToResponseTest {

    @Test
    public void should_convert() {
        Planet planet = new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert", 5);

        PlanetResponse response = PlanetToResponse.convert(planet);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(planet.id());
        assertThat(response.name()).isEqualTo(planet.name());
        assertThat(response.climate()).isEqualTo(planet.climate());
        assertThat(response.terrain()).isEqualTo(planet.terrain());
        assertThat(response.numberOfFilmAppearances()).isEqualTo(planet.numberOfFilmAppearances());
    }

    @Test
    public void should_convert_many() {
        Planet planet1 = new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert", null);
        Planet planet2 = new Planet("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains", null);
        Planet planet3 = new Planet("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests", null);
        List<Planet> planets = Arrays.asList(planet1, planet2, planet3);

        List<PlanetResponse> responses = PlanetToResponse.convert(planets);

        assertThat(responses).containsExactly(
            new PlanetResponse("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert", null),
            new PlanetResponse("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains", null),
            new PlanetResponse("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests", null)
        );
    }
}