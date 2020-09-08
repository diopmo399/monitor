package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.Resultat;
import africa.atps.monitordata.models.Scenario;
import africa.atps.monitordata.repositories.ResultatRepository;
import africa.atps.monitordata.service.IResultatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ResultatServiceImpl implements IResultatService {

    private final ResultatRepository resultatRepository;
    @Value(value = "${cleaning.periode}")
    private Long periode;
    //private final ScenarioRepository scenarioRepository;

    public ResultatServiceImpl(ResultatRepository resultatRepository/*, ScenarioRepository scenarioRepository*/) {
        this.resultatRepository = resultatRepository;
       // this.scenarioRepository = scenarioRepository;
    }


    @Override
    public List<Resultat> getAllResultat() {
        return resultatRepository.findAll();
    }

    @Override
    public Resultat getResultatById(Long id) throws ResourceNotFoundException {
        return resultatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun Resultat avec l'id = " + id + "trouver"));
    }

  /*  @Override
    public List<Resultat> findByDateAndStatus(Date debut, Date fin, String status) {
        return resultatRepository.findResultatByDateCheckBetweenAndStatus(debut, fin , status);
    }*/

    //@RemoveOldEtatServer
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Resultat saveResultat(Resultat resultat , Long id) {
        Scenario scenario = Scenario.builder()
                .id(id)
                .build();
        resultat.setScenario(scenario);
        //resultatRepository.deleteResultatsByDateCheckIsBefore(Date.from(Instant.now().minusSeconds(60)));
        //resultatRepository.deleteResultatsByDateCheckIsBefore(Date.from(Instant.now().minusSeconds(periode)));
        return resultatRepository.save(resultat);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Resultat updateResultat(Resultat resultat) throws ResourceNotFoundException {
        Resultat resultattmp = resultatRepository.findById(resultat.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resultat non trouver"));
        return resultatRepository.save(resultat);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteResultat(Long id) throws ResourceNotFoundException {
        Resultat resultat = resultatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resultat non trouver"));
        resultatRepository.delete(resultat);
    }

    @Override
    public void deleteAll() {
        resultatRepository.deleteAll();
    }
}
