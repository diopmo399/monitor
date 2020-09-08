import {Menu} from '../app/shared/entities/Menu';

export const MockedMenu: Menu[] =
  [
  {
    libelle: 'serveur hotes',
    children: [
      {
      route: '/server/add',
      libelle: 'ajouter'
    },
      {
        route: '/server/lister',
        libelle: 'lister'
      }
    ]
  },
  {
    libelle: 'services',
    children: [
      {
      route: '/service/add',
      libelle: 'ajouter'
    },
      {
        route: '/service/lister',
        libelle: 'lister'
      }
    ]
  }
];
