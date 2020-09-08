package africa.atps.monitorgateway.config;



import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreaker {
    /**
     * Default Resilience4j circuit breaker configuration
     */
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(5)
                        .permittedNumberOfCallsInHalfOpenState(5)
                        .failureRateThreshold(50.0F)
                        .waitDurationInOpenState(Duration.ofMillis(30000))
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(10000)).build()).build());
    }

   /* @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("circuitbreaker_route", r -> r.path("/data/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("myCircuitBreaker").setFallbackUri("forward:/fallback/error"))
                                .rewritePath("/data/(?<segment>.*)", "/$\\{segment}")
                        )
                        .uri("lb://MONITOR-DATA")
                )
                .route("business_route", r -> r.path("/job/**")
                        .filters( f -> f.rewritePath("/job/(?<segment>.*)", "/$\\{segment}"))
                        .uri("lb://MONITOR-DATA")
                )
                        .build();
    }*/
}
