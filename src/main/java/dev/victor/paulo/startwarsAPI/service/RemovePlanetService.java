package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.exception.PlanetNotFoundException;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemovePlanetService {

    @Autowired
    private AllPlanets allPlanets;

    public void byId(String planetId) {
        Planet planet = allPlanets.byId(planetId)
                .orElseThrow(PlanetNotFoundException::new);
        allPlanets.remove(planet);
    }
}
