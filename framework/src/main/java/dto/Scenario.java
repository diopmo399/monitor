package dto;


import dto.ENUM.Tache;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Scenario implements Serializable {

    private Long id;

    private int frequence;

    private List<Resultat> resultats;

    private List<Tache> taches;

    private ServerHote serverHote;
}
