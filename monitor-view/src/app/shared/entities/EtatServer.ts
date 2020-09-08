import {Moment} from 'moment';

export class IEtatServer {
  checked?: boolean;
  dateAnalyse?: Moment;
  id?: number;
  tauxEchecHttp?: number;
  tauxEchecPing?: number;
  tauxEchecTelnet?: number;
  etatConnexion?: number;
}
export class EtatServer implements IEtatServer{
  checked: boolean;
  dateAnalyse: Moment;
  id: number;
  tauxEchecHttp: number;
  tauxEchecPing: number;
  tauxEchecTelnet: number;
  etatConnexion?: number;
}
