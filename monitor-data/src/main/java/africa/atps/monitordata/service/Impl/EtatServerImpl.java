package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.repositories.EtatServerRepository;
import africa.atps.monitordata.repositories.NotificationRepository;
import africa.atps.monitordata.repositories.ServerHoteRepository;
import africa.atps.monitordata.service.IEtatServerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;


@Service
public class EtatServerImpl implements IEtatServerService {
    private final EtatServerRepository etatServerRepository;
    private final ServerHoteRepository serverHoteRepository;
    @Value(value = "${cleaning.periode}")
    private Long periode;

    public EtatServerImpl(EtatServerRepository etatServerRepository, NotificationRepository notificationRepository, ServerHoteRepository serverHoteRepository) {
        this.etatServerRepository = etatServerRepository;
        this.serverHoteRepository = serverHoteRepository;
    }

    /**
     *
     * @param pageable recuperer les etats avec pagination
     * @param id identifiant du serveur hote
     * @return Page<EtatServer>
     */
    @Override
    public Page<EtatServer> getAllEtatServer(Pageable pageable , Long id) {
        return etatServerRepository.findAllByServerHoteId(pageable, id);
    }

    @Override
    public EtatServer getEtatServerById(Long id) throws ResourceNotFoundException {
        return etatServerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ce type de dto.EtatServer n'existe pas"));
    }

    /**
     *
     * @param etatServer a creer pour un serveur hote
     * @param id du serveur hote
     * @return l'etat serveur asocier a un serveur hote
     * permet de creer une notification si jamais le seuil d'echec depasse une certaine valeur
     */
    //@RemoveOldResultat
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public EtatServer saveEtatServer(EtatServer etatServer, Long id) {
        ServerHote serverHote = serverHoteRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Aucun serveurhote avec l'id: " + id + "trouver" ));

        etatServer.setServerHote(serverHote);
        //return etatServerRepository.saveAndFlush(etatServer);
       // etatServerRepository.deleteEtatServerByDateAnalyseIsBefore(Date.from(Instant.now().minusSeconds(60)));
       // etatServerRepository.deleteEtatServerByDateAnalyseIsBefore(Date.from(Instant.now().minusSeconds(periode)));
        return etatServerRepository.save(etatServer);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public EtatServer updateEtatServer(EtatServer etatServer) throws ResourceNotFoundException {
        EtatServer tmp = etatServerRepository.findById(etatServer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.EtatServer n'existe pas"));

        //return etatServerRepository.saveAndFlush(etatServer);
        return etatServerRepository.save(etatServer);
    }

    @Override
    public void deleteEtatServer (Long id) throws ResourceNotFoundException {
        EtatServer EtatServer = etatServerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.EtatServer n'existe pas"));
        etatServerRepository.delete(EtatServer);

    }

    @Override
    public void deleteAll() {
        etatServerRepository.deleteAll();
    }


}
