package dev.victor.paulo.startwarsAPI.web.validation;

import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PlanetRequestValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void should_have_no_violation_when_planet_is_valid() {
        PlanetRequest planetRequest = new PlanetRequest("Tatooine", "Arid", "Desert");

        Set<ConstraintViolation<PlanetRequest>> constraintViolations = validator.validate(planetRequest);

        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_have_violation_when_planet_name_is_null() {
        PlanetRequest planetRequest = new PlanetRequest(null, "Arid", "Desert");

        ConstraintViolation<PlanetRequest> constraintViolation = validator.validate(planetRequest).iterator().next();

        assertConstraintViolation(constraintViolation, "name", null, "Planet name cannot be blank");
    }

    @Test
    public void should_have_violation_when_planet_name_is_blank() {
        PlanetRequest planetRequest = new PlanetRequest("   ", "Arid", "Desert");

        ConstraintViolation<PlanetRequest> constraintViolation = validator.validate(planetRequest).iterator().next();

        assertConstraintViolation(constraintViolation, "name", "   ", "Planet name cannot be blank");
    }

    @Test
    public void should_have_violation_when_planet_climate_is_null() {
        PlanetRequest planetRequest = new PlanetRequest("Tatooine", null, "Desert");

        ConstraintViolation<PlanetRequest> constraintViolation = validator.validate(planetRequest).iterator().next();

        assertConstraintViolation(constraintViolation, "climate", null, "Planet climate cannot be blank");
    }

    @Test
    public void should_have_violation_when_planet_climate_is_blank() {
        PlanetRequest planetRequest = new PlanetRequest("Tatooine", "   ", "Desert");

        ConstraintViolation<PlanetRequest> constraintViolation = validator.validate(planetRequest).iterator().next();

        assertConstraintViolation(constraintViolation, "climate", "   ", "Planet climate cannot be blank");
    }

    @Test
    public void should_have_violation_when_planet_terrain_is_null() {
        PlanetRequest planetRequest = new PlanetRequest("Tatooine", "Arid", null);

        ConstraintViolation<PlanetRequest> constraintViolation = validator.validate(planetRequest).iterator().next();

        assertConstraintViolation(constraintViolation, "terrain", null, "Planet terrain cannot be blank");
    }

    @Test
    public void should_have_violation_when_planet_terrain_is_blank() {
        PlanetRequest planetRequest = new PlanetRequest("Tatooine", "Arid", "   ");

        ConstraintViolation<PlanetRequest> constraintViolation = validator.validate(planetRequest).iterator().next();

        assertConstraintViolation(constraintViolation, "terrain", "   ", "Planet terrain cannot be blank");
    }

    private void assertConstraintViolation(ConstraintViolation<PlanetRequest> constraintViolation, String fieldName, Object invalidValue, String message) {
        assertThat(constraintViolation.getMessage()).isEqualTo(message);
        assertThat(constraintViolation.getInvalidValue()).isEqualTo(invalidValue);
        assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo(fieldName);
    }
}
