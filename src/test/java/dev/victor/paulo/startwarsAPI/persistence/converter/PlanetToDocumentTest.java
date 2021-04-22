package dev.victor.paulo.startwarsAPI.persistence.converter;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlanetToDocumentTest {

    @Test
    void should_convert() {
        Planet planet = new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert", null);

        PlanetDocument planetDocument = PlanetToDocument.convert(planet);

        assertThat(planetDocument).isNotNull();
        assertThat(planetDocument.getId()).isEqualTo(planet.id());
        assertThat(planetDocument.getName()).isEqualTo(planet.name());
        assertThat(planetDocument.getClimate()).isEqualTo(planet.climate());
        assertThat(planetDocument.getTerrain()).isEqualTo(planet.terrain());
    }
}