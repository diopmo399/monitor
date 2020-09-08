package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.DtoRecuperation.ServerHoteDto;
import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.mapper.ServerHoteMapper;
import africa.atps.monitordata.models.Contact;
import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.models.ServiceServer;
import africa.atps.monitordata.repositories.ContactRepository;
import africa.atps.monitordata.repositories.EtatServerRepository;
import africa.atps.monitordata.repositories.ServerHoteRepository;
import africa.atps.monitordata.repositories.ServiceRepository;
import africa.atps.monitordata.service.IServerHoteService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerHoteServiceImpl implements IServerHoteService {

    private final ServerHoteRepository serverHoteRepository;
    private final ServiceRepository serviceRepository;
    private final ContactRepository contactRepository;
    private final EtatServerRepository etatServerRepository;

    private final ServerHoteMapper serverHoteMapper = Mappers.getMapper(ServerHoteMapper.class);

    public ServerHoteServiceImpl(ServerHoteRepository serverHoteRepository, ServiceRepository serviceRepository, ContactRepository contactRepository, EtatServerRepository etatServerRepository) {
        this.serverHoteRepository = serverHoteRepository;
        this.serviceRepository = serviceRepository;
        this.contactRepository = contactRepository;
        this.etatServerRepository = etatServerRepository;
    }

    @Override
    public Page<ServerHoteDto> getAllServerHote(Pageable page)
    {
        Page<ServerHote> serverHotes = serverHoteRepository.findAllServer(page);
        List<ServerHoteDto> serverHotesList = serverHotes.getContent()
            .stream()
            .map(serverHoteMapper::sourceToDestination)
            .collect(Collectors.toList());
        serverHotesList.forEach(serverHoteDto -> serverHoteDto.setEtatServer(
                etatServerRepository.findTopByServerHoteOrderByDateAnalyseDesc(serverHoteMapper.destinationToSource(serverHoteDto))
                )
        );


        return new PageImpl<>(serverHotesList , page , serverHotes.getTotalElements());

    }

    @Override
    public List<ServerHote> getAllServerHoteIsScenarioNull()
    {
        return serverHoteRepository.findAllByScenarioIsNull();
    }


    @Override
    public ServerHote getServerHoteById(Long id) throws ResourceNotFoundException {
        return serverHoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun server trouve avec l'id = " + id )) ;
    }

    @Override
    public ServerHote getServerHoteByEtatServer(Long id) throws ResourceNotFoundException {
        EtatServer etatServer = etatServerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Aucun serveur hote correspondant trouver"));
        ServerHote serverHote = serverHoteRepository.findServerHoteByEtatServersContaining(etatServer);
        serverHote.setEtatServers(null);
        return serverHote;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServerHote saveServerHote(ServerHote serverHote) throws CustomException {
        if(serverHote.getId() != null)
                throw new CustomException("L'id doit etre null lors de la creation");
        List<ServiceServer> serviceServers = serverHote.getServices();
        serverHote.setServices(null);
        List<Contact> contacts = serverHote.getContacts();
        serverHote.setContacts(null);
        ServerHote serverHotetmp = serverHoteRepository.save(serverHote);

        if (serviceServers != null)
        serviceServers.forEach(serviceServer ->{
            serviceServer.setServerHote(serverHotetmp);
            //serviceRepository.save(serviceServer);
            serviceRepository.saveAndFlush(serviceServer);
        });

        if (contacts != null)
        contacts.forEach(contact -> {
            contact.setServerHote(serverHotetmp);
            //contactRepository.save(contact);
            contactRepository.saveAndFlush(contact);
        });

        serverHotetmp.setServices(serviceServers);
        serverHotetmp.setContacts(contacts);
        return serverHotetmp ;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServerHote updateServerHote(ServerHote serverHote) throws ResourceNotFoundException {
        return serverHoteRepository.findById(serverHote.getId())
                .map(serverHote1 -> {
                    List<ServiceServer> serviceServers = serverHote.getServices();
                    serverHote.setServices(null);
                    List<Contact> contacts = serverHote.getContacts();
                    serverHote.setContacts(null);
                    List<EtatServer> etatServers = serverHote.getEtatServers();
                    serverHote.setEtatServers(null);

                    if (serviceServers != null)
                        serviceServers.forEach(serviceServer ->{
                            serviceServer.setServerHote(serverHote1);
                            //serviceRepository.save(serviceServer);
                            serviceRepository.saveAndFlush(serviceServer);
                        });

                    if (contacts != null)
                        contacts.forEach(contact -> {
                            contact.setServerHote(serverHote1);
                            //contactRepository.save(contact);
                            contactRepository.saveAndFlush(contact);
                        });
                    if (etatServers != null)
                        etatServers.forEach(etatServer -> {
                            etatServer.setServerHote(serverHote1);
                            //etatServerRepository.save(etatServer);
                            etatServerRepository.saveAndFlush(etatServer);
                        });

                    serverHote.setServices(serviceServers);
                    serverHote.setContacts(contacts);
                    serverHote.setEtatServers(etatServers);

                   // return serverHoteRepository.saveAndFlush(serverHote);
                    return serverHoteRepository.save(serverHote);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Aucun server avec l'id = " + serverHote.getId()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteServerHote(Long id) throws ResourceNotFoundException {
        ServerHote serverHote = serverHoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("le server n'existe pas"));
         serverHoteRepository.delete(serverHote);
    }

    @Override
    public void deleteAll() {
        serverHoteRepository.deleteAll();
    }
}
