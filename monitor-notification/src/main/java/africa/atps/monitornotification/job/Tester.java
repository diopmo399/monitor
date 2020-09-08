package africa.atps.monitornotification.job;

import africa.atps.monitornotification.services.IMailService;
import africa.atps.monitornotification.services.ISmsService;
import dto.ENUM.TypeNotificationEnum;
import dto.Notification;
import lombok.Builder;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Cette classe permet d'executer les tests en fonction du type de tache
 */
@Builder
public class Tester {
    private final String baseUrl;
    private final String pathUpdate;
    private final WebClient.Builder clientWebTest;

    private final IMailService iMailService;
    private final ISmsService iSmsService;

    /**
     * execute les mails
     * @param notification pour les mails
     */
    private void executeMail(Notification notification){
        System.out.println(notification);
        NotificationMailImpl.builder()
                .notification(notification)
                .callBackClientBuilder(clientWebTest)
                .iMailService(iMailService)
                .baseUrl(baseUrl)
                .pathUpdate(pathUpdate)
                .build()
                .doit();
    }

    /**
     * execute le test sms
     * @param notification pour les sms
     */
    public void executeSMS(Notification notification){
        System.out.println(notification);
        NotificationSMSImpl.builder()
                .notification(notification)
                .iSmsService(iSmsService)
                .callBackClientBuilder(clientWebTest)
                .baseUrl(baseUrl)
                .pathUpdate(pathUpdate)
                .build()
                .doit();
    }




    /**
     * execute le te test
     * @param notification en entrée
     */
    public void executeTest(Notification notification){
        System.out.println("________________ callBack: " + clientWebTest);
        if(notification.getType() ==  TypeNotificationEnum.EMAIL)
            executeMail(notification);
        if(notification.getType() ==  TypeNotificationEnum.SMS)
            executeSMS(notification);

    }
}
