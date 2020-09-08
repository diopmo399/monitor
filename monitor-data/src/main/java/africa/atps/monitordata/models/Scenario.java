package africa.atps.monitordata.models;

import dto.ENUM.Criticite;
import dto.ENUM.Tache;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scenario implements Serializable {
    // TODO: 1/29/20 definir la classe scenario
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private Criticite criticite;

    @Enumerated(EnumType.STRING)
    @Column
    @ElementCollection(targetClass = Tache.class)
    private Set<Tache> taches;

    @OneToOne
    private ServerHote serverHote;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scenario")
    private List<Resultat> resultats;
}
