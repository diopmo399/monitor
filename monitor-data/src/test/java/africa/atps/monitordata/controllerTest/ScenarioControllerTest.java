package africa.atps.monitordata.controllerTest;

import africa.atps.monitordata.MonitorDataApplication;
import africa.atps.monitordata.controller.ScenarioController;
import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.Contact;
import africa.atps.monitordata.models.Scenario;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.service.IScenarioService;
import africa.atps.monitordata.service.IServerHoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ENUM.Criticite;
import dto.ENUM.Tache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * @author diopmo
 */
@SpringBootTest(classes = MonitorDataApplication.class)
@Transactional
public class ScenarioControllerTest {
    //todo: verifier si les information des fils sont presents
    @MockBean
    private IScenarioService scenarioService;
    @MockBean
    private IServerHoteService iServerHoteService;
    
    @Autowired
    @Qualifier(value = "mappingJackson2HttpMessageConverter")
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    private Scenario scenario;
    private ServerHote serverHoteTest;
    private ServerHote serverHote;
    private Contact contact;
    List<Scenario> scenarios;
    List<ServerHote> serverHotes;
    private Pageable pageables;

    private MockMvc mockMvc;
    Pageable pageable = PageRequest.of(0, 5, Sort.by(
            Sort.Order.desc("id")));


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScenarioController resource = new ScenarioController(scenarioService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
        Set<Tache> taches = new HashSet<>();
        taches.add(Tache.HTTP);
        contact = Contact.builder()
                .Prenom("Mohamed")
                .nom("DIOP")
                .fonction("Chef Departement IT")
                .email("diopmo@ept.sn")
                .tel("776244761")
                .build();
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        serverHote = ServerHote.builder()
                .adressIp("192.168.4.63")
                .contacts(contacts)
                .nom("SERVER TEST")
                .login("DIOP")
                .port(1777)
                .build();
        serverHoteTest = serverHote;
        serverHoteTest.setId(2L);

        scenario = Scenario
                .builder()
                .criticite(Criticite.CRITIC)
                .taches(taches)
                .serverHote(serverHote)
                .build();
        ;
        scenarios.add(scenario);




    }

    @BeforeEach
    public void delete() throws CustomException {
        System.out.println("before each");
        scenarioService.deleteAll();
        serverHotes.add(serverHoteTest);
       // serverHoteTest = iServerHoteService.saveServerHote(serverHote);
       // System.out.println(serverHoteTest);

    }

    @Test
    public void testAll() throws Exception {
       // when(scenarioService.getAllScenario(pageable)).thenReturn(scenarios);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/scenario")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc")   // <-- no space after comma!
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[*].id").isNotEmpty());

    }



    @Test
    public void testFindById() throws Exception {
        Scenario test = scenarioService.saveScenario(scenario);
        mockMvc.perform(
        MockMvcRequestBuilders
                .get("/scenario/{id}", test.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(test.getId()));

    }

    @Test
    public void testAdd() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(scenario);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/scenario")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
               .andExpect(jsonPath("$.id").exists());



    }

    @Test
    public void testUpdate() throws Exception {
        scenarioService.saveScenario(scenario);
        scenario.setCriticite(Criticite.CRITIC);
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(scenario);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/scenario")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.frequence").value(scenario.getCriticite()));
   }


}
