package utils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TemplateSms {
    private String telephone;
    private String text;
    private String operateur;
    private String sender;
}
