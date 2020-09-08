package africa.atps.monitordata.mapper;

import africa.atps.monitordata.DtoRecuperation.ServerHoteDto;
import africa.atps.monitordata.models.ServerHote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Permet le conversion de l'entite serveur a serverDto et vice versa
 * @author Mdiop
 */
@Mapper
public interface ServerHoteMapper {

    ServerHoteDto sourceToDestination(ServerHote serverHote);

    ServerHote destinationToSource(ServerHoteDto destination);

}
