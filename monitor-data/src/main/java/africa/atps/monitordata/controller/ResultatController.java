package africa.atps.monitordata.controller;

import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.Resultat;
import africa.atps.monitordata.service.IResultatService;
import africa.atps.monitordata.service.IScenarioService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RefreshScope
@RestController
@Slf4j
@RequestMapping(value = "/resultat")
public class ResultatController  {

    private final IResultatService iResultatService;

    public ResultatController(IResultatService iResultatService) {
        this.iResultatService = iResultatService;
    }

    /**
     * {@code GET  /servers} : Recupere tous les scenario.
     *
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des resultat dans le body.
     */
    @ApiOperation(value = "Récupère l'ensemble des resultat du système", response = Resultat.class)
    @GetMapping
    public ResponseEntity<List<Resultat>> all() {
        return ResponseEntity.ok(iResultatService.getAllResultat());
    }

    /**
     *
     * @param id du resultat à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un resultat par son id", response = Resultat.class)
    @GetMapping("/{id}")
    public ResponseEntity<Resultat> findServerById(@PathVariable Long id) {
        return ResponseEntity.ok(iResultatService.getResultatById(id));
    }

   /* @ApiOperation(value = "Permet la creation d'un nouveau resultat", response = Resultat.class)
    @GetMapping("/test")
    public ResponseEntity<List<Resultat>> findByDateAndStatus() throws CustomException {
        Date debut = new Date();
        long fin = new Date().getTime() - 15000*1000;
        Date fing = new Date(fin);
        System.out.println("#################################");
        System.out.println(fing);
        System.out.println(fin);
        String status = "DOWN";
        return ResponseEntity.status(HttpStatus.CREATED).body(iResultatService.findByDateAndStatus(fing , debut , status ));
    }*/

    @ApiOperation(value = "Modifier l'état d'un resultat dans le systeme", response = Resultat.class)
    @PutMapping
    public ResponseEntity<Resultat> update(
            @RequestBody @Valid
                    Resultat resultat
    ) throws CustomException {
        return ResponseEntity.ok(iResultatService.updateResultat(resultat));
    }

    @ApiOperation(value = "Permet la creation d'un nouveau resultat", response = Resultat.class)
    @PostMapping("/{id}")
    public ResponseEntity<Resultat> add(
            @RequestBody @Valid
                    Resultat resultat, @PathVariable @Valid Long id
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iResultatService.saveResultat(resultat, id ));
    }
}
