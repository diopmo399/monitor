package africa.atps.monitordata.controller;

import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.Ram;
import africa.atps.monitordata.service.IRamService;
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
@RequestMapping(value = "/ram")
public class RamController {

    private final IRamService iRamService;

    public RamController(IRamService iRamService) {
        this.iRamService = iRamService;
    }

    /**
     *
     * @param id du composant à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un dto.Composant par son id", response = Ram.class)
    @GetMapping("/{id}")
    public ResponseEntity<Ram> findComposantById(@PathVariable Long id) {
        return ResponseEntity.ok(iRamService.getRamById(id));
    }

    @ApiOperation(value = "Permet la creation d'un nouveau composant", response = Ram.class)
    @PostMapping
    public ResponseEntity<Ram> add(
            @RequestBody @Valid
                    Ram ram
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iRamService.saveRam(ram));
    }

    @ApiOperation(value = "Modifier l'état d'un composant dans le systeme", response = Ram.class)
    @PutMapping
    public ResponseEntity<Ram> update(
            @RequestBody @Valid
                    Ram ram
    ) throws CustomException {
        return ResponseEntity.ok(iRamService.updateRam(ram));
    }
}
