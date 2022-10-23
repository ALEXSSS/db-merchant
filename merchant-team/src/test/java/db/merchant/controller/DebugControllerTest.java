package db.merchant.controller;

import db.merchant.WebWithPostgresContainer;
import db.merchant.signal.database.AlgoConfiguration;
import db.merchant.signal.database.AlgoConfiguration.AlgoStep;
import db.merchant.signal.database.AlgoConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DebugControllerTest extends WebWithPostgresContainer {

    @Autowired
    private AlgoConfigurationRepository repository;

    @Test
    public void debugControllerCanSaveAndDeleteTest() throws Exception {
        assertTrue(repository.findById(101).isEmpty(), "Shouldn't exist value for id");
        createById(101);
        assertTrue(repository.findById(101).isPresent(), "Should exist value for id");
    }

    private void createById(int id) throws Exception {
        AlgoConfiguration conf = AlgoConfiguration.builder()
                .withId(id)
                .withStepsArray(new AlgoStep.DoAlgo(), new AlgoStep.CancelTrades())
                .build();
        mockMvc.perform(
                        post("/api/signal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(conf))
                )
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(status().isCreated());
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
}