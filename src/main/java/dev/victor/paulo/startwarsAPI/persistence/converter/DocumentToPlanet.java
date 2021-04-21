package dev.victor.paulo.startwarsAPI.persistence.converter;

import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.service.model.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class DocumentToPlanet {
    public static Planet convert(PlanetDocument document) {
        return new Planet(document.getId(), document.getName(), document.getClimate(), document.getTerrain(), document.getNumberOfFilmAppearances());
    }

    public static List<Planet> convert(List<PlanetDocument> documents) {
        return documents.stream()
                .map(DocumentToPlanet::convert)
                .collect(Collectors.toList());
    }
}
