package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.Scenario;
import africa.atps.monitordata.models.ServerHote;
import dto.ENUM.Criticite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    List<Scenario> findByCriticite(Criticite criticite);
    Scenario findByServerHote(ServerHote serverHote);
}
