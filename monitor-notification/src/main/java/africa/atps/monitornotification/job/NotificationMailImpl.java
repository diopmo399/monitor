package africa.atps.monitornotification.job;

import africa.atps.monitornotification.services.IMailService;
import dto.Notification;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import utils.TacheNotifImpl;

/**
 * Implemente les tests http
 */
@Builder
public class NotificationMailImpl extends TacheNotifImpl {
    private final Notification notification;
    private final String baseUrl;
    private final String pathUpdate;

    private final WebClient.Builder callBackClientBuilder;

    private ResponseEntity  response;
    private final IMailService iMailService;

    @Override
    protected void job() {
        System.out.println("doing notification mail");
        System.out.println(callBackClientBuilder);
        System.out.println(notification);

        Boolean isSend = iMailService.sendEmail(notification);
        if (isSend){
            notification.setIsSend(true);
            delete(notification, callBackClientBuilder, baseUrl , pathUpdate).subscribe();
        }

        }

    }

