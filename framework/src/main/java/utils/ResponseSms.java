package utils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseSms {
    private String codeRetour;
    private String messageRetour;
}
