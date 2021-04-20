package dev.victor.paulo.startwarsAPI.service.collections;

import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.PlanetFilters;

import java.util.List;
import java.util.Optional;

public interface AllPlanets {
    Planet add(Planet planet);

    List<Planet> byFilters(PlanetFilters filters);

    Optional<Planet> byId(String id);

    void remove(Planet planet);
}
