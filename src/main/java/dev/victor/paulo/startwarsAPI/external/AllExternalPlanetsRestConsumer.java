package dev.victor.paulo.startwarsAPI.external;

import dev.victor.paulo.startwarsAPI.external.dto.ExternalPlanet;
import dev.victor.paulo.startwarsAPI.external.dto.ExternalPlanetsList;
import dev.victor.paulo.startwarsAPI.service.collections.AllExternalPlanets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class AllExternalPlanetsRestConsumer implements AllExternalPlanets {

    @Autowired
    private RestTemplate externalApiRestTemplate;

    @Override
    public Optional<ExternalPlanet> byName(String name) {
        ResponseEntity<ExternalPlanetsList> response = externalApiRestTemplate.getForEntity(String.format("/planets/?search=%s", name), ExternalPlanetsList.class);
        if (!response.getStatusCode().equals(HttpStatus.OK) || response.getBody() == null) {
            return Optional.empty();
        }

        List<ExternalPlanet> externalPlanets = response.getBody().externalPlanets();

        return externalPlanets.stream()
                .filter(matchesNameIgnoringCase(name))
                .findFirst();
    }

    private Predicate<ExternalPlanet> matchesNameIgnoringCase(String name) {
        return externalPlanet -> externalPlanet.name().equalsIgnoreCase(name);
    }
}
