package dev.victor.paulo.startwarsAPI.persistence;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.persistence.repository.PlanetRepository;
import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AllPlanetsPersistentTest {
    public static final String PLANET_ID = "5399aba6e4b0ae375bfdca88";
    public static final String PLANET_NAME = "Tatooine";
    public static final String PLANET_CLIMATE = "Arid";
    public static final String PLANET_TERRAIN = "Desert";

    public static final PlanetDocument EXPECTED_DOCUMENT_1 = new PlanetDocument("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
    public static final PlanetDocument EXPECTED_DOCUMENT_2 = new PlanetDocument("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final PlanetDocument EXPECTED_DOCUMENT_3 = new PlanetDocument("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
    public static final Planet EXPECTED_PLANET_1 = new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
    public static final Planet EXPECTED_PLANET_2 = new Planet("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final Planet EXPECTED_PLANET_3 = new Planet("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");

    @MockBean
    private PlanetRepository repository;

    @Autowired
    private AllPlanets allPlanets;

    @Test
    public void should_add_planet() {
        Planet planet = new Planet(null, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
        Planet expectedAddedPlanet = new Planet(PLANET_ID, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);

        PlanetDocument planetDocumentToSave = new PlanetDocument(null, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
        PlanetDocument expectedPlanedDocumentToSave = new PlanetDocument(PLANET_ID, PLANET_NAME, PLANET_CLIMATE, PLANET_TERRAIN);
        when(repository.save(planetDocumentToSave)).thenReturn(expectedPlanedDocumentToSave);


        Planet addedPlanet = allPlanets.add(planet);


        verify(repository).save(planetDocumentToSave);
        assertThat(addedPlanet).isEqualTo(expectedAddedPlanet);
    }

    @Test
    public void getAll_should_return_all_planets() {
        when(repository.findAll()).thenReturn(Arrays.asList(EXPECTED_DOCUMENT_1, EXPECTED_DOCUMENT_2, EXPECTED_DOCUMENT_3));

        List<Planet> allPlanets = this.allPlanets.getAll();

        assertThat(allPlanets).containsExactly(
                EXPECTED_PLANET_1,
                EXPECTED_PLANET_2,
                EXPECTED_PLANET_3
        );
    }

    @Test
    public void getAll_should_return_empty_list_when_there_is_no_data() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Planet> allPlanets = this.allPlanets.getAll();

        assertThat(allPlanets).isEmpty();
    }
}
