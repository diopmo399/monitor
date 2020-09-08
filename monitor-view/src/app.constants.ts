// These constants are injected via webpack environment variables.
// You can add more variables in webpack.common.js or in profile specific webpack.<dev|prod>.js files.
// If you change the values in the webpack africa.atps.monitorbusiness.config files, you need to re run webpack to update the application
import {environment} from './environments/environment';
export const DATE_TIME_FORMAT = 'YYYY-MM-DDTHH:mm:ssZ';
export const MINUTE_TIME_FORMAT = 'mm:ssZ';

// export const VERSION = process.env.VERSION;
// export const DEBUG_INFO_ENABLED = !!process.env.DEBUG_INFO_ENABLED;
export const SERVER_API_URL = environment.baseUrl;
export const KEYCLOAKCONFIG = environment.keycloak;
// export const BUILD_TIMESTAMP = process.env.BUILD_TIMESTAMP;
