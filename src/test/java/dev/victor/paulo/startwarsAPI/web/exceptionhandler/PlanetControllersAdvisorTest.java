package dev.victor.paulo.startwarsAPI.web.exceptionhandler;

import dev.victor.paulo.startwarsAPI.service.exception.PlanetNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PlanetControllersAdvisorTest {

    @Test
    public void should_return_status_code_404_when_exception_is_throw() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new Controller())
                .setControllerAdvice(new PlanetControllersAdvisor())
                .build();

        mockMvc.perform(get("/test"))
                .andExpect(status().is(404));
    }

    @RestController("/test")
    private static class Controller {

        @GetMapping
        public ResponseEntity<Void> endpoint() {
            throw new PlanetNotFoundException();
        }
    }
}