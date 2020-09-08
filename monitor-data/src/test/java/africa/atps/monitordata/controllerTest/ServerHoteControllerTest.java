package africa.atps.monitordata.controllerTest;

import africa.atps.monitordata.MonitorDataApplication;
import africa.atps.monitordata.controller.ServerHoteController;
import africa.atps.monitordata.models.Contact;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.service.IServerHoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * @author diopmo
 */
@SpringBootTest(classes = MonitorDataApplication.class)
@Transactional
public class ServerHoteControllerTest {

    @Autowired
    private IServerHoteService serverHoteService;
    
    @Autowired
    @Qualifier(value = "mappingJackson2HttpMessageConverter")
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    private Contact contact;
    private ServerHote serverHote;
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServerHoteController resource = new ServerHoteController(serverHoteService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
        contact = Contact.builder()
                .Prenom("Mohamed")
                .nom("DIOP")
                .fonction("Chef Departement IT")
                .email("diopmo@ept.sn")
                .tel("776244761")
                .build();
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        serverHote = ServerHote
                .builder()
                .port(12444)
                .contacts(contacts)
                .adressIp("127.0.0.1")
                .login("diopmo")
                .build();

    }

    @BeforeEach
    public void deleteAll(){
        serverHoteService.deleteAll();
    }

    @Test
    public void testAll() throws Exception {
        serverHoteService.saveServerHote(serverHote);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/server")
                        .param("page", "0")
                        .param("size", "10")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }



    @Test
    public void testFindById() throws Exception {
        ServerHote test = serverHoteService.saveServerHote(serverHote);
        mockMvc.perform(
        MockMvcRequestBuilders
                .get("/server/{id}", test.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(test.getId()));

    }

    @Test
    public void testAdd() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(serverHote);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/server")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());



    }


}
