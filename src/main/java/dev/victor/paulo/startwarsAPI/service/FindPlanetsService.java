package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.converter.PlanetToResponse;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.PlanetFilters;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FindPlanetsService {

    @Autowired
    private AllPlanets allPlanets;

    public List<PlanetResponse> findAllBy(PlanetFilters filters) {
        List<Planet> allPlanets = this.allPlanets.getAllBy(filters);

        return PlanetToResponse.convert(allPlanets);
    }

    public Optional<PlanetResponse> byId(String id) {
        return allPlanets.byId(id)
                .map(PlanetToResponse::convert);
    }
}
