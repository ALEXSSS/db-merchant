package db.algo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import(SimpleAlgoImpl.class)
public class AlgoTeamAutoConfiguration {
}
