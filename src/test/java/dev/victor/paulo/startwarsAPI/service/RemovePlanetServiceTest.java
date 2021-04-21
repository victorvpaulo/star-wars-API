package dev.victor.paulo.startwarsAPI.service;

import dev.victor.paulo.startwarsAPI.service.collections.AllPlanets;
import dev.victor.paulo.startwarsAPI.service.exception.PlanetNotFoundException;
import dev.victor.paulo.startwarsAPI.service.model.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RemovePlanetServiceTest {

    public static final Planet PLANET_1 = new Planet("5399aba6e4b0ae375bfdca88", "Tatooine", "Arid", "Desert", 5);

    @MockBean
    private AllPlanets allPlanets;

    @Autowired
    private RemovePlanetService remove;

    @Test
    public void should_raise_exception_when_planet_not_exists() {
        String nonExistingPlanetId = "539a7244d9d5e4dea11d4ffe";
        when(allPlanets.byId(nonExistingPlanetId)).thenReturn(Optional.empty());

        assertThrows(PlanetNotFoundException.class,
                () -> remove.byId(nonExistingPlanetId));
    }

    @Test
    public void should_remove_planet() {
        when(allPlanets.byId(PLANET_1.id())).thenReturn(Optional.of(PLANET_1));

        remove.byId(PLANET_1.id());

        verify(allPlanets).byId(PLANET_1.id());
        verify(allPlanets).remove(PLANET_1);
    }
}