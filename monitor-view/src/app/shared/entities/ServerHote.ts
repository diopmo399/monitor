import {Composant, IComposant} from './Composant';
import {IScenario} from './Scenario';
import {Duration} from 'moment';
import {IServiceServer, ServiceServer} from './ServiceServer';
import {Contact, IContact} from './IContact';
import {EtatServer, IEtatServer} from './EtatServer';

/**
 * Cette classe permet de faire le mapping des serverHotes
 */
export interface IServerHote {
  nom?: string;
  id: number;
  adressIp?: string;
  port?: number;
  login?: string;
  password?: string;
  timeout?: Duration;
  token?: string;
  contacts?: IContact[];
  etatServers?: IEtatServer[];
  etatServer?: IEtatServer;
  composants?: IComposant;
  services?: IServiceServer[];
  scenario?: IScenario;
}

/**
 * Cette classe permet de faire l'implementation des serverHotes
 */
export class ServerHote implements IServerHote {
  nom: string;
  id: number;
  adressIp: string;
  port: number;
  login: string;
  password: string;
  timeout: Duration;
  token: string;
  composants: Composant;
  services: ServiceServer[];
  scenario: IScenario;
  contacts: Contact[];
  etatServers: EtatServer[];
  etatServer: IEtatServer;
}
