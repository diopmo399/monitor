import { Component, OnInit } from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ScenarioService} from '../../shared/services/scenario.service';
import {IScenario} from '../../shared/entities/Scenario';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ModalComponent} from '../../shared/modal/modal.component';
import {AlertService} from '../../shared/services/alert.service';
import { without  } from 'lodash';


@Component({
  selector: 'app-scenario-list',
  templateUrl: './scenario-list.component.html',
  styleUrls: ['./scenario-list.component.css']
})
export class ScenarioListComponent implements OnInit {
  scenaris: IScenario[];

  constructor(private scenarioService: ScenarioService,
              private alertService: AlertService,
              private matDialog: MatDialog
) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.scenarioService.query()
      .subscribe((res: HttpResponse<IScenario[]>) => {
        this.scenaris = res.body;
      });
  }

  /**
   * cette fonction pertmet de supprimer un scenario avec confirmation
   *  scenario
   */
  openModalDelete(scenario: IScenario) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'modal-component';
    dialogConfig.data = {
      name: 'supprimer-scenario' ,
      title: 'Etes vous sur de vouloir supprimer ?',
      description: 'Veuillez confirmer la suppression du scenario associé au serveur hote ' + scenario.serverHote.nom,
      actionButtonText: 'supprimer'
    };
    const modalDialog = this.matDialog.open(ModalComponent, dialogConfig);
    modalDialog.componentInstance.output.subscribe(
      (deleted: boolean) => this.delete(scenario)
    );
  }

  /**
   * supprimer un scenario
   *  scenario
   */
  private delete(scenario: IScenario) {
    this.scenarioService.delete(scenario.id).subscribe(
      (response: HttpResponse<number>) => {
        this.alertService.success('scenario ' + response.body + 'est supprime');
        this.scenaris = without(this.scenaris, scenario);
      }
    );
  }
}
