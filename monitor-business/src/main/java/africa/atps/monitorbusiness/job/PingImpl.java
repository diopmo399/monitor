package africa.atps.monitorbusiness.job;

import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.Tache;
import dto.Resultat;
import dto.ServerHote;
import lombok.Builder;
import org.springframework.web.reactive.function.client.WebClient;
import utils.APIMessageMapping;
import utils.TacheImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Cette classe permet d'implementer les tests de connectivite
 */
@Builder
public class PingImpl extends TacheImpl {
    private final ServerHote    serverHote;
    private final String path;
    private final String baseUrl;
    private final WebClient.Builder callBackClientBuilder;

    /**
     * Cette fonction permet d'executer le ping et d'envoyer le resultat au monitor-data
     */
    protected void job()  {
        System.out.println("doing ping");
        Resultat resultat = new Resultat();
        resultat.setTache(Tache.PING);
        resultat.setDateCheck(new Date());
        InetAddress geek  ;
        boolean status;
        try {
            geek = InetAddress.getByName(serverHote.getAdressIp().trim());
            Instant start    = Instant.now();
            status = geek.isReachable((int)serverHote.getTimeout().toMillis());
            resultat.setDuree(Duration.between(start,Instant.now()));
            resultat.setStatusConnexion(status?ConnectiviteEnum.UP:ConnectiviteEnum.DOWN);
            resultat.setStatus(status? APIMessageMapping.SUCCESSFULL:APIMessageMapping.NOT_FOUND);

        } catch (IOException e) {
            resultat.setStatusConnexion(ConnectiviteEnum.DOWN);
            resultat.setStatus(e.getMessage());
        } finally {
            //send(uri,resultat);
            send(resultat, callBackClientBuilder, baseUrl, path).subscribe();
        }
    }
}

