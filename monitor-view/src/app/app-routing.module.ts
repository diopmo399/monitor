import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {UpdateServerComponent} from './serverHote/update-server/update-server.component';
import {ScenarioResolve, ServerHoteResolve} from './resolver';
import {ScenarioComponent} from './scenario/scenario.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CanAuthenticationGuard} from './guards/authentification.guard';

const appRoutes: Routes = [
  {
    path: '', component: HomeComponent,
    canActivate: [CanAuthenticationGuard]
  }
  ,
  {
    path: 'dashboard', component: DashboardComponent
  },
  {
    path: 'server/:id/update', component: UpdateServerComponent,
    resolve: {
      serverHote: ServerHoteResolve
    }
  }
  ,
  {
    path: 'server/add', component: UpdateServerComponent,
    resolve: {
      serverHote: ServerHoteResolve
    }
  },
  {
    path: 'scenario', component: ScenarioComponent,
    resolve: {
      scenario: ScenarioResolve
    }
  },
  {
    path: 'scenario/:id/update', component: ScenarioComponent,
    resolve: {
      scenario: ScenarioResolve
    }
  }
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(appRoutes),
    CommonModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
