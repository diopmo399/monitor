package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.Scenario;
import africa.atps.monitordata.models.ServerHote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerHoteRepository extends JpaRepository<ServerHote, Long> {


    /*@Query(value = "SELECT s FROM ServerHote s")
    List<ServerHote> findAllServerWithoutEtat();*/
    ServerHote findServerHoteByEtatServersContaining(EtatServer etatServer);

    @Query(value = "SELECT distinct  u FROM ServerHote u" ,
    countQuery = "SELECT count (distinct u) FROM ServerHote u")
    Page<ServerHote> findAllServer(Pageable pageable);

    List<ServerHote> findAllByScenarioIsNull();
    ServerHote findByScenario(Scenario scenario);
}
