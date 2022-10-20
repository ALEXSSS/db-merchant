package db.algo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Necessary for spring-boot-starter-test to load a context
 */
@SpringBootApplication
public class SpringAlgoMockApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringAlgoMockApp.class, args);
    }
}
