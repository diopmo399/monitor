package africaatps.monitoranalyse.scheduled;

import africaatps.monitoranalyse.config.AnalyseScheduler;
import africaatps.monitoranalyse.job.AnalyseImpl;
import dto.ENUM.Criticite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Cette classe permet d executer les scenaris de niveau default
 */
@Component
public class DefaultScheduled extends AnalyseScheduler {
    private final String fixedRate = "${schedule.priority.default}";
    @Value("#{T(java.time.Duration).parse('${schedule.priority.default}')}")
    private Duration duree;
    /**
      * Cette fonction execute tous les scenaris de niveau default
      */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled(Criticite.DEFAULT, duree);
    }
}
