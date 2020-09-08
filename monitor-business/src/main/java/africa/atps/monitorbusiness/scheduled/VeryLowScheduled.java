package africa.atps.monitorbusiness.scheduled;

import africa.atps.monitorbusiness.config.TestScheduler;
import dto.ENUM.Criticite;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Cette classe per
 */
@Component
public class VeryLowScheduled extends TestScheduler {
    private final String fixedRate = "${schedule.priority.veryLow}"   ;
        /**
          * Cette fonction execute tous les scenaris de niveau verylow
          */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled(Criticite.VERYLOW);
    }
}

