package africa.atps.monitordata.controller;

import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.Processeur;
import africa.atps.monitordata.service.IProcesseurService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RefreshScope
@RestController
@Slf4j
@RequestMapping(value = "/processeur")
public class ProcesseurController {

    private final IProcesseurService iProcesseurService;

    public ProcesseurController(IProcesseurService iProcesseurService) {
        this.iProcesseurService = iProcesseurService;
    }

    /**
     *
     * @param id du composant à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un dto.Composant par son id", response = Processeur.class)
    @GetMapping("/{id}")
    public ResponseEntity<Processeur> findComposantById(@PathVariable Long id) {
        return ResponseEntity.ok(iProcesseurService.getProcesseurById(id));
    }

    @ApiOperation(value = "Permet la creation d'un nouveau composant", response = Processeur.class)
    @PostMapping
    public ResponseEntity<Processeur> add(
            @RequestBody @Valid
                    Processeur processeur
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iProcesseurService.saveProcesseur(processeur));
    }

    @ApiOperation(value = "Modifier l'état d'un composant dans le systeme", response = Processeur.class)
    @PutMapping
    public ResponseEntity<Processeur> update(
            @RequestBody @Valid
                    Processeur processeur
    ) throws CustomException {
        return ResponseEntity.ok(iProcesseurService.updateProcesseur(processeur));
    }
}
