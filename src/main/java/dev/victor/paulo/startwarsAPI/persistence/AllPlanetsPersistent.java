package dev.victor.paulo.startwarsAPI.persistence;

import dev.victor.paulo.startwarsAPI.persistence.converter.DocumentToPlanet;
import dev.victor.paulo.startwarsAPI.persistence.converter.PlanetToDocument;
import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.persistence.repository.PlanetRepository;
import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<Planet> getAll() {
        List<PlanetDocument> allPlanets = repository.findAll();
        return DocumentToPlanet.convert(allPlanets);
    }
}
