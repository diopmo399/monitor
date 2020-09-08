package africa.atps.monitordata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.http.HttpMethod;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

@Data
@Entity
public class ServiceServer implements Serializable {
    // TODO: 1/29/20
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomService;
    private String url;
    private HttpMethod method;
    private HashMap<String , Object> content;

    @ManyToOne
    @JsonIgnore // todo: push
    private ServerHote serverHote;
}
