package db.algo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SimpleAlgoImpl.class)
public class AlgoTeamAutoConfiguration {
}
