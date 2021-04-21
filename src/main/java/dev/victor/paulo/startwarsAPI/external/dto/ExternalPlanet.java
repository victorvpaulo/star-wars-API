package dev.victor.paulo.startwarsAPI.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalPlanet (String name, List<String> films) {
}
