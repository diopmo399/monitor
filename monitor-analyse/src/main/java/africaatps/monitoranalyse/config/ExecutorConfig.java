package africaatps.monitoranalyse.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class ExecutorConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(115);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(5000);
        executor.setThreadNamePrefix("TreatAnalyse-");
        executor.initialize();
        return executor;
    }
}


