package africa.atps.monitordata.controllerTest;

import africa.atps.monitordata.MonitorDataApplication;
import africa.atps.monitordata.controller.ServiceServerController;
import africa.atps.monitordata.models.ServiceServer;
import africa.atps.monitordata.service.IServiceServer;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * @author diopmo
 */
@SpringBootTest(classes = MonitorDataApplication.class)
@Transactional
public class ServiceServerControllerTest {

    @Autowired
    private IServiceServer iServiceServer;
    
    @Autowired
    @Qualifier(value = "mappingJackson2HttpMessageConverter")
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    private ServiceServer serviceServer;
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceServerController resource = new ServiceServerController(iServiceServer);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
        serviceServer = new ServiceServer();
        serviceServer.setNomService("Proxicash");
        serviceServer.setUrl("192.168.4.213/admin/olv");
    }

    @Test
    public void testAll() throws Exception {
        iServiceServer.deleteAll();
        iServiceServer.saveService(serviceServer);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/service/all")
                        .param("page", "0")
                        .param("size", "10")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[*].id").isNotEmpty());
    }



    @Test
    public void testFindById() throws Exception {
        ServiceServer test = iServiceServer.saveService(serviceServer);
        mockMvc.perform(
        MockMvcRequestBuilders
                .get("/service/{id}", test.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(test.getId()));

    }

    @Test
    public void testAdd() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(serviceServer);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/service")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
               .andExpect(jsonPath("$.id").exists());



    }

    @Test
    public void testUpdate() throws Exception {
        iServiceServer.deleteAll();
        iServiceServer.saveService(serviceServer);
        serviceServer.setUrl("diopmo.com");
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(serviceServer);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/service")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.url").value(serviceServer.getUrl()));
   }


}
