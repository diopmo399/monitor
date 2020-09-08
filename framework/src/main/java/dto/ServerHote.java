package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
public class ServerHote implements Serializable {

    private Long id;
    private String nom;
    private String adressIp;
    private int port;
    private String login;
    private String password;
    private String token;
    private Duration timeout;

    private List<Composant> composants;

    private List<ServiceServer> services;

    private List<Scenario> scenarios;

    private List<EtatServer> etatServers;

    private List<Contact> contacts;


}
