package africa.atps.monitordata.service;

import africa.atps.monitordata.aspect.RemoveOldResultat;
import africa.atps.monitordata.models.Resultat;
import dto.ENUM.ConnectiviteEnum;

import java.util.Date;
import java.util.List;

public interface IResultatService {

    List<Resultat> getAllResultat();
    Resultat getResultatById(Long id);
    Resultat saveResultat(Resultat resultat, Long id);
    Resultat updateResultat(Resultat resultat);
   // List<Resultat> findByDateAndStatus(Date debut, Date fin , String connectiviteEnum);
    void deleteResultat(Long id);
    public void deleteAll();
}
