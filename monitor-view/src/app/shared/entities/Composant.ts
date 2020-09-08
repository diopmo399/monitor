import {ServerHote} from './ServerHote';

export interface IComposant {
  id?: number;
  nom?: string;
  serverHote?: ServerHote;
}

export class Composant implements IComposant{
  id: number;
  nom: string;
  serverHote: ServerHote;
  constructor(id: number, nom: string, serverHote: ServerHote) {
    this.id = id;
    this.nom = nom;
    this.serverHote = serverHote;
  }
}
