import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import {IServerHote} from '../entities/ServerHote';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from '../../../app.constants';
import {createRequestOption} from '../utils/request-util';
import {LoaderService} from './loader.service';

type EntityResponseType = HttpResponse<IServerHote>;
type EntityArrayResponseType = HttpResponse<IServerHote[]>;
@Injectable({
  providedIn: 'root'
})
export class ServerHoteService {
  private resourceUrl = SERVER_API_URL + '/data/server';
  constructor(protected http: HttpClient, private loaderService: LoaderService) {}
  create(serverHote: IServerHote): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .post<IServerHote>(this.resourceUrl, serverHote, {observe : 'response'});
  }
  update(serverHote: IServerHote): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .put<IServerHote>(this.resourceUrl, serverHote, {observe : 'response'});
  }
  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServerHote>(`${this.resourceUrl}/${id}`, {observe : 'response'});
  }
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServerHote[]>(this.resourceUrl , {params : options, observe: 'response'});
  }
  queryNotScenared(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServerHote[]>(this.resourceUrl + '/notscenario' , {params : options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<any>> {
    this.loaderService.startLoading();
    return this.http
      .delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

}
