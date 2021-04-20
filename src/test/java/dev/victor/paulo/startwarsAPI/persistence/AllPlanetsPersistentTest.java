package dev.victor.paulo.startwarsAPI.persistence;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.persistence.repository.PlanetRepository;
import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.PlanetFilters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

//https://www.baeldung.com/queries-in-spring-data-mongodb

    @Test
    public void getAll_should_build_query_example_and_return_planets() {
        PlanetFilters filters = filters(null, null, null);
        Example<PlanetDocument> expectedExample = expectedExampleFor(filters);
        when(repository.findAll(expectedExample)).thenReturn(Arrays.asList(EXPECTED_DOCUMENT_1, EXPECTED_DOCUMENT_2, EXPECTED_DOCUMENT_3));

        List<Planet> allPlanets = this.allPlanets.getAllBy(filters);

        verify(repository).findAll(expectedExample);
        assertThat(allPlanets).containsExactly(
                EXPECTED_PLANET_1,
                EXPECTED_PLANET_2,
                EXPECTED_PLANET_3
        );
    }

    @Test
    public void getAll_should_build_query_example_and_return_empty_list_when_there_is_no_data() {
        PlanetFilters filters = filters("Tatooine", "Temperate", "Grasslands, Mountains");
        Example<PlanetDocument> expectedExample = expectedExampleFor(filters);
        when(repository.findAll(expectedExample)).thenReturn(Collections.emptyList());

        List<Planet> allPlanets = this.allPlanets.getAllBy(filters);

        verify(repository).findAll(expectedExample);
        assertThat(allPlanets).isEmpty();
    }

    @Test
    public void when_planet_exists_byId_should_return_planet() {
        when(repository.findById(EXPECTED_PLANET_1.id())).thenReturn(Optional.of(EXPECTED_DOCUMENT_1));

        Optional<Planet> planet = allPlanets.byId(EXPECTED_PLANET_1.id());

        assertThat(planet).contains(EXPECTED_PLANET_1);
    }

    @Test
    public void when_planet_not_exists_byId_should_return_empty_optional() {
        String nonExistingId = "607a89fa7135a8d2cf3af7dd";
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Optional<Planet> planet = allPlanets.byId(nonExistingId);

        assertThat(planet).isNotPresent();
    }

    private PlanetFilters filters(String name, String climate, String terrain) {
        return new PlanetFilters(name, climate, terrain);
    }

    private Example<PlanetDocument> expectedExampleFor(PlanetFilters filters) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        return Example.of(new PlanetDocument(null, filters.name(), filters.climate(), filters.terrain()), matcher);
    }
}
