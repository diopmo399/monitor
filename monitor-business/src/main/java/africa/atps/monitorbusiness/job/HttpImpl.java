package africa.atps.monitorbusiness.job;

import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.Tache;
import dto.Resultat;
import dto.ServiceServer;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import utils.TacheImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static java.lang.System.out;

/**
 * Implemente les tests http
 */
@Builder
public class HttpImpl extends TacheImpl {
    private final ServiceServer serviceServer;
    private final String path;
    private final String baseUrl;
    private final WebClient.Builder callBackClientBuilder;

    // private Mono response;
    // private  webClient;

    /**
     * Cette fonction permet de faire le test http
     * @return
     */
    protected void job() {
        // Mono response;
        out.println("doing http");
        Resultat resultat = new Resultat();
        resultat.setDateCheck(new Date());
        resultat.setTache(Tache.HTTP);
        Instant start = Instant.now();
        out.println(serviceServer.getUrl());
        out.println(serviceServer.getContent());
        WebClient webClient = WebClient
                .builder()
                .baseUrl(serviceServer.getUrl().trim())
                .build();

        switch(this.serviceServer.getMethod()){
            case GET: webClient.get().retrieve()
                    .onStatus(
                            HttpStatus::isError, clientResponse -> {
                                out.println(clientResponse.statusCode());
                                resultat.setStatus(clientResponse.statusCode().toString());
                                resultat.setStatusConnexion(ConnectiviteEnum.DOWN);
                                return Mono.empty();
                            }
                    )
                    .onStatus(
                            HttpStatus::is2xxSuccessful, clientResponse -> {
                                out.println(clientResponse.statusCode());
                                resultat.setStatus(clientResponse.statusCode().toString());
                                resultat.setStatusConnexion(ConnectiviteEnum.UP);
                                return Mono.empty();
                            }
                    )
                    .bodyToMono(String.class)
                    .doOnError(
                            throwable -> {
                                out.println(throwable.getMessage()
                                );
                                resultat.setStatus(throwable.getMessage());
                                resultat.setStatusConnexion(ConnectiviteEnum.DOWN);
                            }
                    )
                    .doFinally( signalType -> {
                                resultat.setDuree(Duration.between(start, Instant.now()));
                                send(resultat, callBackClientBuilder, baseUrl, path).subscribe();
                            }
                    )
                    .subscribe()
            ;
            break;
            case POST: webClient.post()
                    .bodyValue(serviceServer.getContent().toString())
                    .retrieve()
                    .onStatus(
                            HttpStatus::isError, clientResponse -> {
                                out.println(clientResponse.statusCode());
                                resultat.setStatus(clientResponse.statusCode().toString());
                                resultat.setStatusConnexion(ConnectiviteEnum.DOWN);
                                return Mono.empty();
                            }
                    )
                    .onStatus(
                            HttpStatus::is2xxSuccessful, clientResponse -> {
                                out.println(clientResponse.statusCode());
                                resultat.setStatus(clientResponse.statusCode().toString());
                                resultat.setStatusConnexion(ConnectiviteEnum.UP);
                                return Mono.empty();
                            }
                    )
                    .bodyToMono(String.class)
                    .doOnError(
                            throwable -> {
                                out.println(throwable.getMessage()
                                );
                                resultat.setStatus(throwable.getMessage());
                                resultat.setStatusConnexion(ConnectiviteEnum.DOWN);
                            }
                    )
                    .doFinally( signalType -> {
                                resultat.setDuree(Duration.between(start, Instant.now()));
                                send(resultat, callBackClientBuilder, baseUrl, path).subscribe();
                            }
                    )
                    .subscribe()
            ;
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.serviceServer.getMethod());
        }
    }

}
