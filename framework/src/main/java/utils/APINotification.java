package utils;

import dto.ENUM.TypeNotificationEnum;

public class APINotification {
    public static final  TestNotification TESTMAIL = TestNotification.builder()
            .max(60)
            .min(30)
            .typeNotification(TypeNotificationEnum.EMAIL)
            .build();
    public static final TestNotification TESTSMS = TestNotification.builder()
            .max(80)
            .min(60)
            .typeNotification(TypeNotificationEnum.SMS)
            .build();
    public static final TestNotification TESTAPPEL = TestNotification.builder()
            .max(101)
            .min(80)
            .typeNotification(TypeNotificationEnum.APPEL)
            .build();
   // public static final String BASEURLAPISMS = "http://192.168.4.63/api/notification/sms";
    public static final String BASEURLAPISMS = "https://wstest.proximo.africa/api/notification/sms";

}
