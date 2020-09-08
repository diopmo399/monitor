import {TacheEnum} from './TacheEnum';
import {ConnextiviteEnum} from './ConnextiviteEnum';
import {Moment} from 'moment';
import {IScenario} from './Scenario';
import {INotification} from './INotification';

/**
 * Cette classe permet de africa.atps.monitordata.mapper les resultats
 */
export class IResultat {
  id?: number;
  statusConexion?: ConnextiviteEnum;
  dateCheck?: Moment;
  status?: string;
  tache?: TacheEnum;
  duree?: Moment;
  type?: TacheEnum;
  notification?: INotification;
  scenarion?: IScenario;
}
export class Resultat implements IResultat {
  id: number;
  statusConexion: ConnextiviteEnum;
  dateCheck: Moment;
  status: string;
  tache: TacheEnum;
  duree: Moment;
  type: TacheEnum;
  notification: INotification;
  scenarion: IScenario;
}
