package dev.victor.paulo.startwarsAPI.external;

import dev.victor.paulo.startwarsAPI.external.dto.ExternalPlanet;
import dev.victor.paulo.startwarsAPI.external.dto.ExternalPlanetsList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AllExternalPlanetsRestConsumerTest {

    @MockBean
    private RestTemplate externalApiRestTemplate;

    @Autowired
    private AllExternalPlanetsRestConsumer externalApiConsumer;

    @Test
    public void should_call_external_api_to_fetch_planets_by_name_and_return_external_planet() {
        List<String> filmAppearances = Arrays.asList(
                "http://swapi.dev/api/films/1/",
                "http://swapi.dev/api/films/3/",
                "http://swapi.dev/api/films/4/",
                "http://swapi.dev/api/films/5/",
                "http://swapi.dev/api/films/6/"
        );
        ExternalPlanet expectedExternalPlanet = new ExternalPlanet("Tatooine", filmAppearances);
        when(externalApiRestTemplate.getForEntity("/planets/?search=Tatooine", ExternalPlanetsList.class))
                .thenReturn(expectedResponse(HttpStatus.OK, expectedExternalPlanet));


        Optional<ExternalPlanet> result = externalApiConsumer.byName("Tatooine");


        verify(externalApiRestTemplate).
                getForEntity("/planets/?search=Tatooine", ExternalPlanetsList.class);
        assertThat(result).contains(expectedExternalPlanet);
    }

    @Test
    public void when_external_api_returns_non_ok_status_should_return_empty_optional() {
        when(externalApiRestTemplate.getForEntity("/planets/?search=NonExistingPlanetName", ExternalPlanetsList.class))
                .thenReturn(expectedResponse(HttpStatus.BAD_REQUEST));


        Optional<ExternalPlanet> result = externalApiConsumer.byName("NonExistingPlanetName");


        verify(externalApiRestTemplate).
                getForEntity("/planets/?search=NonExistingPlanetName", ExternalPlanetsList.class);
        assertThat(result).isEmpty();
    }

    @Test
    public void when_external_api_returns_no_planets_should_return_empty_optional() {
        when(externalApiRestTemplate.getForEntity("/planets/?search=NonExistingPlanetName", ExternalPlanetsList.class))
                .thenReturn(expectedResponse(HttpStatus.OK));


        Optional<ExternalPlanet> result = externalApiConsumer.byName("NonExistingPlanetName");


        verify(externalApiRestTemplate).
                getForEntity("/planets/?search=NonExistingPlanetName", ExternalPlanetsList.class);
        assertThat(result).isEmpty();
    }

    @Test
    public void when_external_api_returns_empty_body_should_return_empty_optional() {
        when(externalApiRestTemplate.getForEntity("/planets/?search=PlanetName", ExternalPlanetsList.class))
                .thenReturn(ResponseEntity.ok().build());


        Optional<ExternalPlanet> result = externalApiConsumer.byName("PlanetName");


        verify(externalApiRestTemplate).
                getForEntity("/planets/?search=PlanetName", ExternalPlanetsList.class);
        assertThat(result).isEmpty();
    }

    @Test
    public void when_external_api_returns_multiple_planets_should_return_only_planet_matching_given_name_ignoring_case() {
        ExternalPlanet externalPlanet1 = new ExternalPlanet("yavin IV", Collections.emptyList());
        ExternalPlanet externalPlanet2 = new ExternalPlanet("yavin", Collections.emptyList());
        when(externalApiRestTemplate.getForEntity("/planets/?search=Yavin", ExternalPlanetsList.class))
                .thenReturn(expectedResponse(HttpStatus.OK, externalPlanet1, externalPlanet2));


        Optional<ExternalPlanet> result = externalApiConsumer.byName("Yavin");


        verify(externalApiRestTemplate).
                getForEntity("/planets/?search=Yavin", ExternalPlanetsList.class);
        assertThat(result).contains(externalPlanet2);
    }

    @Test
    public void when_external_api_returns_planet_that_do_not_match_give_name_ignoring_case_should_return_empty_optional() {
        ExternalPlanet externalPlanet1 = new ExternalPlanet("yavin IV", Collections.emptyList());
        when(externalApiRestTemplate.getForEntity("/planets/?search=Yavin", ExternalPlanetsList.class))
                .thenReturn(expectedResponse(HttpStatus.OK, externalPlanet1));


        Optional<ExternalPlanet> result = externalApiConsumer.byName("Yavin");


        verify(externalApiRestTemplate).
                getForEntity("/planets/?search=Yavin", ExternalPlanetsList.class);
        assertThat(result).isEmpty();
    }

    private ResponseEntity<ExternalPlanetsList> expectedResponse(HttpStatus httpStatus, ExternalPlanet... externalPlanets) {
        ExternalPlanetsList externalPlanetsList = new ExternalPlanetsList(Arrays.asList(externalPlanets));
        return ResponseEntity
                .status(httpStatus)
                .body(externalPlanetsList);
    }
}
