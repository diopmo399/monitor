package africa.atps.monitordata.service;

import africa.atps.monitordata.models.Composant;

import java.util.List;

public interface IComposantService {
    List<Composant> getAllComposant();
    Composant getComposantById(Long id);
    Composant saveComposant(Composant composant);
    Composant updateComposant(Composant composant);
    void deleteComposant(Long id);
    public void deleteAll();
}
