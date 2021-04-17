package dev.victor.paulo.startwarsAPI.persistence.repository;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataMongoTest
public class PlanetRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private PlanetRepository repository;

    @Test
    public void should_save_document() {
        PlanetDocument document = new PlanetDocument("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert");

        repository.save(document);

        assertThat(mongoTemplate.findAll(PlanetDocument.class, "planets"))
                .containsOnly(document);
    }
}
