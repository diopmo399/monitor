package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contact {
    private Long id;
    private String Prenom;
    private String nom;
    private String email;
    private String tel;
    private String fonction;

    private ServerHote serverHote;
}
