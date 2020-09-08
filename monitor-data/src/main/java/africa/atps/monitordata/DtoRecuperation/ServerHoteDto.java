package africa.atps.monitordata.DtoRecuperation;

import africa.atps.monitordata.models.EtatServer;
import dto.Composant;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
public class ServerHoteDto {
    private Long id;
    private String nom;
    private String adressIp;
    private int port;
    private String login;
    private String password;
    private String token;
    private Duration timeout;
    private EtatServer etatServer;
    private List<Composant> composants;

}
