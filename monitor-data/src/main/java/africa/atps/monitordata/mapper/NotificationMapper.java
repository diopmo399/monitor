package africa.atps.monitordata.mapper;

import africa.atps.monitordata.DtoRecuperation.NotificationDto;
import africa.atps.monitordata.models.Notification;
import org.mapstruct.Mapper;

/**
 * Permet le conversion de l'entite serveur a serverDto et vice versa
 * @author Mdiop
 */
@Mapper
public interface NotificationMapper {

    NotificationDto sourceToDestination(Notification notification);

    Notification destinationToSource(NotificationDto destination);

}
