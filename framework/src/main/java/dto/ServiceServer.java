package dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.HashMap;

@Data
@NoArgsConstructor
public class ServiceServer implements Serializable {

    private Long id;
    private String nomService;
    private String url;
    private HttpMethod method;
    private HashMap<String , String> content;

    private ServerHote serverHote;
}
