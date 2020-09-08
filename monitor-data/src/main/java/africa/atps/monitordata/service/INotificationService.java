package africa.atps.monitordata.service;

import africa.atps.monitordata.DtoRecuperation.NotificationDto;
import africa.atps.monitordata.models.Notification;

import java.util.List;
import java.util.Optional;

public interface INotificationService {
    List<NotificationDto> getAllNotificationNotSend();
    Notification getNotificationById(Long id);
    Notification saveNotification(Notification notification);
    Notification updateNotification(Notification notification);
    void deleteNotification(Long id);
    public void deleteAll();
}
