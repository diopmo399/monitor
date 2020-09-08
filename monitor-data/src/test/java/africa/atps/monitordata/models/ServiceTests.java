package africa.atps.monitordata.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTests {

    // TODO: 1/29/20 implement test Unit Service

    private final Long id1 = 1L;
    private final String nomService1 = "CASHOUT" ;

    private final Long id2 = 2L;
    private final String nomService2 = "CASHIN" ;

    private final Scenario scenario1 = Scenario.builder().build() ;
    private final Scenario scenario2 = Scenario.builder().build() ;

    private final ServerHote serverHote1 = ServerHote.builder().build();
    private final ServerHote serverHote2 = ServerHote.builder().build();

    @Test
    void crudTest(){

        ServiceServer service1 = new ServiceServer();
        service1.setId(id1);
        service1.setNomService(nomService1);
       // service1.setScenario(scenario1);
        service1.setServerHote(serverHote1);

        ServiceServer service2 = new ServiceServer();
        service2.setId(id2);
        service2.setNomService(nomService2);
       // service2.setScenario(scenario2);
        service2.setServerHote(serverHote2);

        assertThat(service1.getId()).isEqualTo(id1);
        assertThat(service1.getNomService()).isEqualTo(nomService1);
        //assertThat(service1.getScenario()).isEqualTo(scenario1);
        assertThat(service1.getServerHote()).isEqualTo(serverHote1);


        assertThat(service2.getId()).isEqualTo(id2);
        assertThat(service2.getNomService()).isEqualTo(nomService2);
       // assertThat(service2.getScenario()).isEqualTo(scenario2);
        assertThat(service2.getServerHote()).isEqualTo(serverHote2);

    }


}
