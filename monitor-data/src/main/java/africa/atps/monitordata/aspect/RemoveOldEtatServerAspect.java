package africa.atps.monitordata.aspect;

import africa.atps.monitordata.repositories.EtatServerRepository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Aspect
public class RemoveOldEtatServerAspect {
    private final EtatServerRepository etatServerRepository;

    @Value(value = "${cleaning.periode}")
    private Long periode;

    public RemoveOldEtatServerAspect(EtatServerRepository etatServerRepository) {
        this.etatServerRepository = etatServerRepository;
    }

    @Pointcut("@annotation(RemoveOldResultat)")
    public void executeCleaning(){}

    @After(value = "executeCleaning()")
    private void deleteOldRecord() {
         // System.out.println("cleaning etat server");
        etatServerRepository.deleteEtatServerByDateAnalyseIsBefore(Date.from(Instant.now().minusSeconds(periode)));
    }
}
