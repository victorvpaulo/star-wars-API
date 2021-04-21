package dev.victor.paulo.startwarsAPI.persistence.converter;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.service.model.Planet;

public class PlanetToDocument {
    public static PlanetDocument convert(Planet planet) {
        return new PlanetDocument(planet.id(), planet.name(), planet.climate(), planet.terrain(), planet.numberOfFilmAppearances());
    }
}
