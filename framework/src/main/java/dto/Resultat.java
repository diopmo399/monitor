package dto;

import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.Tache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resultat {

    private Long id;

    private ConnectiviteEnum statusConnexion;

    private Date dateCheck;
    private String status;
    private Duration duree;
    private List<Notification> notifications;

    private Scenario scenario;
    private Tache tache;
}
