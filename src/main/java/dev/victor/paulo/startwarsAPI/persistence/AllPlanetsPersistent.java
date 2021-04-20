package dev.victor.paulo.startwarsAPI.persistence;

import dev.victor.paulo.startwarsAPI.persistence.converter.DocumentToPlanet;
import dev.victor.paulo.startwarsAPI.persistence.converter.PlanetToDocument;
import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.persistence.repository.PlanetRepository;
import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.PlanetFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Component
public class AllPlanetsPersistent implements AllPlanets {

    @Autowired
    private PlanetRepository repository;

    @Override
    public Planet add(Planet planet) {
        PlanetDocument documentToSave = PlanetToDocument.convert(planet);
        PlanetDocument savedDocument = repository.save(documentToSave);
        return DocumentToPlanet.convert(savedDocument);
    }

    @Override
    public List<Planet> getAllBy(PlanetFilters filters) {
        Example<PlanetDocument> example = exampleFor(filters);
        List<PlanetDocument> allPlanets = repository.findAll(example);
        return DocumentToPlanet.convert(allPlanets);
    }

    @Override
    public Optional<Planet> byId(String id) {
        return repository.findById(id)
                .map(DocumentToPlanet::convert);
    }

    private Example<PlanetDocument> exampleFor(PlanetFilters filters) {
        String probe = filters.terrain();
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        return Example.of(new PlanetDocument(null, filters.name(), filters.climate(), probe), matcher);
    }
}
