package africa.atps.monitornotification.services.Impl;

import africa.atps.monitornotification.services.ISmsService;
import dto.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import utils.ResponseSms;
import utils.TemplateSms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static java.nio.charset.StandardCharsets.UTF_8;
import static utils.APINotification.BASEURLAPISMS;

@Service
public class SmsServiceImpl implements ISmsService {


    @Override
    public Boolean sendSMS(Notification notification) {
        System.out.println("_______________sendSMS___________________");
        AtomicReference<Boolean> isSend = new AtomicReference<>(false);
        try {
            List<TemplateSms> templateSmsList = new ArrayList<>();
            List<ResponseSms> responseSmsList = new ArrayList<>();
            notification.getServerHote().getContacts().forEach(
                    contact -> {
                        templateSmsList.add(
                                TemplateSms.builder()
                                        .operateur("GETI")
                                        .sender("ATPS")
                                        .text(redactionSMS(notification))
                                        .telephone(contact.getTel())
                                        .build()
                        );
                    }
            );
            templateSmsList
                    .stream()
                    .peek( templateSms -> {
                        ResponseSms responseSms = send(templateSms);
                        if (responseSms != null) {
                            isSend.set(true);
                        }

                    })
                    .collect(Collectors.toList());



        }catch (Exception e){

            System.out.println(e.getMessage());
            System.out.println("erreur de recuperation du serverHote");
        }
        return isSend.get();
    }

    private String redactionSMS(Notification notification){
        return "Bonjour, ATPS MONITORING vous informe que le serveur: " + notification.getEtatServer()
                .getServerHote().getNom() + " est actuellement en panne ";
    }
    ResponseSms send(TemplateSms request){
        WebClient webClient = WebClient
                .builder()
                .baseUrl(BASEURLAPISMS.trim())
                .build();
        return webClient.post()
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString(("adminolv" + ":" + "Aspire@1365").getBytes(UTF_8)))
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatus::isError, clientResponse -> {
                            return Mono.empty();
                        }
                )
                .onStatus(
                        HttpStatus::is2xxSuccessful, clientResponse -> {
                            return Mono.empty();
                        }
                )
                .bodyToMono(ResponseSms.class)
                .doOnError(
                        throwable -> {
                            out.println(throwable.getMessage()
                            );

                        }
                )
                .doFinally(out::println)
                .block();}
}
