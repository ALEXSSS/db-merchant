package db.merchant.signal.database;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import db.algo.Algo;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Database entity description, containing steps to run a particular handler.
 */
@Table
public class AlgoConfiguration {
    @Id
    private Integer id;
    @Version
    private Integer version;

    private String task;
    private String author;
    private String description;
    private AlgoSteps steps;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AlgoSteps getSteps() {
        return steps;
    }

    public void setSteps(AlgoSteps steps) {
        this.steps = steps;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public static class AlgoSteps {

        private List<AlgoStep> steps;

        public List<AlgoStep> getSteps() {
            return steps;
        }

        public void setSteps(List<AlgoStep> steps) {
            this.steps = steps;
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = AlgoStep.DoAlgo.class, name = "DO_ALGO"),
            @JsonSubTypes.Type(value = AlgoStep.CancelTrades.class, name = "CANCEL_TRADES"),
            @JsonSubTypes.Type(value = AlgoStep.Reverse.class, name = "REVERSE"),
            @JsonSubTypes.Type(value = AlgoStep.SubmitToMarket.class, name = "SUBMIT_TO_MARKET"),
            @JsonSubTypes.Type(value = AlgoStep.PerformCalc.class, name = "PERFORM_CALC"),
            @JsonSubTypes.Type(value = AlgoStep.SetUp.class, name = "SET_UP"),
            @JsonSubTypes.Type(value = AlgoStep.SetAlgoParam.class, name = "SET_ALGO_PARAM")
    })
    public static abstract class AlgoStep {

        abstract void handle(Algo algo);

        @JsonTypeName("DO_ALGO")
        public static class DoAlgo extends AlgoStep {

            @Override
            void handle(Algo algo) {
                algo.doAlgo();
            }
        }

        @JsonTypeName("CANCEL_TRADES")
        public static class CancelTrades extends AlgoStep {

            @Override
            void handle(Algo algo) {
                algo.cancelTrades();
            }
        }

        @JsonTypeName("REVERSE")
        public static class Reverse extends AlgoStep {

            @Override
            void handle(Algo algo) {
                algo.reverse();
            }
        }

        @JsonTypeName("SUBMIT_TO_MARKET")
        public static class SubmitToMarket extends AlgoStep {

            @Override
            void handle(Algo algo) {
                algo.submitToMarket();
            }
        }

        @JsonTypeName("PERFORM_CALC")
        public static class PerformCalc extends AlgoStep {

            @Override
            void handle(Algo algo) {
                algo.performCalc();
            }
        }

        @JsonTypeName("SET_UP")
        public static class SetUp extends AlgoStep {

            @Override
            void handle(Algo algo) {
                algo.setUp();
            }
        }

        @JsonTypeName("SET_ALGO_PARAM")
        public static class SetAlgoParam extends AlgoStep {
            private int param;
            private int value;

            public int getParam() {
                return param;
            }

            public void setParam(int param) {
                this.param = param;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            @Override
            void handle(Algo algo) {
                algo.setAlgoParam(param, value);
            }
        }
    }

    public static AlgoConfigurationBuilder builder() {
        return new AlgoConfigurationBuilder();
    }

    public static class AlgoConfigurationBuilder {

        private final AlgoConfiguration algoConfiguration = new AlgoConfiguration();

        public AlgoConfiguration build() {
            return algoConfiguration;
        }

        public AlgoConfigurationBuilder withId(int id) {
            algoConfiguration.setId(id);
            return this;
        }

        public AlgoConfigurationBuilder withTask(String task) {
            algoConfiguration.setTask(task);
            return this;
        }

        public AlgoConfigurationBuilder withAuthor(String author) {
            algoConfiguration.setAuthor(author);
            return this;
        }

        public AlgoConfigurationBuilder withDescription(String description) {
            algoConfiguration.setDescription(description);
            return this;
        }

        public AlgoConfigurationBuilder withSteps(AlgoSteps steps) {
            algoConfiguration.setSteps(steps);
            return this;
        }

        public AlgoConfigurationBuilder withStepsArray(AlgoStep... steps) {
            AlgoSteps algoSteps = new AlgoSteps();
            algoSteps.setSteps(Arrays.stream(steps).collect(Collectors.toList()));
            algoConfiguration.setSteps(algoSteps);
            return this;
        }
    }
}