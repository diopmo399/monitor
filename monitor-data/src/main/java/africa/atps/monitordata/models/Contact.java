package africa.atps.monitordata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable {
    // todo: 11/02/2020 definir la classe contact
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Prenom;
    private String nom;
    private String email;
    private String tel;
    private String fonction;

    @ManyToOne
    @JsonIgnore
    private ServerHote serverHote;

}
