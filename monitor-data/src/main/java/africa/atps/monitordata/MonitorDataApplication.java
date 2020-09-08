package africa.atps.monitordata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@RefreshScope
@EnableScheduling
public class MonitorDataApplication  {
    public static void main(String[] args) {

        SpringApplication.run(MonitorDataApplication.class, args);

    }
}
