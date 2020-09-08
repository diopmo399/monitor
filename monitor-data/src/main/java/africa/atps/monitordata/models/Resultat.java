package africa.atps.monitordata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.Tache;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resultat {
    // TODO: 1/29/20 definir la classe resultat
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ConnectiviteEnum statusConnexion;
    private Date dateCheck;
    @Column(columnDefinition = "TEXT")
    private String status ;
    private Tache tache;
    private Duration duree ;


    @JsonIgnore
    @ManyToOne
    private Scenario scenario;
}
