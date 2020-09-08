import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable, of} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {ServerHoteService} from './shared/services/server-hote.service';
import {IServerHote, ServerHote} from './shared/entities/ServerHote';
import {Injectable} from '@angular/core';
import {IScenario, Scenario} from './shared/entities/Scenario';
import {ScenarioService} from './shared/services/scenario.service';

@Injectable({ providedIn: 'root' })
export class ServerHoteResolve implements Resolve<IServerHote> {
  constructor(private service: ServerHoteService) { }
  resolve(route: ActivatedRouteSnapshot): Observable<IServerHote> {
    const id = route.params.id;
    if (id) {
      return this.service.find(id)
        .pipe(map((serverHote: HttpResponse<ServerHote>) =>
          serverHote.body
        ));
    }
    return of(new ServerHote());
  }
}

@Injectable({ providedIn: 'root' })
export class ScenarioResolve implements Resolve<IScenario> {
  constructor(private service: ScenarioService) { }
  resolve(route: ActivatedRouteSnapshot): Observable<IScenario> {
    const id = route.params.id;
    if (id) {
      return this.service.find(id).pipe(map((scenario: HttpResponse<IScenario>) => scenario.body));
    }
    return of(new Scenario());
  }

}
