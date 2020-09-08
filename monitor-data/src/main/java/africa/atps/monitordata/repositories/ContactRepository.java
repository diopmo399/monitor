package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact , Long> {
}
