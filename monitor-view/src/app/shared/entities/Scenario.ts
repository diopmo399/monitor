import {IServerHote, ServerHote} from './ServerHote';
import {TacheEnum} from './TacheEnum';
import {IResultat, Resultat} from './IResultat';
import {CriticiteEnum} from './CriticiteEnum';

/**
 * Cette classe permet de africa.atps.monitordata.mapper les scenarios
 */
export interface IScenario {
  id?: number;
  criticite?: CriticiteEnum;
  serverHote?: IServerHote;
  taches?: TacheEnum[];
  resultat?: IResultat;
}

export class Scenario implements IScenario {
  id: number;
  criticite: CriticiteEnum;
  serverHote: ServerHote;
  taches: TacheEnum[];
  resultat: Resultat;
}
