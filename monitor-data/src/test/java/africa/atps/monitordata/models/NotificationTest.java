package africa.atps.monitordata.models;

import africa.atps.monitordata.utils.APIMessageMapping;
import com.sun.jersey.impl.ApiMessages;
import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.TypeNotificationEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class NotificationTest {
    // TODO: 1/29/20 implementer les tests de notification
    private final Boolean isSend1 = true;
    private final TypeNotificationEnum type1   = TypeNotificationEnum.APPEL;
    private Resultat resultat1;
    Validator validator;

    private final Boolean isSend2 = false;
    private final TypeNotificationEnum type2 = TypeNotificationEnum.SMS;

    private Resultat resultat2;

    @BeforeEach
      void init(){
        resultat1 = Resultat.builder()
                .id(1L)
                .statusConnexion(ConnectiviteEnum.BONNE)
                .dateCheck(new Date())
                .status(APIMessageMapping.SUCCESSFULL)
                .build();
        resultat2 = Resultat.builder()
                .id(2L)
                .statusConnexion(ConnectiviteEnum.MAUVAISE)
                .dateCheck(new Date())
                .status(APIMessageMapping.SUCCESSFULL)
                .build();
        validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    @Test
    void crudTest(){
        Notification notification1 = Notification.builder()
                .id(1L)
                .type(type1)
                .isSend(isSend1)
                .name("test")
                .description("test desc")
                .build();

        Notification notification2 = Notification.builder()
                .id(2L)
                .isSend(isSend2)
                .type(type2)
                .build();

        assertThat(notification1.getId()).isEqualTo(1L);
        assertThat(notification1.getIsSend()).isEqualTo(isSend1);
        assertThat(notification1.getType()).isEqualTo(type1);

        assertThat(notification2.getId()).isEqualTo(2L);
        assertThat(notification2.getIsSend()).isEqualTo(isSend2);
        assertThat(notification2.getType()).isEqualTo(type2);;

        Set<ConstraintViolation<Notification>> violations = validator.validate(notification1);
        assertThat(violations.size()).isEqualTo(0);

    }
}
