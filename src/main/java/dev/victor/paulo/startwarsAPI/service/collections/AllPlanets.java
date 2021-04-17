package dev.victor.paulo.startwarsAPI.service.collections;

import dev.victor.paulo.startwarsAPI.service.model.Planet;

import java.util.List;

public interface AllPlanets {
    Planet add(Planet planet);

    List<Planet> getAll();
}
