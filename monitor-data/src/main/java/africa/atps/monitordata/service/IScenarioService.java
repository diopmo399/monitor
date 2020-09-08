package africa.atps.monitordata.service;

import africa.atps.monitordata.models.Scenario;
import dto.ENUM.Criticite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.List;

public interface IScenarioService {

    Page<Scenario> getAllScenario(Pageable pageable);
    Scenario getScenarioById(Long id);
    Scenario saveScenario(Scenario scenario);
    List<Scenario> getScenarioByDateandStatus(Criticite criticite, Duration duree);
    Scenario updateScenario(Scenario scenario);
    List<Scenario> findByCriticite(Criticite criticite);
    void deleteScenario(Long id);
    public void deleteAll();
}
