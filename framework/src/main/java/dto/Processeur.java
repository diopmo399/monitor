package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Processeur extends Composant implements Serializable {

    private Long nombreDeCoeur;
    private String frequeParCoeur;
    private int tauxUtilisation;
}
