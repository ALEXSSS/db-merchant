package db.merchant;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * 1) allows running postgres database with tests (purging database)
 * 2) automatically set-ups mock mvc for convenience
 */
@AutoConfigureMockMvc
public class WebWithPostgresContainer {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    public void setFlyway(Flyway flyway) {
        WebWithPostgresContainer.flyway = flyway;
    }

    @AfterEach
    public void dropPostgres() {
        if (flyway != null) {
            flyway.clean();
            flyway.migrate();
        }
    }

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13-alpine")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432);

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "postgres");
    }

    static volatile Flyway flyway = null;
}
