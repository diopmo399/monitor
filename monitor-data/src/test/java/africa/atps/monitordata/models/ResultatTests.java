package africa.atps.monitordata.models;

import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.TypeNotificationEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResultatTests {
    // TODO: 1/29/20 implementer les tests

  private final Long id1 = 1L;
  private final ConnectiviteEnum connectivite1 = ConnectiviteEnum.MAUVAISE;
  private final Date dateCheck1 = new Date();

  private final Long id2 = 1L;
  private final ConnectiviteEnum connectivite2 = ConnectiviteEnum.DOWN;
  private final Date dateCheck2 = new Date();

 List<Notification> notifications1 = new ArrayList<>();
 List<Notification> notifications2 = new ArrayList<>();

  Notification notification1;
  Notification notification2;
  Notification notification3;

 @BeforeEach
 public  void init(){
  notification1  = Notification.builder()
          .id(1L)
          .isSend(false)
          .type(TypeNotificationEnum.EMAIL)
          .build();
  notification2 = Notification.builder()
          .id(2L)
          .isSend(false)
          .type(TypeNotificationEnum.SMS)
          .build();
  notification2 = Notification.builder()
          .id(3L)
          .isSend(true)
          .type(TypeNotificationEnum.APPEL)
          .build();
  notifications1.add(notification1);
  notifications1.add(notification2);
  notifications2.add(notification2);
  notifications2.add(notification3);


 }

 @Test
 void crudTest(){
  Resultat resultat1 = new Resultat();
  resultat1.setId(id1);
  resultat1.setDateCheck(dateCheck1);
  resultat1.setStatusConnexion(connectivite1);

  //Notification1.setPassword(password1);

  Resultat resultat2 = new Resultat();
  resultat2.setId(id2);
  resultat2.setDateCheck(dateCheck2);
  resultat2.setStatusConnexion(connectivite2);
  //notification2.setPassword(password2);

  assertThat(resultat1.getId()).isEqualTo(id1);
  assertThat(resultat1.getDateCheck()).isEqualTo(dateCheck1);
  assertThat(resultat1.getStatusConnexion()).isEqualTo(connectivite1);
  //assertThat(Notification1.getPassword()).isEqualTo(password1);

  assertThat(resultat2.getId()).isEqualTo(id2);
  assertThat(resultat2.getDateCheck()).isEqualTo(dateCheck2);
  assertThat(resultat2.getStatusConnexion()).isEqualTo(connectivite2);
  //assertThat(Notification1.getPassword()).isNotEqualTo(notification2.getPassword());

 }


}
