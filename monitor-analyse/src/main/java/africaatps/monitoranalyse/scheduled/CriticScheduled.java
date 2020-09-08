package africaatps.monitoranalyse.scheduled;

import africaatps.monitoranalyse.config.AnalyseScheduler;
import dto.ENUM.Criticite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Cette classe permet d executer les scenaris de niveau criticite critic
 */
@Component
public class CriticScheduled extends AnalyseScheduler {
    private final String fixedRate = "${schedule.priority.critic}";
    @Value("#{T(java.time.Duration).parse('${schedule.priority.critic}')}")
    private Duration duree;
    /**
      * Cette fonction analyse tous les scenaris de niveau critic
      */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled(Criticite.CRITIC, duree);
    }
}

