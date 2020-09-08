package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.Processeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcesseurRepository extends JpaRepository <Processeur , Long> {
}
