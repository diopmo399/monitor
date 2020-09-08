package africa.atps.monitordata.controller;

import africa.atps.monitordata.DtoRecuperation.ServerHoteDto;
import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.ServerHote;
import africa.atps.monitordata.service.IServerHoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.APICodeMapping;
import utils.APIMessageMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * diopmo
 */

@Api("Permet la gestion des serveurs du systeme")

@ApiResponses(value = {
        @ApiResponse(code = APICodeMapping.SUCCESSFULL, message = APIMessageMapping.SUCCESSFULL)
        ,
        @ApiResponse(code = APICodeMapping.BAD_REQUEST, message = APIMessageMapping.BAD_REQUEST)
        ,
        @ApiResponse(code = APICodeMapping.NOT_AUTHORIZED, message = APIMessageMapping.NOT_AUTHORIZED)
        ,
        @ApiResponse(code = APICodeMapping.FORBIDDEN, message = APIMessageMapping.FORBIDDEN)
        ,
        @ApiResponse(code = APICodeMapping.NOT_FOUND, message = APIMessageMapping.NOT_FOUND)
        ,
        @ApiResponse(code = APICodeMapping.NOT_ALLOWED, message = APIMessageMapping.NOT_ALLOWED)
        ,
        @ApiResponse(code = APICodeMapping.INTERNAL, message = APIMessageMapping.INTERNAL)
}
)


@RefreshScope
@RestController
@Slf4j
@RequestMapping(value = "/server")
public class ServerHoteController {

    private final IServerHoteService iServerHoteService;


    public ServerHoteController(IServerHoteService iServerHoteService) {
        this.iServerHoteService = iServerHoteService;
    }

    /**
     * {@code GET  /servers} : Recupere tous les servers.
     *
     * @param page la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des serverHotes dans le body.
     */
    @ApiOperation(value = "Récupère l'ensemble des serveurs du système", response = ServerHoteDto.class)
    @GetMapping
    public ResponseEntity<List<ServerHoteDto>> all(Pageable page) {
        return ResponseEntity.ok(iServerHoteService.getAllServerHote(page).getContent());
    }

    @ApiOperation(value = "Récupère l'ensemble des serveurs du système dont le scenario est null", response = ServerHote.class)
    @GetMapping("/notscenario")
    public ResponseEntity<List<ServerHote>> allServerIsScenarioNull() {
        return ResponseEntity.ok(iServerHoteService.getAllServerHoteIsScenarioNull());
    }

    /**
     *
     * @param id du serveur à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un server par son id", response = ServerHote.class)
    @GetMapping("/{id}")
    public ResponseEntity<ServerHote> findServerById(@PathVariable Long id) {
        return ResponseEntity.ok(iServerHoteService.getServerHoteById(id));
    }

    /**
     *
     * @param id du serveur à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un server par connaissant son etatServer", response = ServerHote.class)
    @GetMapping("/analyse/{id}")
    public ResponseEntity<ServerHote> getServerHoteByEtat(@PathVariable Long id) {
        return ResponseEntity.ok(iServerHoteService.getServerHoteByEtatServer(id));
    }

    /**
     *
     * @param serverHote
     * @return
     * @throws CustomException
     */
    @ApiOperation(value = "Permet la creation d'un nouveau serveur", response = ServerHote.class)
    @PostMapping
    public ResponseEntity<ServerHote> add(
            @RequestBody @Valid
             ServerHote serverHote
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iServerHoteService.saveServerHote(serverHote));
    }

    @ApiOperation(value = "Modifier l'état d'un serveur dans le systeme", response = ServerHote.class)
    @PutMapping
    public ResponseEntity<ServerHote> update(
            @RequestBody @Valid
             ServerHote serverHote
    ) throws CustomException {
        return ResponseEntity.ok(iServerHoteService.updateServerHote(serverHote));
    }

    /**
     * Permet la suppression d' un serverHote
     * @param id
     * @return ResponseEntity<Long><>
     */
    @ApiOperation(value = "Permet la suppression d'un server par son id", response = ResponseEntity.class)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteServerHote(@PathVariable Long id){
        iServerHoteService.deleteServerHote(id);
        return ResponseEntity.ok(id);
    }
}
