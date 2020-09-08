package africa.atps.monitorgateway.config;

import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

@Configuration
public class SecurityConfig {
    @Bean
    public CorsConfiguration corsConfiguration(RoutePredicateHandlerMapping routePredicateHandlerMapping) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList(CorsConfiguration.ALL)));
        corsConfiguration.setAllowedMethods(Arrays.asList(
                HttpMethod.POST.name(),
                HttpMethod.GET.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name()
        ));
        corsConfiguration.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList(CorsConfiguration.ALL)));
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowCredentials(true);
        routePredicateHandlerMapping.setCorsConfigurations(
                new HashMap<String, CorsConfiguration>() {{ put("/**", corsConfiguration); }});
        return corsConfiguration;
    }

    /*@Bean
    SecurityWebFilterChain springWebFilterChain(final ServerHttpSecurity http) throws Exception {
        *//*return http
                .csrf().disable()
                .build();*//*
        return http
                .authorizeExchange()
                .matchers(pathMatchers("/data/**")).authenticated() // require a logged-in user for api calls
                .anyExchange().permitAll()
                .and().httpBasic().disable()
                .csrf().disable()
                .oauth2Client()
                .and()
                .oauth2Login()
                .and()
                .build();
    }*/
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            ReactiveClientRegistrationRepository clientRegistrationRepository) {
        // Authenticate through configured OpenID Provider
        http.oauth2Login();

        // Also logout at the OpenID Connect provider
        http.logout(logout -> logout.logoutSuccessHandler(
                new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));

        // Require authentication for all requests
        http.authorizeExchange().anyExchange().permitAll();

        // Allow showing /home within a frame
        // http.headers().frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN);

        // Disable CSRF in the gateway to prevent conflicts with proxied service CSRF
        http.csrf().disable();
        return http.build();
    }
}
