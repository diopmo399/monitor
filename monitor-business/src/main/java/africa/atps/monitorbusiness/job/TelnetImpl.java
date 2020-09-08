package africa.atps.monitorbusiness.job;

import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.Tache;
import dto.Resultat;
import dto.ServerHote;
import lombok.Builder;
import org.springframework.web.reactive.function.client.WebClient;
import utils.APIMessageMapping;
import utils.TacheImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Builder
public class TelnetImpl extends TacheImpl {
    private final ServerHote serverHote;
    private final String path;
    private final String baseUrl;
    private final WebClient.Builder callBackClientBuilder;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    /**
     * Cette fonction nous permet de faire les test telnet
     */
    @Override
    public void job(){
        System.out.println("doing telnet");
        System.out.println(serverHote.getAdressIp() +" : "+ serverHote.getPort() );
        Resultat resultat = new Resultat();
        resultat.setTache(Tache.TELNET);
        resultat.setDateCheck(new Date());
        try {
            Instant start    = Instant.now();
            startConnection(serverHote.getAdressIp(),serverHote.getPort());
            resultat.setDuree(Duration.between(start,Instant.now()));
            resultat.setStatusConnexion(ConnectiviteEnum.UP);
            resultat.setStatus(APIMessageMapping.SUCCESSFULL);
            stopConnection();
        } catch (IOException e) {
           resultat.setStatusConnexion(ConnectiviteEnum.DOWN);
           resultat.setStatus(e.getMessage());
        }
        finally {
            send(resultat, callBackClientBuilder, baseUrl, path).subscribe();
        }
        }

}
