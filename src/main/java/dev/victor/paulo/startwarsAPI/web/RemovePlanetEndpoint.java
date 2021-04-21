package dev.victor.paulo.startwarsAPI.web;

import dev.victor.paulo.startwarsAPI.service.RemovePlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/planets")
public class RemovePlanetEndpoint {

    @Autowired
    private RemovePlanetService remove;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        remove.byId(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
