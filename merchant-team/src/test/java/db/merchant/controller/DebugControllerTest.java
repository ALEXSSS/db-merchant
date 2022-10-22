package db.merchant.controller;

import db.merchant.WebWithPostgresContainer;
import db.merchant.signal.database.AlgoConfiguration;
import db.merchant.signal.database.AlgoConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DebugControllerTest extends WebWithPostgresContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlgoConfigurationRepository repository;

    @Test
    public void debugControllerCanSaveAndDeleteTest() throws Exception {
        assertTrue(repository.findById(101).isEmpty(), "Shouldn't exist value for id");
        createById(101);
        assertTrue(repository.findById(101).isPresent(), "Should exist value for id");
    }

    private void createById(int id) throws Exception {
        AlgoConfiguration conf = new AlgoConfiguration();
        AlgoConfiguration.AlgoSteps steps = new AlgoConfiguration.AlgoSteps();
        List<AlgoConfiguration.AlgoStep> stepsList = new ArrayList<>();
        conf.setId(id);
        stepsList.add(new AlgoConfiguration.AlgoStep.DoAlgo());
        stepsList.add(new AlgoConfiguration.AlgoStep.CancelTrades());
        steps.setSteps(stepsList);
        conf.setSteps(steps);
        mockMvc.perform(
                        post("/api/signal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(conf))
                )
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(status().isCreated());
    }

    private static final ObjectMapper mapper = new ObjectMapper();
}