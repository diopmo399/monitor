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
  production: true,
  // baseUrl: 'http://192.168.4.213:9002'
  // baseUrl: 'http://192.168.4.66:9002'
  baseUrl: 'http://localhost:9002',
  keycloak: keycloakConfig
};
