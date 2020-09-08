package africa.atps.monitordata.models;

import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.Criticite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScenarioTests {

 private final Long id1 = 1L;
 private final Criticite frequence1 = Criticite.CRITIC;

 private final Long id2 = 2L;
 private final Criticite frequence2 = Criticite.DEFAULT;

 //List<Tache> taches1 = new ArrayList<>();
 //List<Tache> taches2 = new ArrayList<>();
 private final String nom1 = "server1";
 private final Contact contact = Contact
         .builder()
         .email("diopmo@ept.sn")
         .fonction("Chef Departement IT")
         .nom("DIOP")
         .Prenom("Mohamed")
         .build();

 ServerHote serverHote1;
 private final String nom2 = "server2";
 ServerHote serverHote2;
 List<ServiceServer> services1 = new ArrayList<>();
 List<ServiceServer> services2 = new ArrayList<>();

 List<Resultat> resultats1 = new ArrayList<>();
 List<Resultat> resultats2 = new ArrayList<>();

 Resultat resultat = Resultat.builder()
         .id(1L)
         .statusConnexion(ConnectiviteEnum.BONNE)
         .status("Forbiden")
         .build();






 @BeforeEach
 public  void init(){
  serverHote1 = ServerHote.builder().build();
  serverHote1.setNom(nom1);
  serverHote2 = ServerHote.builder().build();
  resultats1.add(resultat);
  resultats1.add(resultat);

  resultats2.add(resultat);
  resultats2.add(resultat);

 }

 @Test
 void crudTest(){
  Scenario scenario1 = Scenario.builder().build();
  scenario1.setId(id1);
  scenario1.setCriticite(Criticite.CRITIC);
 // scenario1.s(resultats1);
  //scenario1.setServices(services1);
  scenario1.setServerHote(serverHote1);
  //scenario1.setTaches(taches1);

  //Notification1.setPassword(password1);

  Scenario scenario2 = Scenario.builder().build();
  scenario2.setId(id2);
  scenario2.setCriticite(Criticite.DEFAULT);
 // scenario2.setResultats(resultats2);
  //scenario2.setServices(services2);
  scenario2.setServerHote(serverHote2);
  //scenario2.setTaches(taches2);
  //notification2.setPassword(password2);

  assertThat(scenario1.getId()).isEqualTo(id1);
  assertThat(scenario1.getCriticite()).isEqualTo(frequence1);
  //assertThat(scenario1.getResultats()).isEqualTo(resultats1);
  //assertThat(scenario1.getServices()).isEqualTo(services1);
  assertThat(scenario1.getServerHote()).isEqualTo(serverHote1);
  //assertThat(scenario1.getTaches()).isEqualTo(taches1);

  //assertThat(Notification1.getPassword()).isEqualTo(password1);

  assertThat(scenario2.getId()).isEqualTo(id2);
  assertThat(scenario2.getCriticite()).isEqualTo(frequence2);
//  assertThat(scenario2.getResultats()).isEqualTo(resultats2);
  //assertThat(scenario2.getServices()).isEqualTo(services2);
  assertThat(scenario2.getServerHote()).isEqualTo(serverHote2);
  //assertThat(scenario2.getTaches()).isEqualTo(taches2);
  //assertThat(Notification1.getPassword()).isNotEqualTo(notification2.getPassword());

 }
}
