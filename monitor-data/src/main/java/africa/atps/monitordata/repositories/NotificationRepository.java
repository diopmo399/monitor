package africa.atps.monitordata.repositories;

import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findNotificationsByIsSendIsFalse();
    List<Notification> findAllByEtatServer(EtatServer etatServer);
}
