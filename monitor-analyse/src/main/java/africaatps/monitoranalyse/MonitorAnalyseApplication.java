package africaatps.monitoranalyse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@RefreshScope
public class MonitorAnalyseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorAnalyseApplication.class, args);
    }

}
