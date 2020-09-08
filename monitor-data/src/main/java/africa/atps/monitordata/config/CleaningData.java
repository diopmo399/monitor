package africa.atps.monitordata.config;

import africa.atps.monitordata.aspect.RemoveOldEtatServer;
import africa.atps.monitordata.aspect.RemoveOldResultat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CleaningData {
    @Scheduled(fixedRateString = "PT1M")
    @Transactional
    @RemoveOldResultat
    @RemoveOldEtatServer
    public void clean(){
    }
}
