package africa.atps.monitordata.controller;


import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.Scenario;
import africa.atps.monitordata.service.IScenarioService;
import dto.ENUM.Criticite;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RefreshScope
@RestController
@Slf4j
@RequestMapping(value = "/scenario")
public class ScenarioController  {

    private final IScenarioService iScenarioService;

    public ScenarioController(IScenarioService iScenarioService) {
        this.iScenarioService = iScenarioService;
    }

    /**
     * {@code GET  /servers} : Recupere tous les scenario.
     *
     * @param page la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des scenario dans le body.
     */
    @ApiOperation(value = "Récupère l'ensemble des scenario du système", response = Scenario.class)
    @GetMapping
    public ResponseEntity<List<Scenario>> all(Pageable page) {
        return ResponseEntity.ok(iScenarioService.getAllScenario(page).getContent());
    }

    /**
     * {@code GET  /servers} : Recupere tous les scenario dans un intervale de 2 minute.
     *
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des scenario dans le body.
     */
    @ApiOperation(value = "Récupère l'ensemble des scenario du système dans un intervale fournit ", response = Scenario.class)
    @GetMapping("/analyse/{criticite}/{duree}")
    public ResponseEntity<List<Scenario>> allScenarioTest(@PathVariable Criticite criticite, @PathVariable Duration duree) {
        return ResponseEntity.ok(iScenarioService.getScenarioByDateandStatus(criticite, duree));
    }

    /**
     *
     * @param id du scenario à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un scenario par son id", response = Scenario.class)
    @GetMapping("/{id}")
    public ResponseEntity<Scenario> findServerById(@PathVariable Long id) {
        return ResponseEntity.ok(iScenarioService.getScenarioById(id));
    }

    /**
     *
     * @param criticite du scenario à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un scenario par son id", response = Scenario.class)
    @GetMapping("/criticite/{criticite}")
    public ResponseEntity<List<Scenario>> findServerByCriticite(@PathVariable Criticite criticite) {
        return ResponseEntity.ok(iScenarioService.findByCriticite(criticite));
    }

    @ApiOperation(value = "Permet la creation d'un nouveau scenario", response = Scenario.class)
    @PostMapping
    public ResponseEntity<Scenario> add(
            @RequestBody @Valid
                    Scenario scenario
    ) throws CustomException {
        System.out.println("##################################");
        System.out.println(scenario.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(iScenarioService.saveScenario(scenario));
    }

    @ApiOperation(value = "Modifier l'état d'un scenario dans le systeme", response = Scenario.class)
    @PutMapping
    public ResponseEntity<Scenario> update(
            @RequestBody @Valid
                    Scenario scenario
    ) throws CustomException {
        return ResponseEntity.ok(iScenarioService.updateScenario(scenario));
    }
}
