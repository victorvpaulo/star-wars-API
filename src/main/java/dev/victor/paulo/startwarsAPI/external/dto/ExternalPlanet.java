package dev.victor.paulo.startwarsAPI.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalPlanet (
        @JsonProperty("name") String name,
        @JsonProperty("films") List<String> films) {
}
