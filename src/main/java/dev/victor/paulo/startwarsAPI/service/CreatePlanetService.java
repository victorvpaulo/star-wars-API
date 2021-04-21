package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.external.dto.ExternalPlanet;
import dev.victor.paulo.startwarsAPI.service.collections.AllExternalPlanets;
import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.converter.PlanetToResponse;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreatePlanetService {

    @Autowired
    private AllPlanets allPlanets;

    @Autowired
    private AllExternalPlanets allExternalPlanets;

    public PlanetResponse forData(PlanetRequest planetRequest) {
        Integer numberOfFilmAppearances = getNumberOfFilmAppearances(planetRequest);
        Planet planet = planetFrom(planetRequest, numberOfFilmAppearances);
        Planet saved = allPlanets.add(planet);
        return PlanetToResponse.convert(saved);
    }

    private Planet planetFrom(PlanetRequest planetRequest, Integer numberOfFilmAppearances) {
        return new Planet(null, planetRequest.name(), planetRequest.climate(), planetRequest.terrain(), numberOfFilmAppearances);
    }

    private Integer getNumberOfFilmAppearances(PlanetRequest planetRequest) {
        return fetchExternalPlanet(planetRequest)
                .map(externalPlanet -> externalPlanet.films().size())
                .orElse(null);
    }

    private Optional<ExternalPlanet> fetchExternalPlanet(PlanetRequest planetRequest) {
        return allExternalPlanets.byName(planetRequest.name());
    }

}
