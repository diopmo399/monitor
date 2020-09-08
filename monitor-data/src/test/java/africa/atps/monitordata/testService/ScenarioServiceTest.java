package africa.atps.monitordata.testService;

import africa.atps.monitordata.exceptions.BadRequestException;
import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.Resultat;
import africa.atps.monitordata.models.Scenario;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.service.Impl.ResultatServiceImpl;
import africa.atps.monitordata.service.Impl.ScenarioServiceImpl;
import africa.atps.monitordata.service.Impl.ServerHoteServiceImpl;
import dto.ENUM.Criticite;
import dto.ENUM.Tache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Duration;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ScenarioServiceTest {
    @Autowired
    private ScenarioServiceImpl scenarioService;
    @Autowired
    private ServerHoteServiceImpl serverHoteService;
    @Autowired
    private ResultatServiceImpl resultatService;


    private Scenario scenario;
    private ServerHote serverHote;
    private EtatServer etatServer;
    private List<Scenario> scenarios;
    Page<Scenario> scenarioPage;

    Pageable pageable = PageRequest.of(0, 5, Sort.by(
            Sort.Order.desc("id")));

    @BeforeEach
    public void setup() throws CustomException {
        Set<Tache> taches = new HashSet<>();
        taches.add(Tache.HTTP);
        taches.add(Tache.PING);

        scenario = Scenario.builder()
                .criticite(Criticite.CRITIC)
                .taches(taches)
                .build();
        etatServer = EtatServer.builder()
                .etatConnexion(95)
                .tauxEchecPing(35)
                .tauxEchecTelnet(42)
                .dateAnalyse(new Date())
                .build();
        List<EtatServer> etatServers = new ArrayList<>();
        etatServers.add(etatServer);
        serverHote = ServerHote.builder()
                .login("diopmo")
                .adressIp("127.0.0.1")
                .nom("serverTest")
                .port(1244)
                .etatServers(etatServers)
                .build();

        serverHote = serverHoteService.saveServerHote(serverHote);

        System.out.println("beforeEach");



    }


    @Test
    public void testAdd() throws CustomException {
        scenario.setServerHote(serverHote);
       Scenario scenariotest = scenarioService.saveScenario(scenario);
        assertThat(scenariotest.getCriticite()).isEqualByComparingTo(scenario.getCriticite());
        assertThat(scenariotest.getTaches()).isEqualTo(scenario.getTaches());
        assertThat(scenariotest.getServerHote().getPort()).isEqualTo(scenario.getServerHote().getPort());

    }

    @Test
    void testExceptionScenario(){
        Exception exception = assertThrows(BadRequestException.class, ()-> {
            scenarioService.saveScenario(scenario);
        });

        String exceptionMessage = "Ce flow n'est pas prise en compte";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(exceptionMessage));
    }



    @Test
    void testgetAll(){
        Page<Scenario> scenarioPage = scenarioService.getAllScenario(pageable);
        Assertions.assertTrue(!scenarioPage.isEmpty());
        System.out.println(scenarioPage.getTotalElements());
    }

    @Test
    void testGetScenarioByCriticite(){
        scenario.setServerHote(serverHote);
        scenario = scenarioService.saveScenario(scenario);
        List<Resultat> resultats = new ArrayList<>();
        Long date = new Date().getTime();
        resultats.add(Resultat.builder()
                .dateCheck(new Date(date - 1000))
                .build());
        resultats.add(Resultat.builder()
                .dateCheck(new Date(date - 2000))
                .build());
        resultats.add(Resultat.builder()
                .dateCheck(new Date(date - 3000))
                .build());
        resultats.add(Resultat.builder()
                .dateCheck(new Date(date - 4000))
                .build());
        resultats.forEach(resultat -> resultatService.saveResultat(resultat, scenario.getId()));
      //  List<Scenario> scenarios = scenarioService.getScenarioByCriticite(Duration.parse("PT5S"));
        assertTrue(scenarios.get(0).getResultats().isEmpty());
    }


}
