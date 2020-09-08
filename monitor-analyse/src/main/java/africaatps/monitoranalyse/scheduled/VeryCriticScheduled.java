package africaatps.monitoranalyse.scheduled;

import africaatps.monitoranalyse.config.AnalyseScheduler;
import dto.ENUM.Criticite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Cette classe permet d executer les scenaris de niveau verycritic
 */
@Component
public class VeryCriticScheduled extends AnalyseScheduler {
    private final String fixedRate = "${schedule.priority.veryCritic}"   ;
    @Value("#{T(java.time.Duration).parse('${schedule.priority.veryCritic}')}")
    private Duration duree;
    /**
      * Cette fonction execute tous les scenaris de niveau verycritic
      */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled(Criticite.VERYCRITIC , duree);
    }
}

