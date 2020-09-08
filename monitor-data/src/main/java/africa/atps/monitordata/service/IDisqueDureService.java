package africa.atps.monitordata.service;

import africa.atps.monitordata.models.DisqueDure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDisqueDureService {
    DisqueDure getDisqueDureById(Long id);
    DisqueDure saveDisqueDure(DisqueDure cisqueDure);
    DisqueDure updateDisqueDure(DisqueDure cisqueDure);
    void deleteDisqueDure(Long id);
    public void deleteAll();
}
