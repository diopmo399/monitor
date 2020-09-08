package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.Composant;
import africa.atps.monitordata.repositories.ComposantRepository;
import africa.atps.monitordata.service.IComposantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComposantServiceImpl implements IComposantService {

    @Autowired
    private ComposantRepository composantRepository;

    @Override
    public List<Composant> getAllComposant() {
        return composantRepository.findAll();
    }

    @Override
    public Composant getComposantById(Long id) throws ResourceNotFoundException {
        return composantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ce type de dto.Composant n'existe pas"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Composant saveComposant(Composant Composant) {
        return composantRepository.save(Composant);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Composant updateComposant(Composant composant) throws ResourceNotFoundException {
        Composant tmp = composantRepository.findById(composant.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.Composant n'existe pas"));

        return composantRepository.saveAndFlush(composant);
    }

    @Override
    public void deleteComposant(Long id) throws ResourceNotFoundException {
        Composant Composant = composantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.Composant n'existe pas"));
        composantRepository.delete(Composant);

    }

    @Override
    public void deleteAll() {
        composantRepository.deleteAll();
    }
}
