package africa.atps.monitorgateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class GatewayFallback {

    @GetMapping("/error")
        public ResponseEntity<String> fallBack() {
           return new ResponseEntity<>("Une erreur est survenue", HttpStatus.GATEWAY_TIMEOUT);
        }
}
