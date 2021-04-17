package dev.victor.paulo.startwarsAPI.persistence;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.persistence.repository.PlanetRepository;
import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllPlanetsPersistent implements AllPlanets {

    @Autowired
    private PlanetRepository repository;

    @Override
    public Planet add(Planet planet) {
        PlanetDocument documentToSave = toDocument(planet);
        PlanetDocument savedDocument = repository.save(documentToSave);
        return toPlanet(savedDocument);
    }

    private Planet toPlanet(PlanetDocument savedDocument) {
        return new Planet(savedDocument.getId(), savedDocument.getName(), savedDocument.getClimate(), savedDocument.getTerrain());
    }

    private PlanetDocument toDocument(Planet planet) {
        return new PlanetDocument(planet.id(), planet.name(), planet.climate(), planet.terrain());
    }
}
