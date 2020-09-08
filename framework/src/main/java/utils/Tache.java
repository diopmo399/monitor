package utils;


import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;

@FunctionalInterface
public interface Tache {
    ExecutorService executor = Executors.newFixedThreadPool(1000);

    /**
     *Envoi le resultat vers le microserve monitor-data
     * @param  object generique
     */
    public default void send(URI uri, Object object){
        System.out.println("URI:" +uri);
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(uri ,object, Object.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    default Mono<Object> send(Object request, WebClient.Builder callBackClientBuilder, String baseUrl, String path){
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
                                        .flatMap(Mono::error)
                )
               .onStatus(HttpStatus::is4xxClientError,
                       clientResponse -> {
                           out.println(clientResponse.statusCode());
                           return clientResponse
                                   .bodyToMono(WebClientResponseException.class)
                                   .flatMap(Mono::error);
                       }
                       )
                .bodyToMono(Object.class)
                .doOnError(throwable -> out.println(throwable.getMessage()))
                .doFinally(out::println);
    }

    public Future<Void> doit();
}
