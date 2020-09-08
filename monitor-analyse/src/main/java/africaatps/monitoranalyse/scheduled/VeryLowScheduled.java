package africaatps.monitoranalyse.scheduled;

import africaatps.monitoranalyse.config.AnalyseScheduler;
import dto.ENUM.Criticite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Cette classe per
 */
@Component
public class VeryLowScheduled extends AnalyseScheduler {
    private final String fixedRate = "${schedule.priority.veryLow}"   ;
    @Value("#{T(java.time.Duration).parse('${schedule.priority.veryLow}')}")
    private Duration duree;
        /**
          * Cette fonction execute tous les scenaris de niveau verylow
          */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled(Criticite.VERYLOW , duree);
    }
}

