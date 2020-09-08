import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SharedLibsModule} from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import {MenuSideComponent} from './menu-side/menu-side.component';
import {AppRoutingModule} from '../app-routing.module';
import { PourcentagePipe } from './pipe/pourcentage.pipe';
import { ModalComponent } from './modal/modal.component';
import { CriticitePipe } from './pipe/criticite.pipe';
import { LoaderComponent } from './loader/loader.component';

@NgModule({
  declarations: [AlertComponent, MenuSideComponent, PourcentagePipe,
    ModalComponent, CriticitePipe, LoaderComponent],
  exports: [
    AlertComponent,
    MenuSideComponent,
    PourcentagePipe,
    CriticitePipe,
    ModalComponent,
    LoaderComponent
  ],
  imports: [
    CommonModule,
    SharedLibsModule,
    AppRoutingModule
  ],
  entryComponents: [ModalComponent]

})
export class SharedModule { }
