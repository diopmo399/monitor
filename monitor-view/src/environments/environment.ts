// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import { KeycloakConfig } from 'keycloak-angular';

const keycloakConfig: KeycloakConfig = {
  realm: 'atps',
  url: 'http://test-inter.proximo.africa:9007/auth',
  clientId: 'monitor',
  credentials: {
     secret: '015d6586-74d3-4f69-b3b9-037af74b4589'
  }
};
export const environment = {
  production: false,
  // baseUrl: 'http://192.168.4.66:9002'
  // baseUrl: 'http://192.168.43.156:9002',
  baseUrl: 'http://localhost:9002',
  keycloak: keycloakConfig
  };
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
