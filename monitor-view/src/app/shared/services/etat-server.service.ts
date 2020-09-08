import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {IEtatServer} from '../entities/EtatServer';
import {SERVER_API_URL} from '../../../app.constants';
import {Observable} from 'rxjs';
import {createRequestOption} from '../utils/request-util';

type EntityArrayResponseType = HttpResponse<IEtatServer[]>;
@Injectable({
  providedIn: 'root'
})
export class EtatServerService {
  private resourceUrl = SERVER_API_URL + '/data/etatserver';

  constructor(protected http: HttpClient) { }

  query( id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtatServer[]>(`${this.resourceUrl}/${id}`, {params : options, observe: 'response'});
  }
}
