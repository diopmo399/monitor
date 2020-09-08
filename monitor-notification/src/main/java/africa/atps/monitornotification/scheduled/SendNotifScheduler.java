package africa.atps.monitornotification.scheduled;

import africa.atps.monitornotification.config.SendScheduler;
import africa.atps.monitornotification.services.IMailService;
import africa.atps.monitornotification.services.ISmsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Cette classe permet d executer les scenaris de niveau default
 */
@Component
public class SendNotifScheduler extends SendScheduler {
    private final String fixedRate = "${schedule.notification.duree}";
    private final IMailService iMailService;
    private final ISmsService iSmsService;
    public SendNotifScheduler(IMailService iMailService, ISmsService iSmsService) {
        super(iMailService , iSmsService);
        this.iMailService = iMailService;
        this.iSmsService = iSmsService;
    }


    /**
      * Cette fonction execute tous les notification non envoyer
      */
    @Scheduled(fixedRateString = fixedRate )
    public void scheduled()  {
        super.scheduled();
    }
}

