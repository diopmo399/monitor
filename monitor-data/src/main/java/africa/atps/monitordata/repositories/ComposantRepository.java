package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.Composant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComposantRepository extends JpaRepository<Composant , Long> {
}
