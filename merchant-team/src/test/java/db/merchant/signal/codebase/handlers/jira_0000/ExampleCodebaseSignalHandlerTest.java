package db.merchant.signal.codebase.handlers.jira_0000;

import db.algo.Algo;
import db.merchant.WebWithPostgresContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * tests specific logic
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleCodebaseSignalHandlerTest extends WebWithPostgresContainer {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Algo algo;

    @Test
    void handleSignalTest() throws Exception {
        mockMvc.perform(get("/api/signal/0")).andExpect(status().isAccepted());
        // as an example to fix logic inside handler
        verify(algo).setAlgoParam(1,2);
        verify(algo).doAlgo();
    }
}