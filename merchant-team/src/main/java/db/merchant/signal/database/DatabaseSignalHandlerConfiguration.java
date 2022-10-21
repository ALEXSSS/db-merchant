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

@Configuration
class DatabaseSignalHandlerConfiguration extends AbstractJdbcConfiguration {

    // todo
    @Profile("!prod")
    @Bean
    public FlywayMigrationInitializer initializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, fly -> {
            fly.clean();
            fly.migrate();
        });
    }

    @Override
    protected List<?> userConverters() {
        return Arrays.asList(new AlgoWritingConverter(), new AlgoReadingConverter());
    }

    @WritingConverter
    static class AlgoWritingConverter implements Converter<AlgoConfiguration.AlgoSteps, PGobject> {
        @Override
        public PGobject convert(AlgoConfiguration.AlgoSteps value) {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            try {
                jsonObject.setValue(mapper.writeValueAsString(value));
            } catch (Exception e) {
                // ignore impossible checked exceptions
            }
            return jsonObject;
        }

        private static final ObjectMapper mapper = new ObjectMapper();
    }

    @ReadingConverter
    static class AlgoReadingConverter implements Converter<PGobject, AlgoConfiguration.AlgoSteps> {

        @Override
        public AlgoConfiguration.AlgoSteps convert(PGobject source) {
            String value = source.getValue();
            AlgoConfiguration.AlgoSteps result = null;
            try {
                result = mapper.readValue(value, AlgoConfiguration.AlgoSteps.class);
            } catch (JsonProcessingException e) {
                // ignore impossible checked exceptions
            }
            return result;
        }

        private static final ObjectMapper mapper = new ObjectMapper();
    }

}