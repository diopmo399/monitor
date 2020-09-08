package africa.atps.monitordata.controller;


import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.ServiceServer;
import africa.atps.monitordata.service.IServiceServer;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
@Slf4j
@RequestMapping(value = "/service")
public class ServiceServerController {


    private final IServiceServer iServiceServer;

    public ServiceServerController(IServiceServer iServiceServer) {
        this.iServiceServer = iServiceServer;
    }

    /**
     * {@code GET  /servers} : Recupere tous les services.
     *
     * @param page la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des services dans le body.
     */
    @ApiOperation(value = "Récupère l'ensemble des services du système", response = ServiceServer.class)
    @GetMapping
    public ResponseEntity<List<ServiceServer>> all(Pageable page) {
        return ResponseEntity.ok(iServiceServer.getAllService(page).getContent());
    }

    /**
     *
     * @param id du service à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un service par son id", response = ServiceServer.class)
    @GetMapping("/{id}")
    public ResponseEntity<ServiceServer> findServerById(@PathVariable Long id) {
        return ResponseEntity.ok(iServiceServer.getServiceById(id));
    }

    /**
     *
     * @param serviceServer pour la creation d'un nouveau service
     * @return ResponseENTITY
     * @throws CustomException personnalise l'exception
     */
    @ApiOperation(value = "Permet la creation d'un nouveau service", response = ServiceServer.class)
    @PostMapping
    public ResponseEntity<ServiceServer> add(
            @RequestBody @Valid
                    ServiceServer serviceServer
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceServer.saveService(serviceServer));
    }

    /**
     *
     * @param serviceServer pour la mise à jours d'un service
     * @return ResponseEntity
     */
    @ApiOperation(value = "Modifier l'état d'un service dans le systeme", response = ServiceServer.class)
    @PutMapping
    public ResponseEntity<ServiceServer> update(
            @RequestBody @Valid
                    ServiceServer serviceServer
    ) throws CustomException {
        return ResponseEntity.ok(iServiceServer.updateService(serviceServer));
    }
}
