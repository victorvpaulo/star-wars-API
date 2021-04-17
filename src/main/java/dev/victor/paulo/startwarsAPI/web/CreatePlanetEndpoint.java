package dev.victor.paulo.startwarsAPI.web;

import dev.victor.paulo.startwarsAPI.service.CreatePlanetService;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/planets")
public class CreatePlanetEndpoint {

    @Autowired
    CreatePlanetService createPlanet;

    @PostMapping
    public ResponseEntity<PlanetResponse> create(@RequestBody PlanetRequest request) {
        PlanetResponse planet = createPlanet.forData(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planet);
    }
}
