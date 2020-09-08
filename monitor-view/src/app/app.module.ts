import { BrowserModule } from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import { AppComponent } from './app.component';
import { ListServerComponent } from './serverHote/list-server/list-server.component';
import { UpdateServerComponent } from './serverHote/update-server/update-server.component';
import { ServerSearchComponent } from './serverHote/server-search/server-search.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SharedModule} from './shared/shared.module';
import {ErrorHandlerInterceptor} from './interceptor/error.interceptor';
import {SharedLibsModule} from './shared/shared-libs.module';
import { SidenavComponent } from './sidenav/sidenav.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { ScenarioComponent } from './scenario/scenario.component';
import { ScenarioAddComponent } from './scenario/scenario-add/scenario-add.component';
import { DetailServerComponent } from './serverHote/detail-server/detail-server.component';
import { EtatServerHeaderComponent } from './serverHote/etat-server-header/etat-server-header.component';
import { ScenarioListComponent } from './scenario/scenario-list/scenario-list.component';
import { ScenarioDetailComponent } from './scenario/scenario-detail/scenario-detail.component';
import {MatRippleModule} from '@angular/material/core';
import { DashboardComponent } from './dashboard/dashboard.component';
import {KeycloakAngularModule, KeycloakBearerInterceptor, KeycloakService} from 'keycloak-angular';
import {initializer} from './shared/utils/initKeycloak';

@NgModule({
  declarations: [
    AppComponent,
    ListServerComponent,
    UpdateServerComponent,
    ServerSearchComponent,
    SidenavComponent,
    HomeComponent,
    ScenarioComponent,
    ScenarioAddComponent,
    DetailServerComponent,
    EtatServerHeaderComponent,
    ScenarioListComponent,
    ScenarioDetailComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    SharedModule,
    SharedLibsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatRippleModule,
    KeycloakAngularModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    }
    ],
  bootstrap: [AppComponent]
})
export class AppModule {}
