package africa.atps.monitorbusiness.scheduled;

import africa.atps.monitorbusiness.config.TestScheduler;
import dto.ENUM.Criticite;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Cette classe permet d executer les scenaris de niveau criticite low
 */
@Component
public class LowScheduled extends TestScheduler {
    private final String fixedRate = "${schedule.priority.low}"   ;
    /**
      * Cette fonction execute tous les scenaris de niveau default
      */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled(Criticite.LOW);
    }
}

