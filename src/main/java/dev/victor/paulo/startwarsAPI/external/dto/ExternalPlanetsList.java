package dev.victor.paulo.startwarsAPI.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExternalPlanetsList(@JsonProperty("results") List<ExternalPlanet> externalPlanets) {
}
