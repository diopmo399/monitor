package africa.atps.monitordata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerHote implements Serializable {
    // TODO: 1/29/20 definir les attributs de la classe serveurHote
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String adressIp;
    private int port;
    private String login;
    private String password;
    private String token;
    private Duration timeout;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Composant> composants;

    @OneToMany(mappedBy = "serverHote",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceServer> services;

    @JsonIgnore
    @OneToOne(mappedBy = "serverHote", cascade = CascadeType.ALL)
    private Scenario scenario;

    @OneToMany(mappedBy = "serverHote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "serverHote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EtatServer> etatServers;
}
