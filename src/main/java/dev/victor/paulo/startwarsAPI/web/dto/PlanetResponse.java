package dev.victor.paulo.startwarsAPI.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlanetResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("climate") String climate,
        @JsonProperty("terrain") String terrain,
        @JsonProperty("numberOfFilmAppearances") Integer numberOfFilmAppearances) {
}
