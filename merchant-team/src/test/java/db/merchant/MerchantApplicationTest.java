package db.merchant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Context loads test
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MerchantApplicationTest extends WebWithPostgresContainer {

    @Test
    public void contextLoad() {
    }
}