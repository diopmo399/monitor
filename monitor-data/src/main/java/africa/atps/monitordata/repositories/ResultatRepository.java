package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {
    void deleteResultatsByDateCheckIsBefore(Date date);
}
