package dev.victor.paulo.startwarsAPI.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record PlanetRequest(
        @JsonProperty("name") @NotBlank(message = BLANK_PLANET_NAME_MESSAGE) String name,
        @JsonProperty("climate") @NotBlank(message = BlANK_PLANET_CLIMATE_MESSAGE) String climate,
        @JsonProperty("terrain") @NotBlank(message = BlANK_PLANET_TERRAIN_MESSAGE) String terrain) {

    public static final String BLANK_PLANET_NAME_MESSAGE = "Planet name cannot be blank";
    public static final String BlANK_PLANET_CLIMATE_MESSAGE = "Planet climate cannot be blank";
    public static final String BlANK_PLANET_TERRAIN_MESSAGE = "Planet terrain cannot be blank";
}
