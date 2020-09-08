export class IContact {
  id?: number;
  email?: string;
  fonction?: string;
  nom?: string;
  prenom?: string;
  tel?: string;
}

export class Contact implements IContact {
  id: number;
  email: string;
  fonction: string;
  nom: string;
  prenom: string;
  tel: string;
}
