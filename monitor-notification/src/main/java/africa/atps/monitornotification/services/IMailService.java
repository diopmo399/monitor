package africa.atps.monitornotification.services;

import dto.Notification;

public interface IMailService {
    Boolean sendEmail( Notification notification);
}
