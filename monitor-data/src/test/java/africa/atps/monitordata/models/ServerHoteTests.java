package africa.atps.monitordata.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ServerHoteTests {
    // TODO: 1/29/20 ecrir les tests crud de ServerHote

  private final Long id1 = 1L;
  private final String url1 = "test.com";
  private final String protocole1 = "HTTP";
  private final int port1 = 1556;
  private final String login1 = "diopmo";
  private final String password1 = "123456";
  private final String token1 = "1111111111111111";

  private final Long id2 = 1L;
  private final String url2 = "test.com";
  private final String protocole2 = "HTTP";
  private final int port2 = 1556;
  private final String login2 = "diopmo";
  private final String password2 = "123456";
  private final String token2 = "1111111111111111";

  List<ServiceServer> services1 = new ArrayList<>();
  List<Composant> composants1 = new ArrayList<>();
  List<Scenario> scenarios1 = new ArrayList<>();

 List<ServiceServer> services2 = new ArrayList<>();
 List<Composant> composants2 = new ArrayList<>();
 List<Scenario> scenarios2 = new ArrayList<>();

 @Test
 void crudTest(){

  ServerHote serverHote1 = ServerHote.builder().build();
  serverHote1.setId(id1);
  serverHote1.setAdressIp(url1);
  serverHote1.setPort(port1);
  serverHote1.setLogin(login1);
  serverHote1.setPassword(password1);
  serverHote1.setToken(token1);
 // serverHote1.setScenarios(scenarios1);
  serverHote1.setServices(services1);;
  serverHote1.setComposants(composants1);

  ServerHote serverHote2 = ServerHote.builder().build();
  serverHote2.setId(id2);
  serverHote2.setAdressIp(url2);
  serverHote2.setPort(port2);
  serverHote2.setLogin(login2);
  serverHote2.setPassword(password2);
  serverHote2.setToken(token2);
  serverHote2.setComposants(composants2);
 // serverHote2.setScenarios(scenarios2);
  serverHote2.setServices(services2);


  assertThat(serverHote1.getId()).isEqualTo(id1);
  assertThat(serverHote1.getAdressIp()).isEqualTo(url1);
  assertThat(serverHote1.getPort()).isEqualTo(port1);
  assertThat(serverHote1.getLogin()).isEqualTo(login1);
  assertThat(serverHote1.getPassword()).isEqualTo(password1);
  assertThat(serverHote1.getToken()).isEqualTo(token1);
  assertThat(serverHote1.getComposants()).isEqualTo(composants1);
//  assertThat(serverHote1.getScenarios()).isEqualTo(scenarios1);
  assertThat(serverHote1.getServices()).isEqualTo(services1);

  assertThat(serverHote2.getId()).isEqualTo(id2);
  assertThat(serverHote2.getAdressIp()).isEqualTo(url2);
  assertThat(serverHote2.getPort()).isEqualTo(port2);
  assertThat(serverHote2.getLogin()).isEqualTo(login2);
  assertThat(serverHote2.getPassword()).isEqualTo(password2);
  assertThat(serverHote2.getToken()).isEqualTo(token2);
  assertThat(serverHote2.getComposants()).isEqualTo(composants2);
 // assertThat(serverHote2.getScenarios()).isEqualTo(scenarios2);
  assertThat(serverHote2.getServices()).isEqualTo(services2);
  
 }

}
