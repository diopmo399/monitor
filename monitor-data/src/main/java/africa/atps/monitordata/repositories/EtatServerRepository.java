package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.ServerHote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EtatServerRepository extends JpaRepository<EtatServer , Long> {
    List<EtatServer> findAllByServerHote(ServerHote serverHote);
    @Query(value = "SELECT s FROM EtatServer s where s.serverHote =: server ORDER BY s.id DESC")
    EtatServer findLastEtatByServerHote(@Param("serverHote") ServerHote serverHote);

    EtatServer findTopByServerHoteOrderByDateAnalyseDesc(ServerHote serverHote);

    Page<EtatServer> findAllByServerHoteId(Pageable pageable , Long serverId);

    void deleteEtatServerByDateAnalyseIsBefore(Date date);
}
