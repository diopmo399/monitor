import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
/*
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
*/
import {MatCheckboxModule} from '@angular/material/checkbox';
// import {MatTableModule} from '@angular/material/table';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatBadgeModule} from '@angular/material/badge';
import {MatChipsModule} from '@angular/material/chips';
import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';
import {MatStepperModule} from '@angular/material/stepper';
import {MatSelectModule} from '@angular/material/select';
// icon
import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {ChartsModule} from 'ng2-charts';

@NgModule({
  exports: [
    FormsModule,
    CommonModule,
    // FormsModule,
    FontAwesomeModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
/*
    MatProgressSpinnerModule,
*/
    MatCheckboxModule,
 //   MatTableModule,
    MatGridListModule,
    ChartsModule,
    MatListModule,
    MatIconModule,
    MatDialogModule ,
    MatInputModule,
    MatBadgeModule,
    MatChipsModule,
    MatStepperModule,
    MatSelectModule,
    ReactiveFormsModule
  ]
})
export class SharedLibsModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas);
  }
}
