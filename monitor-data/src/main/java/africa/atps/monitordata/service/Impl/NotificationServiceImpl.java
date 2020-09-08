package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.DtoRecuperation.NotificationDto;
import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.mapper.NotificationMapper;
import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.Notification;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.repositories.EtatServerRepository;
import africa.atps.monitordata.repositories.NotificationRepository;
import africa.atps.monitordata.repositories.ServerHoteRepository;
import africa.atps.monitordata.service.INotificationService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ServerHoteRepository serverHoteRepository;
    @Autowired
    private EtatServerRepository etatServerRepository;

    private final NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);


    @Override
    public List<NotificationDto> getAllNotificationNotSend() {
        List<Notification> notifications = notificationRepository.findNotificationsByIsSendIsFalse();
       List<NotificationDto> notificationDtoList = notifications
                .stream()
                .map(notificationMapper::sourceToDestination)
                .collect(Collectors.toList());
        notificationDtoList.forEach(notificationDto -> {
                   ServerHote serverHote = serverHoteRepository.findServerHoteByEtatServersContaining(notificationDto.getEtatServer());
                   serverHote.setEtatServers(null);
                   notificationDto.setServerHote(serverHote);
        });
        return notificationDtoList;
    }

    @Override
    public Notification getNotificationById(Long id) throws ResourceNotFoundException {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ce type de notification n'existe pas"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Notification saveNotification(Notification notification) {
        EtatServer etatServerTmp = etatServerRepository.findById(notification.getEtatServer().getId())
                .orElseThrow(()-> new ResourceNotFoundException("erreur sur l'id de l'etat server"));
        ServerHote serverHote = serverHoteRepository.findServerHoteByEtatServersContaining(notification.getEtatServer());
        AtomicReference<Boolean> isNotifier = new AtomicReference<>(false);
        List<EtatServer> etatServers = serverHote.getEtatServers()
                .stream()
                .filter(etatServer -> {
                    return Duration.between(etatServer.getDateAnalyse().toInstant(), Instant.now()).compareTo(Duration.ofHours(1)) <= 0;
                })
                .peek(etatServer -> {
                      notificationRepository.findAllByEtatServer(etatServer).forEach(
                            notification1 -> {
                                if (notification.getType() == notification1.getType() && notification.getMotifTache() == notification1.getMotifTache())
                                    isNotifier.set(true);
                            }
                    );
                })
                .collect(Collectors.toList());

        System.out.println("THE END: " + isNotifier.get());
        if (!isNotifier.get())
            return notificationRepository.save(notification);
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Notification updateNotification(Notification notification) throws ResourceNotFoundException {
        Notification notif = notificationRepository.findById(notification.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cette notification n'existe pas"));
        Notification notification1 = notificationRepository.saveAndFlush(notification);
        notification1.getEtatServer().setServerHote(null);
        return notification1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteNotification(Long id) throws ResourceNotFoundException {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cette notification n'existe pas"));
        notificationRepository.delete(notification);

    }

    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
    }
}
