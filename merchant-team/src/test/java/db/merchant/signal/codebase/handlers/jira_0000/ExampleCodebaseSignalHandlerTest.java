package db.merchant.signal.codebase.handlers.jira_0000;

import db.algo.Algo;
import db.merchant.WebWithPostgresContainer;
import db.merchant.signal.executor.SignalExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

/**
 * tests specific logic
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleCodebaseSignalHandlerTest extends WebWithPostgresContainer {

    @MockBean
    private Algo algo;
    private final ThreadPoolExecutor executor;

    @Autowired
    public ExampleCodebaseSignalHandlerTest(SignalExecutor signalExecutor) {
        executor = (ThreadPoolExecutor) signalExecutor.executor;
    }

    @Test
    void handleSignalTest() throws Exception {
        mockMvc.perform(get("/api/signal/0")).andExpect(status().isAccepted());
        await().atLeast(5, TimeUnit.MILLISECONDS)
                .atMost(5, TimeUnit.SECONDS)
                .with()
                .pollInterval(100, TimeUnit.MILLISECONDS)
                .until(() -> executor.getActiveCount() == 0);
        // as an example to fix logic inside handler
        verify(algo).setAlgoParam(1, 2);
        verify(algo).doAlgo();
    }
}