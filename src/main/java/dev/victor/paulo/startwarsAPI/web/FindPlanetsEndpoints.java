package dev.victor.paulo.startwarsAPI.web;

import dev.victor.paulo.startwarsAPI.service.FindPlanetsService;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/planets")
public class FindPlanetsEndpoints {

    @Autowired
    private FindPlanetsService find;

    @GetMapping
    public ResponseEntity<List<PlanetResponse>> findAll() {
        List<PlanetResponse> allPlanets = find.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allPlanets);
    }
}
