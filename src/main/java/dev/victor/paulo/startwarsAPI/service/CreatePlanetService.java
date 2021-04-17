package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePlanetService {

    @Autowired
    private AllPlanets allPlanets;

    public PlanetResponse forData(PlanetRequest planetRequest) {
        Planet planet = planetFrom(planetRequest);
        Planet saved = allPlanets.add(planet);
        return responseFor(saved);
    }

    private Planet planetFrom(PlanetRequest planetRequest) {
        return new Planet(null, planetRequest.name(), planetRequest.climate(), planetRequest.terrain());
    }

    private PlanetResponse responseFor(Planet saved) {
        return new PlanetResponse(saved.id(), saved.name(), saved.climate(), saved.terrain());
    }
}
