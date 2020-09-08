package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.Processeur;
import africa.atps.monitordata.repositories.ProcesseurRepository;
import africa.atps.monitordata.service.IProcesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrecesserServiceImpl implements IProcesseurService {
    
    @Autowired
    private ProcesseurRepository processeurRepository;

    @Override
    public Processeur getProcesseurById(Long id) throws ResourceNotFoundException {
        return processeurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ce type de dto.Processeur n'existe pas"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Processeur saveProcesseur(Processeur processeur) {
        return processeurRepository.save(processeur);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Processeur updateProcesseur(Processeur Processeur) throws ResourceNotFoundException {
        Processeur tmp = processeurRepository.findById(Processeur.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.Processeur n'existe pas"));

        return processeurRepository.saveAndFlush(Processeur);
    }

    @Override
    public void deleteProcesseur (Long id) throws ResourceNotFoundException {
        Processeur Processeur = processeurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.Processeur n'existe pas"));
        processeurRepository.delete(Processeur);

    }

    @Override
    public void deleteAll() {
        processeurRepository.deleteAll();
    }

}
