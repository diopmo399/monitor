package africa.atps.monitordata.service;

import africa.atps.monitordata.models.ServiceServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServiceServer {

    Page<ServiceServer> getAllService(Pageable pageable);
    ServiceServer getServiceById(Long id);
    ServiceServer saveService(ServiceServer service);
    ServiceServer updateService(ServiceServer service);
    void deleteService(Long id);
    public void deleteAll();

}
