package africa.atps.monitornotification.services;

import dto.Notification;

public interface ISmsService {
    Boolean sendSMS(Notification notification);
}
