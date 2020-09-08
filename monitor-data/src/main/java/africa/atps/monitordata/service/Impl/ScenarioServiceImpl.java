package africa.atps.monitordata.service.Impl;

import africa.atps.monitordata.exceptions.BadRequestException;
import africa.atps.monitordata.exceptions.ResourceNotFoundException;
import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.models.Resultat;
import africa.atps.monitordata.models.Scenario;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.repositories.ScenarioRepository;
import africa.atps.monitordata.repositories.ServerHoteRepository;
import africa.atps.monitordata.service.IScenarioService;
import dto.ENUM.Criticite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScenarioServiceImpl implements IScenarioService {

    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private ServerHoteRepository serverHoteRepository;

    @Override
    public Page<Scenario> getAllScenario(Pageable pageable) {
        Page<Scenario> scenarios = scenarioRepository.findAll(pageable);
        for(Scenario scenario : scenarios) {
            scenario.setResultats(null);
/*
            scenario.getServerHote().setEtatServers(null);
*/
        }

        return scenarios;
    }

    @Override
    @Transactional
    public List<Scenario> getScenarioByDateandStatus(Criticite criticite, Duration duree) {

        List<Scenario> scenarios = scenarioRepository.findByCriticite(criticite);

        for (Scenario scenario : scenarios) {
            scenario.getServerHote().setEtatServers(null);
            List<Resultat> resultats = scenario.getResultats()
                    .stream()
                    .filter( resultat -> (
                            Duration.between( resultat.getDateCheck().toInstant(), Instant.now()).compareTo(duree.multipliedBy(5)) <=  0
                            )
                    )
                    .collect(Collectors.toList()
                    );
            scenario.setResultats(resultats);
            }
        return scenarios;
    }

    @Override
    public Scenario getScenarioById(Long id) throws ResourceNotFoundException {
        return scenarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun scenario trouver avec l'id = " + id));
    }

    @Override
    public List<Scenario> findByCriticite(Criticite criticite) throws ResourceNotFoundException {
        List<Scenario> scenarios = scenarioRepository.findByCriticite(criticite);
        scenarios.forEach(scenario -> {
            scenario.setResultats(null);
            scenario.getServerHote().setEtatServers(null);
        });
        return scenarios;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
        public Scenario saveScenario(Scenario scenario) throws BadRequestException {
        ServerHote serverHote = serverHoteRepository.getOne(scenario.getServerHote().getId());
        if (serverHote == null)
            throw new BadRequestException("Ce flow n'est pas prise en compte");

        return scenarioRepository.save(scenario);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Scenario updateScenario(Scenario scenario) {
         Scenario tmp = scenarioRepository.findById(scenario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Update imposible car scenario non trouver"));
        //return scenarioRepository.saveAndFlush(scenario);
        return scenarioRepository.save(scenario);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteScenario(Long id) {
        Scenario scenario = scenarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le scenario avec l'id = " + id + "n'existe pas"));
        scenarioRepository.delete(scenario);
    }

    @Override
    public void deleteAll() {

        scenarioRepository.deleteAll();

    }
}
