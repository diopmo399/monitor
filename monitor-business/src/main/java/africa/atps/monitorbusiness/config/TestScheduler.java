package africa.atps.monitorbusiness.config;


import africa.atps.monitorbusiness.job.Tester;
import dto.ENUM.Criticite;
import dto.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import utils.Predicats;

import static java.lang.System.out;


/**
 * Cette fonction permet d'executer les scenaris
 * nous avons des scenaris de niveau verycritic, jusqua very low
 */
public class TestScheduler {
    @Autowired
    WebClient.Builder webClientBuilder;

    private final String baseUrl = "http://monitor-data";

    /**
     * Cette fonction permet de recuperer les scenaris en fonctions de la criticite
     * @param criticite
     * @return
     */
    public Flux<Scenario> getScenaris(Criticite criticite)
    {
        return webClientBuilder
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/scenario/criticite/")
                                .pathSegment(criticite.toString())
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
                .bodyToFlux(Scenario.class)
                ;
    }
    public void accept(Scenario s) {
        Tester.builder()
                .callBackClientBuilder(webClientBuilder)
                .baseUrl(baseUrl)
                .path("/resultat/" + s.getId())
                .build()
                .executeTest(s);
    }

    public void scheduled(Criticite criticite)  {
        out.println("scheduled " + criticite );
        getScenaris(criticite)
                .filter(Predicats.serverHoteNotNull)
                .subscribe(this::accept)
        ;
    }
}
