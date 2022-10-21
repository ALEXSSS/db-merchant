package db.merchant.signal.codebase.handlers;

import db.merchant.utils.PropagatedComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// todo
@Configuration(proxyBeanMethods = false)
@ComponentScan(
        basePackages = "db.merchant.signal.codebase.handlers",
        includeFilters = @ComponentScan.Filter(PropagatedComponent.class)
)
class HandlersScanConfiguration {
}