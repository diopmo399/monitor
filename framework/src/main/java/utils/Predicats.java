package utils;

import dto.ENUM.ConnectiviteEnum;
import dto.ENUM.Tache;
import dto.Resultat;
import dto.Scenario;

import java.util.function.Predicate;

public class Predicats {
    public static Predicate<Scenario> isPing            = scenario -> scenario.getTaches().contains(Tache.PING) ;
    public static Predicate<Scenario> isHTTP            = scenario -> scenario.getTaches().contains(Tache.HTTP);
    public static Predicate<Scenario> isTELNET          = scenario -> scenario.getTaches().contains(Tache.TELNET);
    public static Predicate<Scenario> serverHoteNotNull = scenario -> scenario.getServerHote() !=null;
    public static Predicate<Scenario> serviceNotNull    = scenario -> scenario.getServerHote().getServices() !=null;
    public static Predicate<Resultat> isPingResultat    = resultat -> resultat.getTache() == Tache.PING;
    public static Predicate<Resultat> isHTTPResultat    = resultat -> resultat.getTache() == Tache.HTTP;
    public static Predicate<Resultat> isTELNETResultat  = resultat -> resultat.getTache() == Tache.TELNET;
    public static Predicate<Resultat> isDown            = resultat -> resultat.getStatusConnexion() == ConnectiviteEnum.DOWN;

}
