package dev.victor.paulo.startwarsAPI.persistence.repository;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataMongoTest
public class PlanetRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PlanetRepository repository;

    @AfterEach
    public void tearDown() {
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
    public void findAll_should_return_all_planets() {
        PlanetDocument document1 = new PlanetDocument("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");
        PlanetDocument document2 = new PlanetDocument("607a72495196adef1e2d094b", "Alderaan", "Temperate", "Grasslands, Mountains");
        PlanetDocument document3 = new PlanetDocument("539a7244d9d5e4dea11d4ffe", "Yavin IV", "Temperate", "Jungle, Rainforests");
        mongoTemplate.save(document1);
        mongoTemplate.save(document2);
        mongoTemplate.save(document3);

        List<PlanetDocument> all = repository.findAll();

        assertThat(all).containsExactly(document1, document2, document3);
    }

    @Test
    public void findAll_should_return_empty_list_when_there_is_no_data() {
        List<PlanetDocument> all = repository.findAll();

        assertThat(all).isEmpty();
    }
}
