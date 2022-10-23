package db.merchant.signal.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.postgresql.util.PGobject;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Database necessary configurations
 */
@Configuration
class DatabaseSignalHandlerConfiguration extends AbstractJdbcConfiguration {

    // for convenience clean-migrate on not-prod envs
    @Profile("!prod")
    @Bean
    public FlywayMigrationInitializer initializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, fly -> {
            fly.clean();
            fly.migrate();
        });
    }

    /**
     * Allows us to define additional converters for json type in postgres
     * <p>
     * POSSIBLE FUTURE WORK:
     * 1) put it in the separate library module
     * 2) unify creation of other type converters
     */
    @Override
    protected List<?> userConverters() {
        return Arrays.asList(new AlgoWritingConverter(), new AlgoReadingConverter());
    }

    @WritingConverter
    private static class AlgoWritingConverter implements Converter<AlgoConfiguration.AlgoSteps, PGobject> {
        @Override
        public PGobject convert(AlgoConfiguration.AlgoSteps value) {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            try {
                jsonObject.setValue(MAPPER.writeValueAsString(value));
            } catch (Exception e) {
                // ignore impossible checked exceptions
                throw new RuntimeException(e);
            }
            return jsonObject;
        }
    }

    @ReadingConverter
    private static class AlgoReadingConverter implements Converter<PGobject, AlgoConfiguration.AlgoSteps> {

        @Override
        public AlgoConfiguration.AlgoSteps convert(PGobject source) {
            String value = source.getValue();
            AlgoConfiguration.AlgoSteps result = null;
            try {
                result = MAPPER.readValue(value, AlgoConfiguration.AlgoSteps.class);
            } catch (JsonProcessingException e) {
                // ignore impossible checked exceptions
                throw new RuntimeException(e);
            }
            return result;
        }
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
}