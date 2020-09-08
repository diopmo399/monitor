package africa.atps.monitornotification.job;

import africa.atps.monitornotification.services.ISmsService;
import dto.Notification;
import lombok.Builder;
import org.springframework.web.reactive.function.client.WebClient;
import utils.TacheNotifImpl;

/**
 * Implemente les tests http
 */
@Builder
public class NotificationSMSImpl extends TacheNotifImpl {
    private final Notification notification;
    private final ISmsService iSmsService;
    private final String baseUrl;
    private final String pathUpdate;
    private final WebClient.Builder callBackClientBuilder;

    @Override
    protected void job() {
        System.out.println("doing notification sms");

        Boolean isSend = iSmsService.sendSMS(notification);
        System.out.println(isSend);
        if (isSend){
            notification.setIsSend(true);
            delete(notification , callBackClientBuilder , baseUrl , pathUpdate).subscribe();
        }

        }

    }

