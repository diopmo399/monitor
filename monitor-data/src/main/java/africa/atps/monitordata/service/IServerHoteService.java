package africa.atps.monitordata.service;

import africa.atps.monitordata.DtoRecuperation.ServerHoteDto;
import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.ServerHote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IServerHoteService {

    Page<ServerHoteDto> getAllServerHote(Pageable page) ;
    ServerHote getServerHoteById(Long id) throws ResourceNotFoundException;
    ServerHote saveServerHote(ServerHote serverHote) throws ResourceNotFoundException, CustomException;
    ServerHote updateServerHote(ServerHote serverHote) throws CustomException;
    ServerHote getServerHoteByEtatServer(Long id);
    List<ServerHote> getAllServerHoteIsScenarioNull();
    void deleteServerHote(Long id) throws ResourceNotFoundException;
    public void deleteAll();
}
