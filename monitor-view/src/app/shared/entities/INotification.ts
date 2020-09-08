import {IResultat} from './IResultat';
import {TypeNotificationEnum} from './TypeNotificationEnum';

export class INotification {
  id?: number;
  isSend?: boolean;
  type?: TypeNotificationEnum;
  resultat?: IResultat;
}
export class Notification implements INotification {
  id: number;
  isSend: boolean;
  type: TypeNotificationEnum;
  resultat: IResultat;
}
