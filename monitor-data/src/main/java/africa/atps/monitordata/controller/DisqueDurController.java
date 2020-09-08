package africa.atps.monitordata.controller;

import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.DisqueDure;
import africa.atps.monitordata.service.IDisqueDureService;
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
@RequestMapping(value = "/disquedur")
public class DisqueDurController {

    private final IDisqueDureService iDisqueDureService;


    public DisqueDurController(IDisqueDureService iDisqueDureService) {
        this.iDisqueDureService = iDisqueDureService;
    }



    /**
     *
     * @param id du composant à rechercher
     * @return ResponseEntity
     */
    @ApiOperation(value = "Récupère les informations d'un dto.Composant par son id", response = DisqueDure.class)
    @GetMapping("/{id}")
    public ResponseEntity<DisqueDure> findComposantById(@PathVariable Long id) {
        return ResponseEntity.ok(iDisqueDureService.getDisqueDureById(id));
    }

   /* @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }*/

    @ApiOperation(value = "Permet la creation d'un nouveau composant", response = DisqueDure.class)
    @PostMapping
    public ResponseEntity<DisqueDure> add(
            @RequestBody @Valid
                    DisqueDure disqueDure
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iDisqueDureService.saveDisqueDure(disqueDure));
    }

    @ApiOperation(value = "Modifier l'état d'un composant dans le systeme", response = DisqueDure.class)
    @PutMapping
    public ResponseEntity<DisqueDure> update(
            @RequestBody @Valid
                    DisqueDure disqueDure
    ) throws CustomException {
        return ResponseEntity.ok(iDisqueDureService.updateDisqueDure(disqueDure));
    }
}
