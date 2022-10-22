package db.merchant.controller.system;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Convenience controller to redirect from root path to swagger
 */
@Controller
public class SystemController {

    private final ResponseEntity<?> redirectResponse = ResponseEntity
            .status(HttpStatus.FOUND)
            .header(
                    HttpHeaders.LOCATION,
                    "/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config"
            ).build();

    @GetMapping
    public ResponseEntity<?> redirectToSwagger() {
        return redirectResponse;
    }
}
