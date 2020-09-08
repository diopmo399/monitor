package africa.atps.monitordata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtatServer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double tauxEchecPing;
    private double tauxEchecHttp;
    private double tauxEchecTelnet;
    private double etatConnexion;
    private Date dateAnalyse;
    @JsonIgnore
    @OneToMany(mappedBy = "etatServer", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @JsonIgnore
    @ManyToOne
    private ServerHote serverHote;


}
