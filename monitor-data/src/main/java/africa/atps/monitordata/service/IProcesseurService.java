package africa.atps.monitordata.service;

import africa.atps.monitordata.models.Processeur;

import java.util.List;

public interface IProcesseurService {
    Processeur getProcesseurById(Long id);
    Processeur saveProcesseur(Processeur processeur);
    Processeur updateProcesseur(Processeur processeur);
    void deleteProcesseur(Long id);
    public void deleteAll();
}
