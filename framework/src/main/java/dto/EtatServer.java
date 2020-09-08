package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtatServer /*implements Serializable*/ {

    private Long id;
    private Double tauxEchecPing;
    private Double tauxEchecHttp;
    private Double tauxEchecTelnet;
    private Double etatConnexion;
    private Date dateAnalyse;
    private List<Notification> notifications;
    private ServerHote serverHote;

}
