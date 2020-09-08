package africa.atps.monitordata.service;

import africa.atps.monitordata.aspect.RemoveOldEtatServer;
import africa.atps.monitordata.models.EtatServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEtatServerService {
    Page<EtatServer> getAllEtatServer(Pageable pageable , Long id);
    EtatServer getEtatServerById(Long id);
    EtatServer saveEtatServer(EtatServer etatServer, Long id);
    EtatServer updateEtatServer(EtatServer etatServer);
    void deleteEtatServer(Long id);
    public void deleteAll();
}
