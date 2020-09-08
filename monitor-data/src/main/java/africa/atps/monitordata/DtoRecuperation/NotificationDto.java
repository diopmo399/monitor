package africa.atps.monitordata.DtoRecuperation;

import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.ServerHote;
import dto.ENUM.Tache;
import dto.ENUM.TypeNotificationEnum;
import lombok.Data;

import java.time.Duration;

@Data
public class NotificationDto {

    private Long id;
    private Boolean isSend;

    private TypeNotificationEnum type;
    private Tache motifTache;
    private Duration timeOute;
    private ServerHote serverHote;

    private EtatServer etatServer;
}
