package africa.atps.monitordata.controller;

import africa.atps.monitordata.DtoRecuperation.NotificationDto;
import africa.atps.monitordata.exceptions.CustomException;
import africa.atps.monitordata.models.Notification;
import africa.atps.monitordata.service.INotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RefreshScope
@RestController
@Slf4j
@RequestMapping(value = "/notification")
public class NotificationController {
    private final INotificationService iNotificationService;

    public NotificationController(INotificationService iNotificationService) {
        this.iNotificationService = iNotificationService;
    }

    /**
     * {@code GET  /servers} : Recupere tous les notification non envoyer.
     *.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des notification dans le body.
     */
    @ApiOperation(value = "Récupère l'ensemble des notification non envoyer du système", response = Notification.class)
    @GetMapping
    public ResponseEntity<List<NotificationDto>> allNotSend() {
        return ResponseEntity.ok(iNotificationService.getAllNotificationNotSend());
    }

    @ApiOperation(value = "Permet la creation d'un nouveau notification", response = Notification.class)
    @PostMapping
    public ResponseEntity<Notification> add(
            @RequestBody @Valid
                    Notification notification
    ) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(iNotificationService.saveNotification(notification));
    }

    @ApiOperation(value = "Modifier l'état d'une notification dans le systeme", response = Notification.class)
    @PutMapping
    public ResponseEntity<Notification> update(
            @RequestBody @Valid
                    Notification notification
    ) throws CustomException {
        return ResponseEntity.ok(iNotificationService.updateNotification(notification));
    }

    @ApiOperation(value = "Modifier l'état d'une notification dans le systeme", response = Notification.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<@Valid Long> delete(
            @PathVariable @Valid

                    Long id
    ) throws CustomException {
        iNotificationService.deleteNotification(id);
        return ResponseEntity.ok(id);

    }

}
