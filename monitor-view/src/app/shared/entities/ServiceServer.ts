import {HttpMethodEnum} from './HttpMethodEnum';

export interface IServiceServer {
  content?: string;
  id?: number;
  method?: HttpMethodEnum;
  nomService?: any;
  url?: string;
}

export class ServiceServer {
  content: any;
  id: number;
  method: HttpMethodEnum;
  nomService: string;
  url: string;
}
