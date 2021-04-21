package dev.victor.paulo.startwarsAPI.web;

import dev.victor.paulo.startwarsAPI.service.FindPlanetsService;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/planets")
public class FindPlanetsEndpoints {

    @Autowired
    private FindPlanetsService find;

    @GetMapping
    public ResponseEntity<List<PlanetResponse>> findByFilters(@RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "climate", required = false) String climate,
                                                              @RequestParam(value = "terrain", required = false) String terrain) {

        PlanetFilters filters = filters(name, climate, terrain);
        List<PlanetResponse> allPlanets = find.byFilters(filters);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allPlanets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponse> findById(@PathVariable String id) {
        PlanetResponse response = find.byId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
    private PlanetFilters filters(String name, String climate, String terrain) {
        return new PlanetFilters(name, climate, terrain);
    }
}
