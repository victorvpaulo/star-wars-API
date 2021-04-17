package dev.victor.paulo.startwarsAPI.persistence;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.persistence.repository.PlanetRepository;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AllPlanetsPersistentTest {
    public static final String PLANET_ID = "5399aba6e4b0ae375bfdca88";
    public static final String PLANET_NAME = "Tatooine";
    public static final String PLANET_CLIMATE = "Arid";
    public static final String PLANET_TERRAIN = "Desert";

    @MockBean
    private PlanetRepository repository;

    @Autowired
    private AllPlanetsPersistent allPlanetsPersistent;

    @Test
    public void should_add_planet() {
        when(repository.save(planetDocumentToSave())).thenReturn(expectedSavedPlanetDocument());

        Planet addedPlanet = allPlanetsPersistent.add(planet());

        verify(repository).save(planetDocumentToSave());
        assertThat(addedPlanet).isEqualTo(expectedAddedPlanet());
    }

    private PlanetDocument expectedSavedPlanetDocument() {
        return new PlanetDocument(PLANET_ID, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }

    private PlanetDocument planetDocumentToSave() {
        return new PlanetDocument(null, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }

    private Planet planet() {
        return new Planet(null, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }

    private Planet expectedAddedPlanet() {
        return new Planet(PLANET_ID, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
    }
}