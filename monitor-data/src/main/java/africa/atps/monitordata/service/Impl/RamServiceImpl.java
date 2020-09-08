package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.Ram;
import africa.atps.monitordata.repositories.RamRepository;
import africa.atps.monitordata.service.IRamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RamServiceImpl implements IRamService {
    
    @Autowired
    private RamRepository ramRepository;

    @Override
    public Ram getRamById(Long id) throws ResourceNotFoundException {
        return ramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ce type de dto.Ram n'existe pas"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Ram saveRam(Ram ram) {
        return ramRepository.save(ram);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Ram updateRam(Ram Ram) throws ResourceNotFoundException {
        Ram tmp = ramRepository.findById(Ram.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.Ram n'existe pas"));

        return ramRepository.saveAndFlush(Ram);
    }

    @Override
    public void deleteRam (Long id) throws ResourceNotFoundException {
        Ram Ram = ramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cette dto.Ram n'existe pas"));
        ramRepository.delete(Ram);

    }

    @Override
    public void deleteAll() {
        ramRepository.deleteAll();
    }

}
