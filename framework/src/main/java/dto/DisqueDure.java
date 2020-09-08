package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DisqueDure extends Composant implements Serializable {

    private int taille;
    private int tailleLibre;
    private String unite;
}
