package utils;


import dto.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;

@FunctionalInterface
public interface TacheNotif {

    ExecutorService executor = Executors.newFixedThreadPool(1000);

    public default Mono<Object> delete(Notification request, WebClient.Builder callBackClientBuilder, String baseUrl, String path){
        return callBackClientBuilder
                .baseUrl(baseUrl)
                .build()
                .delete()
                .uri(
                        uriBuilder -> uriBuilder
                                .path(path)
                                .pathSegment(request.getId().toString())
                                .build()
                )
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
