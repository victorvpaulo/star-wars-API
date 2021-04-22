package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.converter.PlanetToResponse;
import dev.victor.paulo.startwarsAPI.service.exception.PlanetNotFoundException;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.PlanetFilters;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindPlanetsService {

    @Autowired
    private AllPlanets allPlanets;

    public List<PlanetResponse> byFilters(PlanetFilters filters) {
        List<Planet> allPlanets = this.allPlanets.byFilters(filters);

        return PlanetToResponse.convert(allPlanets);
    }

    public PlanetResponse byId(String id) {
        return allPlanets.byId(id)
                .map(PlanetToResponse::convert)
                .orElseThrow(PlanetNotFoundException::new);
    }
}
