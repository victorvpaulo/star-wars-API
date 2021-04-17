package dev.victor.paulo.startwarsAPI.persistence.repository;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<PlanetDocument, String> {
}
