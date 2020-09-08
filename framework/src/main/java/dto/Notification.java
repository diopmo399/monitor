package dto;


import dto.ENUM.Tache;
import dto.ENUM.TypeNotificationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {

    private Long id;
    private Boolean isSend;

    private TypeNotificationEnum type;
    private Tache motifTache;
    private Duration timeOute;
    private ServerHote serverHote;

    private EtatServer etatServer;
}
