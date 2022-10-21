package db.merchant.signal.codebase.handlers;

import db.merchant.utils.PropagatedComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Configuration to automatically collect all subclasses of dev team defined handlers.
 *
 * Eliminates necessity to put {@link Component} like annotation on each newly defined handler.
 */
@Configuration
@ComponentScan(
        basePackages = "db.merchant.signal.codebase.handlers",
        includeFilters = @ComponentScan.Filter(PropagatedComponent.class)
)
class HandlersScanConfiguration {
}