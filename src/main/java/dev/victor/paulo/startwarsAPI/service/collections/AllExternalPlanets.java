package dev.victor.paulo.startwarsAPI.service.collections;

import dev.victor.paulo.startwarsAPI.external.dto.ExternalPlanet;

import java.util.Optional;

public interface AllExternalPlanets {
    Optional<ExternalPlanet> byName(String name);
}
