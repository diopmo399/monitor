package africaatps.monitoranalyse.config;


import africaatps.monitoranalyse.job.AnalyseImpl;
import dto.ENUM.Criticite;
import dto.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import utils.Predicats;

import java.time.Duration;

/**
 * Cette fonction permet d'analyser les scenaris
 * nous avons des scenaris de niveau verycritic, jusqua very low
 */
public class AnalyseScheduler {
   /* @Autowired
    private LoadBalancerClient loadBalancer;*/

    @Autowired
    WebClient.Builder webClientBuilder;

    private final String baseUrl = "http://monitor-data";


/*BiFunction<String, String , URI> getUri = (appName, ressource) -> URI.create(loadBalancer.choose(appName).getUri() + ressource);*/


    /**
     * Cette fonction permet de recuperer les resultats en fonctions de la criticite
     * @param criticite
     */
    public Flux<Scenario> getScenaris(Criticite criticite, Duration duree)
    {
       /* RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Scenario[]> response = restTemplate
                .getForEntity(getUri.apply("monitor-data","/scenario/analyse/" + criticite + '/' + duree ),Scenario[].class);
        Scenario[] scenarios = response.getBody();
        System.out.println(Arrays.toString(scenarios));
        return scenarios;*/
        return webClientBuilder
                .baseUrl(baseUrl)
                .build()
                .get()
                /*.uri(
                        uriBuilder -> uriBuilder
                                .path("/scenario/analyse/criticite/")
                                .pathSegment(criticite.toString())
                                //.path("/")
                                .pathSegment(duree.toString())
                                .build()
                )*/
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/scenario/analyse/")
                                .pathSegment(criticite.toString())
                                .pathSegment(duree.toString())
                                .build()
                )
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(WebClientResponseException.class)
                                        .flatMap(Mono::error))
                .bodyToFlux(Scenario.class)
                ;
    }

    public void accept(Scenario s) {
        AnalyseImpl.builder()
                .scenario(s)
                .pathEtatServer("/etatserver/" + s.getServerHote().getId())
                .pathNotification("/notification")
                .callBackClientBuilder(webClientBuilder)
                .baseUrl(baseUrl)
                .build()
                .doit();
    }

    public void scheduled(Criticite criticite, Duration duree)  {
        System.out.println("scheduled " + criticite );

        getScenaris(criticite , duree)
                .filter(Predicats.serverHoteNotNull)
                .subscribe(this::accept);
        /*Arrays.stream( getScenaris(criticite, duree)  )
                .filter(Predicats.serverHoteNotNull)
                .forEach(this::accept);*/
    }
}
