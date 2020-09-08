import { KeycloakService } from 'keycloak-angular';
import {KEYCLOAKCONFIG} from '../../../app.constants';


export function initializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
          config: KEYCLOAKCONFIG,
          bearerPrefix: 'Bearer',
          initOptions: {
            onLoad: 'login-required',
            checkLoginIframe: false,
          },
          bearerExcludedUrls: ['/assets/*']
        });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
  };
}
