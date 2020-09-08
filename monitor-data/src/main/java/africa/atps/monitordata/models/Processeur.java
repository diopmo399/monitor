package africa.atps.monitordata.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class Processeur extends Composant implements Serializable {

    @Column(name = "nombre_coeur",nullable = false)
    private Long nombreDeCoeur;
    private String frequeParCoeur;
    private int tauxUtilisation;
}
