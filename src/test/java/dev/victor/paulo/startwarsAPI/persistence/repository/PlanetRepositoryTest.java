package dev.victor.paulo.startwarsAPI.persistence.repository;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataMongoTest
public class PlanetRepositoryTest {

    public static final PlanetDocument PLANET_DOCUMENT_1 = new PlanetDocument("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
    public static final PlanetDocument PLANET_DOCUMENT_2 = new PlanetDocument("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PlanetRepository repository;

    private void setUpDataSet() {
        mongoTemplate.save(PLANET_DOCUMENT_1);
        mongoTemplate.save(PLANET_DOCUMENT_2);
    }

    @BeforeEach
    private void cleanDatabase() {
        mongoTemplate.findAllAndRemove(new Query(), PlanetDocument.class);
    }

    @Test
    public void should_save_document() {
        PlanetDocument document = new PlanetDocument(null, "Tatooine", "Arid", "Desert");


        repository.save(document);


        List<PlanetDocument> planets = mongoTemplate.findAll(PlanetDocument.class, "planets");
        assertThat(planets).hasSize(1);

        PlanetDocument planet = planets.get(0);
        assertThat(planet)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(document);
        assertThat(planet.getId()).isNotNull();
    }

    @Test
    public void when_planet_exists_findById_should_return_planet() {
        setUpDataSet();
        Optional<PlanetDocument> document = repository.findById(PLANET_DOCUMENT_1.getId());

        assertThat(document).contains(PLANET_DOCUMENT_1);
    }

    @Test
    public void when_planet_no_exists_findById_should_return_empty_optional() {
        setUpDataSet();
        String nonExistingId = "607a89fa7135a8d2cf3af7dd";

        Optional<PlanetDocument> document = repository.findById(nonExistingId);

        assertThat(document).isNotPresent();
    }

    @Test
    public void findAll_should_return_all_planets_when_filters_are_empty() {
        setUpDataSet();
        Example<PlanetDocument> example = exampleWith(null, null, null);
        List<PlanetDocument> all = repository.findAll(example);

        assertThat(all).containsExactly(PLANET_DOCUMENT_1, PLANET_DOCUMENT_2);
    }

    @Test
    public void findAll_should_return_planets_matching_single_filter() {
        setUpDataSet();
        Example<PlanetDocument> example = exampleWith("Yavin IV", null, null);
        List<PlanetDocument> all = repository.findAll(example);

        assertThat(all).containsExactly(PLANET_DOCUMENT_2);
    }

    @Test
    public void findAll_should_return_planets_matching_combined_filters() {
        setUpDataSet();
        Example<PlanetDocument> example = exampleWith(null, "Temperate", "Grasslands, Mountains");
        List<PlanetDocument> all = repository.findAll(example);

        assertThat(all).containsExactly(PLANET_DOCUMENT_1);
    }

    @Test
    public void findAll_should_return_empty_list_when_there_is_no_planet_matching_example() {
        setUpDataSet();
        Example<PlanetDocument> example = exampleWith("Alderaan", null, "Jungle, Rainforests");

        List<PlanetDocument> all = repository.findAll(example);

        assertThat(all).isEmpty();
    }

    @Test
    public void delete_should_delete_planet() {
        setUpDataSet();

        repository.delete(PLANET_DOCUMENT_1);

        assertThat(mongoTemplate.findAll(PlanetDocument.class)).doesNotContain(PLANET_DOCUMENT_1);
    }

    private Example<PlanetDocument> exampleWith(String name, String climate, String terrain) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        return Example.of(new PlanetDocument(null, name, climate, terrain), matcher);
    }
}
