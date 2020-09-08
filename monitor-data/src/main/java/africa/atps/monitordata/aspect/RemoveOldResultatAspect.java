package africa.atps.monitordata.aspect;

import africa.atps.monitordata.repositories.ResultatRepository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Aspect
public class RemoveOldResultatAspect {
    private final ResultatRepository resultatRepository;

    @Value(value = "${cleaning.periode}")
    private Long periode;

    public RemoveOldResultatAspect(ResultatRepository resultatRepository) {
        this.resultatRepository = resultatRepository;
    }

    @Pointcut("@annotation(RemoveOldResultat)")
    public void executeCleaning(){}

    @After(value = "executeCleaning()")
    private void deleteOldRecord() {
        // System.out.println("cleaning resultat");
        resultatRepository.deleteResultatsByDateCheckIsBefore(Date.from(Instant.now().minusSeconds(periode)));
    }
}
