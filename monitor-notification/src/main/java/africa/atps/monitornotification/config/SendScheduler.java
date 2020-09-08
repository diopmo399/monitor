package africa.atps.monitornotification.config;


import africa.atps.monitornotification.job.Tester;
import africa.atps.monitornotification.services.IMailService;
import africa.atps.monitornotification.services.ISmsService;
import dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.System.out;

/**
 * Cette fonction permet d'executer les scenaris
 * nous avons des scenaris de niveau verycritic, jusqua very low
 */
public class SendScheduler {
    @Autowired
    WebClient.Builder webClientBuilder;




    private final String baseUrl = "http://monitor-data";
    private final IMailService iMailService;
    private final ISmsService iSmsService;

    public SendScheduler(IMailService iMailService , ISmsService iSmsService){
        this.iMailService = iMailService;
        this.iSmsService = iSmsService;
    }


    /**
     * Cette fonction permet de recuperer les notification non envoyer
     */
    public Flux<Notification> getNotificationNotSend()
    {
        return webClientBuilder
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/notification")
                                .build()
                )
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> {
                            out.println(clientResponse.statusCode());
                            return   clientResponse
                                    .bodyToMono(WebClientResponseException.class)
                                    .flatMap(Mono::error);
                        }
                )
                .bodyToFlux(Notification.class)
                ;
    }

    public void accept(Notification n) {
        System.out.println(n);

                Tester.builder()
                    .iMailService(iMailService)
                    .iSmsService(iSmsService)
                    .baseUrl(baseUrl)
                    .clientWebTest(webClientBuilder)
                    .pathUpdate("notification")
                    .build()
                    .executeTest(n);


    }

    public void scheduled()  {
        System.out.println("scheduled notification" );
        /*Arrays.stream( getNotificationNotSend()  )
                .forEach(this::accept);*/
        getNotificationNotSend()
                .filter(notification -> true)
                .subscribe(this::accept);
    }
}
