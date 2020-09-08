package africa.atps.monitornotification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("monitor-notificator").select()
                .apis(RequestHandlerSelectors.basePackage("*"))
                .paths(any()).build().apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "notifications Services",
                "collection de service pour acceder aux donnees des resultats de tests",
                "1.0.0",
                null,
                new Contact("SES", "https://africa.atps/ses", "ses.itd@m2t.biz"),
                "License of API",
                "API license URL",
                Collections.emptyList()
        );
    }
}
