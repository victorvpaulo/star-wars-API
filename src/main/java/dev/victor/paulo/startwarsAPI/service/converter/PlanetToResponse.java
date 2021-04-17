package dev.victor.paulo.startwarsAPI.service.converter;

import dev.victor.paulo.startwarsAPI.service.model.Planet;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetToResponse {
    public static PlanetResponse convert(Planet planet) {
        return new PlanetResponse(planet.id(), planet.name(), planet.climate(), planet.terrain());
    }

    public static List<PlanetResponse> convert(List<Planet> planets) {
        return planets.stream()
                .map(PlanetToResponse::convert)
                .collect(Collectors.toList());
    }
}
