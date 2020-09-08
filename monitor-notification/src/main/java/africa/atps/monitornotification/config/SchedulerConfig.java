package africa.atps.monitornotification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableAsync
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegister) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setErrorHandler(t -> System.out.println("Une exception " + t.getMessage()));
        taskScheduler.setPoolSize(5000);
        taskScheduler.setThreadNamePrefix("Monitor-notification-job-pool-");
        taskScheduler.initialize();
        scheduledTaskRegister.setTaskScheduler(taskScheduler);
    }
}
