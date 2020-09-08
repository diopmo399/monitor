package africa.atps.monitordata.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Ram extends Composant implements Serializable {
    private int memoireTotal;
    private int memoireLibre;
    private String unite;
}
