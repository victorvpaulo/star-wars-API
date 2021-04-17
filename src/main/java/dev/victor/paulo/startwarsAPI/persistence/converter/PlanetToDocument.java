package dev.victor.paulo.startwarsAPI.persistence.converter;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.service.model.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetToDocument {
    public static PlanetDocument convert(Planet planet) {
        return new PlanetDocument(planet.id(), planet.name(), planet.climate(), planet.terrain());
    }
}
