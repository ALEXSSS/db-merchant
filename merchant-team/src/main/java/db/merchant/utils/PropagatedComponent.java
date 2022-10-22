package db.merchant.utils;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Adds inheritance to @Component annotation
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Inherited
public @interface PropagatedComponent {}
