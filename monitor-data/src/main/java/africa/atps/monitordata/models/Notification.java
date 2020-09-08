package africa.atps.monitordata.models;


import dto.ENUM.Tache;
import dto.ENUM.TypeNotificationEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Duration;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
    // TODO: 1/29/20 definir la classe Notification

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean isSend;
    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private TypeNotificationEnum type;
    @NotNull(message = "Name may not be null")
    private String name;
    @NotBlank(message = "Description may not be blank")
    private String description;
    private Tache motifTache;
    private Duration timeOute;

    @ManyToOne
    private EtatServer etatServer;
}
