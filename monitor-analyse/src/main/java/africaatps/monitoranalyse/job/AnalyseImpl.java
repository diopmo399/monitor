package africaatps.monitoranalyse.job;

import dto.ENUM.Tache;
import dto.ENUM.TypeNotificationEnum;
import dto.EtatServer;
import dto.Notification;
import dto.Resultat;
import dto.Scenario;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import utils.Predicats;
import utils.TacheImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static utils.APINotification.*;

/**
 * Implemente les tests http
 */
@Builder
public class AnalyseImpl extends TacheImpl {
    private final Scenario scenario;
    /*private final URI uri;
    private final URI notifUri;*/
    private final String pathEtatServer;
    private final String pathNotification;
    private final String baseUrl;
    private final WebClient.Builder callBackClientBuilder;
    private ResponseEntity  response;

    private Double tauxEchouer(List<Resultat> resultats){
        List<Resultat> Echouer = resultats
                .stream()
                .filter(Predicats.isDown)
                .collect(Collectors.toList());
        return resultats.size() == 0 ? null :  ((double) Echouer.size()/(double)resultats.size())*100;
    }

    private void createNotif(Double tauxEchec, Long id , Tache tache) {
        EtatServer etatServer = EtatServer.builder()
                .id(id)
                .build();
        //if (tauxEchec >= TESTMAIL.getMin() && tauxEchec < TESTMAIL.getMax()){
        if (tauxEchec >= TESTMAIL.getMin() && tauxEchec < TESTMAIL.getMax()){
            Notification notification = Notification.builder()
                    .isSend(false)
                    .motifTache(tache)
                    .type(TypeNotificationEnum.EMAIL)
                    .etatServer(etatServer)
                    .build();
           // send(notifUri , notification );
            send(notification, callBackClientBuilder , baseUrl , pathNotification).subscribe();
        }

        if (tauxEchec >= TESTSMS.getMin() && tauxEchec < TESTSMS.getMax()){
            Notification notification = Notification.builder()
                    .isSend(false)
                    .motifTache(tache)
                    .type(TypeNotificationEnum.SMS)
                    .etatServer(etatServer)
                    .build();
            send(notification , callBackClientBuilder , baseUrl , pathNotification ).subscribe();
            }
        if (tauxEchec >= TESTAPPEL.getMin() && tauxEchec <= TESTAPPEL.getMax()){
            Notification notification = Notification.builder()
                    .isSend(false)
                    .motifTache(tache)
                    .type(TypeNotificationEnum.APPEL)
                    .etatServer(etatServer)
                    .build();
            send(notification , callBackClientBuilder , baseUrl , pathNotification).subscribe();
            }
    }


    @Override
    protected void job() {
        System.out.println("doing analyse");
        System.out.println(scenario);
        EtatServer etatServer = new EtatServer();

        List<Resultat> ping = scenario.getResultats()
                .stream()
                .filter(Predicats.isPingResultat)
                .collect(Collectors.toList());
        List<Resultat> etatConnectivite = scenario.getResultats()
                .stream()
                .filter(Predicats.isPingResultat.or(Predicats.isTELNETResultat))
                .collect(Collectors.toList());
        List<Resultat> tauxEchec = scenario.getResultats()
                .stream()
                .filter(Predicats.isDown)
                .collect(Collectors.toList());

        System.out.println("___________PING__________");
        System.out.println(tauxEchouer(ping));
        etatServer.setTauxEchecPing(tauxEchouer(ping));

        List<Resultat> http = scenario.getResultats()
                .stream()
                .filter(Predicats.isHTTPResultat)
                .collect(Collectors.toList());
        etatServer.setTauxEchecHttp(tauxEchouer(http));

        List<Resultat> telnet = scenario.getResultats()
                .stream()
                .filter(Predicats.isTELNETResultat)
                .collect(Collectors.toList());
        etatServer.setTauxEchecTelnet(tauxEchouer(telnet));
        etatServer.setEtatConnexion(tauxEchouer(etatConnectivite));

        etatServer.setServerHote(scenario.getServerHote());
        etatServer.setDateAnalyse(new Date());

        try {
            EtatServer responseEntity =  sendEtat(etatServer, callBackClientBuilder , baseUrl , pathEtatServer).block();

            if (responseEntity != null){
                createNotif(responseEntity.getTauxEchecHttp(),responseEntity.getId() , Tache.HTTP);
                createNotif(responseEntity.getTauxEchecPing(), responseEntity.getId() , Tache.PING);
                createNotif(responseEntity.getTauxEchecTelnet(), responseEntity.getId() , Tache.TELNET);
            }


        } catch (Exception e){
           e.printStackTrace();
        }





        }

    private Mono<EtatServer> sendEtat(EtatServer request, WebClient.Builder callBackClientBuilder, String baseUrl, String path){
        return callBackClientBuilder
                .baseUrl(baseUrl)
                .build()
                .post()
                .uri(path)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(WebClientResponseException.class)
                                        .flatMap(Mono::error))
                .bodyToMono(EtatServer.class)
                .doOnError(throwable -> out.println(throwable.getMessage()))
                .doFinally(out::println);
    }

    }

