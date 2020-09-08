package utils;

import dto.ENUM.TypeNotificationEnum;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestNotification {
    private int min;
    private int max;
    private TypeNotificationEnum typeNotification;
}
