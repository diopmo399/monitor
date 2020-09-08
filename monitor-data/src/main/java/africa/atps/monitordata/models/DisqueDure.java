package africa.atps.monitordata.models;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisqueDure extends Composant implements Serializable {

    private int taille;
    private int tailleLibre;
    private String unite;
}
