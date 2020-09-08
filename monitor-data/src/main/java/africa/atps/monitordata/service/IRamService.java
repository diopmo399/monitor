package africa.atps.monitordata.service;

import africa.atps.monitordata.models.Ram;

import java.util.List;

public interface IRamService {

    Ram getRamById(Long id);
    Ram saveRam(Ram ram);
    Ram updateRam(Ram ram);
    void deleteRam(Long id);
    public void deleteAll();
}
