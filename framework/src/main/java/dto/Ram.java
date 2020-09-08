package dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Ram extends Composant implements Serializable {
    private int memoireTotal;
    private int memoireLibre;
    private String unite;
}
