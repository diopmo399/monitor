package africa.atps.monitorbusiness.job;

import dto.Scenario;
import dto.ServerHote;
import dto.ServiceServer;
import lombok.Builder;
import org.springframework.web.reactive.function.client.WebClient;
import utils.Predicats;

/**
 * Cette classe permet d'executer les tests en fonction du type de tache
 */
@Builder
public class Tester {
    private final String path;
    private final String baseUrl;
    private final WebClient.Builder callBackClientBuilder;
    /**
     * execute le ping
     * @param serverHote les pings se font sur les servers Hotes
     */
    private void executePing(ServerHote serverHote){
        PingImpl.builder()
                .serverHote(serverHote)
                .baseUrl(baseUrl)
                .path(path)
                .callBackClientBuilder(callBackClientBuilder)
                .build()
                .doit();
    }

    /**
     * execute le test Telnet
     * @param serverHote les telnet se font sur les servers Hotes
     */
    public void executeTelnet(ServerHote serverHote){
        TelnetImpl.builder()
                .serverHote(serverHote)
                .baseUrl(baseUrl)
                .path(path)
                .callBackClientBuilder(callBackClientBuilder)
                .build()
                .doit();
    }

    /**
     * execute le  test http
     * @param serviceServer
     */
    private void executeHttp(ServiceServer serviceServer){
        // System.out.println("test http");
        HttpImpl.builder()
                .serviceServer(serviceServer)
                .baseUrl(baseUrl)
                .callBackClientBuilder(callBackClientBuilder)
                .path(path)
                .build()
                .doit();
    }

    /**
     * execute le te test
     * @param scenario en entrée
     */
    public void executeTest(Scenario scenario) {

        if(Predicats.isPing.test(scenario))
            executePing(scenario.getServerHote());

        if(Predicats.isTELNET.test(scenario))
            executeTelnet(scenario.getServerHote());

        if (Predicats.isHTTP.test(scenario))
            scenario.getServerHote().getServices()
                    .forEach(this::executeHttp);
    }
}
