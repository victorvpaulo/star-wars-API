package endtoend;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.victor.paulo.startwarsAPI.StartWarsApiApplication;
import dev.victor.paulo.startwarsAPI.persistence.model.PlanetDocument;
import dev.victor.paulo.startwarsAPI.web.dto.PlanetRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = StartWarsApiApplication.class)
public class PlanetAPIEndToEndTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void executeTestSuite () throws Exception {
        givenNoPlanets_whenFindAllPlanets_thenShouldReturnOkAndEmptyList();

        givenPlanetThatExistsOnExternalAPI_whenCreatePlanet_thenShouldCreatePlanetWithFilmAppearancesAndReturnCreated();

        givenPlanetThatDoesNotExistsOnExternalAPI_whenCreatePlanet_thenShouldCreatePlanetWithEmptyFilmAppearancesAndReturnCreated();

        givenExistingPlanets_whenFetchAllPlanets_thenShouldReturnOkAndAllPlanets();

        givenExistingPlanet_whenFindAllPlanetsByName_thenShouldReturnPlanetAndOk();

        givenExistingPlanetId_whenFindById_thenShouldReturnPlanetAndOk();

        givenNonExistingPlanetId_whenFindById_thenShouldReturnNotFound();

        givenExistingPlanetId_whenDelete_thenShouldDeleteAndReturnNoContent();

        givenNonExistingPlanetId_whenDelete_thenShouldReturnNotFound();
    }

    private void givenNoPlanets_whenFindAllPlanets_thenShouldReturnOkAndEmptyList() throws Exception {
        mockMvc.perform(get("/planets")
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    private void givenPlanetThatExistsOnExternalAPI_whenCreatePlanet_thenShouldCreatePlanetWithFilmAppearancesAndReturnCreated() throws Exception {
        PlanetRequest dto = new PlanetRequest("Tatooine", "Arid", "Desert");
        mockMvc.perform(post("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(dto)))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Tatooine"))
                .andExpect(jsonPath("$.climate").value("Arid"))
                .andExpect(jsonPath("$.terrain").value("Desert"))
                .andExpect(jsonPath("$.numberOfFilmAppearances").isNumber());
    }

    private void givenPlanetThatDoesNotExistsOnExternalAPI_whenCreatePlanet_thenShouldCreatePlanetWithEmptyFilmAppearancesAndReturnCreated() throws Exception {
        PlanetRequest dto = new PlanetRequest("Made Up Planet", "Temperate", "Grasslands");
        mockMvc.perform(post("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(dto)))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Made Up Planet"))
                .andExpect(jsonPath("$.climate").value("Temperate"))
                .andExpect(jsonPath("$.terrain").value("Grasslands"))
                .andExpect(jsonPath("$.numberOfFilmAppearances").isEmpty());
    }

    private void givenExistingPlanets_whenFetchAllPlanets_thenShouldReturnOkAndAllPlanets() throws Exception {
        mockMvc.perform(get("/planets")
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").isString())
                .andExpect(jsonPath("$.[0].name").value("Tatooine"))
                .andExpect(jsonPath("$.[0].climate").value("Arid"))
                .andExpect(jsonPath("$.[0].terrain").value("Desert"))
                .andExpect(jsonPath("$.[0].numberOfFilmAppearances").isNumber())
                .andExpect(jsonPath("$.[1].id").isString())
                .andExpect(jsonPath("$.[1].name").value("Made Up Planet"))
                .andExpect(jsonPath("$.[1].climate").value("Temperate"))
                .andExpect(jsonPath("$.[1].terrain").value("Grasslands"))
                .andExpect(jsonPath("$.[1].numberOfFilmAppearances").isEmpty());
    }

    private void givenExistingPlanet_whenFindAllPlanetsByName_thenShouldReturnPlanetAndOk() throws Exception {
        mockMvc.perform(get("/planets")
                .param("name", "Tatooine")
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").isString())
                .andExpect(jsonPath("$.[0].name").value("Tatooine"))
                .andExpect(jsonPath("$.[0].climate").value("Arid"))
                .andExpect(jsonPath("$.[0].terrain").value("Desert"))
                .andExpect(jsonPath("$.[0].numberOfFilmAppearances").isNumber());
    }

    private void givenExistingPlanetId_whenFindById_thenShouldReturnPlanetAndOk() throws Exception {
        PlanetDocument planetDocument = getPersistedPlanet();

        mockMvc.perform(get(String.format("/planets/%s", planetDocument.getId()))
                .param("name", "Tatooine")
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(planetDocument.getId()))
                .andExpect(jsonPath("$.name").value(planetDocument.getName()))
                .andExpect(jsonPath("$.climate").value(planetDocument.getClimate()))
                .andExpect(jsonPath("$.terrain").value(planetDocument.getTerrain()))
                .andExpect(jsonPath("$.numberOfFilmAppearances").value(planetDocument.getNumberOfFilmAppearances()));
    }

    private void givenNonExistingPlanetId_whenFindById_thenShouldReturnNotFound() throws Exception {
        String nonExistingId = "nonExistingId";
        mockMvc.perform(get(String.format("/planets/%s", nonExistingId))
                .param("name", "Tatooine")
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private void givenExistingPlanetId_whenDelete_thenShouldDeleteAndReturnNoContent() throws Exception {
        PlanetDocument planetDocument = getPersistedPlanet();


        mockMvc.perform(delete(String.format("/planets/%s", planetDocument.getId()))
                .param("name", "Tatooine")
                .accept(MediaType.APPLICATION_JSON))


                .andDo(print())
                .andExpect(status().isNoContent());

        assertPlanetWasDeleted(planetDocument);
    }

    private void givenNonExistingPlanetId_whenDelete_thenShouldReturnNotFound() throws Exception {
        String nonExistingId = "nonExistingId";
        mockMvc.perform(delete(String.format("/planets/%s", nonExistingId))
                .param("name", "Tatooine")
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private PlanetDocument getPersistedPlanet() {
        Query query = new Query();
        query.limit(1);
        List<PlanetDocument> planetDocuments = mongoTemplate.find(query, PlanetDocument.class);
        return planetDocuments.get(0);
    }

    private void assertPlanetWasDeleted(PlanetDocument planet) {
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("id").is(planet.getId()));
        assertThat(mongoTemplate.exists(query2, PlanetDocument.class)).isFalse();
    }
}
