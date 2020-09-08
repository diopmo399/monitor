package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Composant implements Serializable {
    private Long id;
    private String nom;

    private ServerHote serverHote;


}
