package africa.atps.monitordata.controllerTest;

import africa.atps.monitordata.MonitorDataApplication;
import africa.atps.monitordata.controller.NotificationController;
import dto.ENUM.TypeNotificationEnum;
import africa.atps.monitordata.models.Notification;
import africa.atps.monitordata.service.INotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
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
public class NotificationControllerTest {

    @Autowired
    private INotificationService iNotificationService;
    
    @Autowired
    @Qualifier(value = "mappingJackson2HttpMessageConverter")
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    private Notification notification;
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificationController resource = new NotificationController(iNotificationService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
        notification = new Notification();
        notification.setType(TypeNotificationEnum.APPEL);
        notification.setIsSend(false);
    }

    @Test
    public void testAll() throws Exception {
        iNotificationService.deleteAll();
        iNotificationService.saveNotification(notification);
        mockMvc.perform( MockMvcRequestBuilders
                .get("/notification/all")
                        .param("page", "0")
                        .param("size", "10")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[*].id").isNotEmpty());
    }




    @Test
    public void testAdd() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(notification);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/notification")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
               .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testUpdate() throws Exception {
        iNotificationService.deleteAll();
        iNotificationService.saveNotification(notification);
        notification.setIsSend(true);
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(notification);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/notification")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.isSend").value(notification.getIsSend()));
   }


}
