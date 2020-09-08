package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.ServiceServer;
import africa.atps.monitordata.repositories.ServiceRepository;
import africa.atps.monitordata.service.IServiceServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ServiceServerImpl implements IServiceServer {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Page<ServiceServer> getAllService(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }

    @Override
    public ServiceServer getServiceById(Long id) throws ResourceNotFoundException {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("impossible de trouver le service avec l'id = " + id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceServer saveService(ServiceServer service) {
        return serviceRepository.save(service);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceServer updateService(ServiceServer service) throws ResourceNotFoundException {
        ServiceServer serviceTmp = serviceRepository.findById(service.getId())
                .orElseThrow(() -> new ResourceNotFoundException("impossible de mettre à jours le service car Service introuvable"));
        //return serviceRepository.saveAndFlush(service);
        return serviceRepository.save(service);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteService(Long id) throws ResourceNotFoundException {
        serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("impossible de supprimer car service inexistante"));

    }

    @Override
    public void deleteAll() {

        serviceRepository.deleteAll();

    }
}
