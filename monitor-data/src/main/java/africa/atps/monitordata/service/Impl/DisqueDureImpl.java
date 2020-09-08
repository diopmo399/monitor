package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.DisqueDure;
import africa.atps.monitordata.repositories.DisqueDureRepository;
import africa.atps.monitordata.service.IDisqueDureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisqueDureImpl implements IDisqueDureService {

    @Autowired
    private DisqueDureRepository disqueDureRepository;



    @Override
    public DisqueDure getDisqueDureById(Long id) throws ResourceNotFoundException {
        return disqueDureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ce type de dto.DisqueDure n'existe pas"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public DisqueDure saveDisqueDure(DisqueDure disqueDure) {
        return disqueDureRepository.save(disqueDure);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public DisqueDure updateDisqueDure(DisqueDure DisqueDure) throws ResourceNotFoundException {
        DisqueDure tmp = disqueDureRepository.findById(DisqueDure.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.DisqueDure n'existe pas"));

        return disqueDureRepository.saveAndFlush(DisqueDure);
    }

    @Override
    public void deleteDisqueDure (Long id) throws ResourceNotFoundException {
        DisqueDure DisqueDure = disqueDureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.DisqueDure n'existe pas"));
        disqueDureRepository.delete(DisqueDure);

    }

    @Override
    public void deleteAll() {
        disqueDureRepository.deleteAll();
    }
    
}
