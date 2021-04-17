package dev.victor.paulo.startwarsAPI.persistence.converter;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.service.converter.PlanetToResponse;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DocumentToPlanetTest {

    @Test
    void should_convert() {
        PlanetDocument planetDocument = new PlanetDocument("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");

        Planet planet = DocumentToPlanet.convert(planetDocument);

        assertThat(planet).isNotNull();
        assertThat(planet.id()).isEqualTo(planet.id());
        assertThat(planet.name()).isEqualTo(planet.name());
        assertThat(planet.climate()).isEqualTo(planet.climate());
        assertThat(planet.terrain()).isEqualTo(planet.terrain());
    }

    @Test
    public void should_convert_many() {
        PlanetDocument document1 = new PlanetDocument("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
        PlanetDocument document2 = new PlanetDocument("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
        PlanetDocument document3 = new PlanetDocument("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
        List<PlanetDocument> documents = Arrays.asList(document1, document2, document3);

        List<Planet> planets = DocumentToPlanet.convert(documents);

        AssertionsForInterfaceTypes.assertThat(planets).containsExactly(
                new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert"),
                new Planet("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains"),
                new Planet("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests")
        );
    }
}