package africa.atps.monitordata.controller;

import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.EtatServer;
import africa.atps.monitordata.service.IEtatServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.APICodeMapping;
import utils.APIMessageMapping;

import javax.validation.Valid;
import java.util.List;

//import org.springframework.messaging.simp.SimpMessagingTemplate;

@SuppressWarnings("ALL")
@Api("Permet la gestion des Etat du server")

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
@RequestMapping(value = "/etatserver")
public class EtatServerController {

    //    @Autowired
    private final IEtatServerService iEtatServerService;

    /*@Autowired
    private SimpMessagingTemplate template;*/


    public EtatServerController(IEtatServerService iEtatServerService) {
        this.iEtatServerService = iEtatServerService;
    }

    /**
     * {@code GET  /etatServer} : Recupere tous les etatServer.
     *
     * @param id du serveur hote
     * @param pageable la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des serverHotes dans le body.
     */
    @ApiOperation(value = "Récupère l'ensemble des etat serveurs du système connaissant l'id du serveurhote", response = EtatServer.class)
    @GetMapping("/{id}")
    public ResponseEntity<List<EtatServer>> all(@PathVariable Long id, @PageableDefault(size=20, page=0, sort={"dateAnalyse"}, direction= Sort.Direction.DESC) Pageable pageable ) {
        return ResponseEntity.ok(iEtatServerService.getAllEtatServer(pageable , id).getContent());
    }


    /**
     * @param etatServer , id du serveur serveurHote  //todo: A commenter
     * @return
     * @throws CustomException
     */
    @ApiOperation(value = "Permet la creation d'un nouveau etat du serveur", response = EtatServer.class)
    @PostMapping("/{id}")
    public ResponseEntity<EtatServer> add(
            @RequestBody @Valid
                    EtatServer etatServer,
            @PathVariable @Valid Long id
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iEtatServerService.saveEtatServer(etatServer , id));
    }

    @ApiOperation(value = "Modifier l'état d'un serveur dans le systeme", response = EtatServer.class)
    @PutMapping
    public ResponseEntity<EtatServer> update(
            @RequestBody @Valid
                    EtatServer etatServer
    ) throws CustomException {
        return ResponseEntity.ok(iEtatServerService.updateEtatServer(etatServer));
    }

    /**
     * Permet la suppression d' un serverHote
     *
     * @param id
     * @return ResponseEntity<Long><>
     */
    @ApiOperation(value = "Permet la suppression d'un Etat par son id", response = ResponseEntity.class)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteEtatServer(@PathVariable Long id) {
        iEtatServerService.deleteEtatServer(id);
        return ResponseEntity.ok(id);
    }
}
