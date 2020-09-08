package africa.atps.monitorbusiness.scheduled;

import africa.atps.monitorbusiness.config.TestScheduler;
import dto.ENUM.Criticite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.Predicats;

import java.time.Duration;
import java.util.Arrays;

/**
 * Cette classe permet d executer les scenaris de niveau criticite medium
 */
@Component
public class MediumScheduled extends TestScheduler {
    private final String fixedRate = "${schedule.priority.medium}"   ;
    /**
      * Cette fonction execute tous les scenaris de niveau vlow
      */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled(Criticite.MEDIUM);
    }
}

