package africa.atps.monitordata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Collections;
import java.util.List;
import static springfox.documentation.builders.PathSelectors.any;

@Configuration
public class SwaggerConfig {

   @Value(value = "${swagger.auth_uri}")
    private  String AUTH_SERVER      ;
   @Value(value = "${swagger.auth_clientid}")
    private  String CLIENT_ID  ;
   @Value(value = "${swagger.auth_secret}")
    private  String CLIENT_SECRET  ;
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("monitor-data").select()
                .apis(RequestHandlerSelectors.basePackage("africa.atps.monitordata"))
                .paths(any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(List.of(securityScheme()))
                .securityContexts(List.of(securityContext()));
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/protocol/openid-connect/token", "access_token"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(AUTH_SERVER + "/protocol/openid-connect/auth", CLIENT_ID, CLIENT_SECRET))
                .build();

        return new OAuthBuilder().name("monitor")
                .grantTypes(List.of(grantType))
                .scopes(List.of(scopes()))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[] {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("monitor", "Access monitor API")};
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        List.of(new SecurityReference("monitor", scopes())))
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "monitoring Services",
                "collection de service pour acceder aux donnees des resultats de tests", "1.0.0",
                null,
                new Contact("SES", "https://africa.atps/ses", "ses.itd@m2t.biz"),
                "License of API",
                "API license URL",
                Collections.emptyList()
        );
    }
}
