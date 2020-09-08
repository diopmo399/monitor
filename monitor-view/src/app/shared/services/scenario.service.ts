import { Injectable } from '@angular/core';
import {SERVER_API_URL} from '../../../app.constants';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {IScenario} from '../entities/Scenario';
import {Observable} from 'rxjs';
import {createRequestOption} from '../utils/request-util';
import {LoaderService} from "./loader.service";

type EntityResponseType = HttpResponse<IScenario>;
type EntityArrayResponseType = HttpResponse<IScenario[]>;
@Injectable({
  providedIn: 'root'
})
export class ScenarioService {
  private resourceUrl = SERVER_API_URL + '/data/scenario';
  constructor(protected http: HttpClient, private loaderService: LoaderService) {}
  create(scenario: IScenario): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .post<IScenario>(this.resourceUrl, scenario, {observe : 'response'});
  }
  update(scenario: IScenario): Observable<EntityResponseType> {
    this.loaderService.startLoading();
    return this.http
      .put<IScenario>(this.resourceUrl, scenario, {observe : 'response'});
  }
  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IScenario>(`${this.resourceUrl}/${id}`, {observe : 'response'});
  }
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScenario[]>(this.resourceUrl , {params : options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<any>> {
    this.loaderService.startLoading();
    return this.http
      .delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
