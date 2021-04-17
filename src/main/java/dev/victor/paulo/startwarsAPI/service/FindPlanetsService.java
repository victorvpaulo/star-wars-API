package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.converter.PlanetToResponse;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindPlanetsService {

    @Autowired
    private AllPlanets allPlanets;

    public List<PlanetResponse> findAll() {
        List<Planet> allPlanets = this.allPlanets.getAll();

        return PlanetToResponse.convert(allPlanets);
    }
}
