import { Component, OnInit } from '@angular/core';
import {TacheEnum} from '../../shared/entities/TacheEnum';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../../shared/services/alert.service';
import {ServerHoteService} from '../../shared/services/server-hote.service';
import {ActivatedRoute} from '@angular/router';
import {ScenarioService} from '../../shared/services/scenario.service';
import {IServerHote} from '../../shared/entities/ServerHote';
import {IScenario, Scenario} from '../../shared/entities/Scenario';
import {HttpResponse} from '@angular/common/http';
import {CriticiteEnum} from '../../shared/entities/CriticiteEnum';

@Component({
  selector: 'app-scenario-add',
  templateUrl: './scenario-add.component.html',
  styleUrls: ['./scenario-add.component.css']
})
export class ScenarioAddComponent implements OnInit {
  LisTaches = Object.keys(TacheEnum);
  criticites = Object.keys(CriticiteEnum);
  submitted = false;
  scenarioForm: FormGroup;
  serverHotes: IServerHote[]  ;
  addDisplay: boolean;
  scenario: IScenario;

  constructor(private alertService: AlertService,
              private fb: FormBuilder,
              private serverHoteService: ServerHoteService,
              private scenarioService: ScenarioService,
              private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.scenarioForm = this.fb.group({
      id: [null],
      serverHote: [null, [Validators.required]],
      criticite: [null, [Validators.required]],
      taches: [null]
    });
    this.activatedRoute.data.subscribe(
       ({ scenario }) => this.updateForm(scenario)
      // ({ scenario }) => console.log(scenario)
    );
  }

  /**
   * cette fonction permet de remplir le formulaire a partir du scenario provenant du route resolver
   *  scenario
   */
  private updateForm(scenario: Scenario) {
    if (scenario === undefined || scenario.serverHote == null ) {
      this.loadAll();
      return;
    }
    this.scenario = scenario;
    this.serverHotes = [];
    this.serverHotes.unshift(scenario.serverHote);
    this.scenarioForm.patchValue({
      id: scenario.id,
      serverHote: scenario.serverHote,
      criticite: scenario.criticite,
      taches: scenario.taches
    });
    this.toggleAddDisplay(false);
  }
  private createFromForm(): IScenario {
    return {
      ...new Scenario(),
      id: this.scenarioForm.get(['id']).value,
      serverHote: this.scenarioForm.get(['serverHote']).value,
      criticite: this.scenarioForm.get(['criticite']).value,
      taches: this.scenarioForm.get(['taches']).value
    };
  }
  loadAll() {
    if (this.scenario === undefined) {
      this.serverHoteService.queryNotScenared().subscribe(
        (res: HttpResponse<IServerHote[]>) => {
          this.serverHotes = res.body;
        }
      );
    }
  }
  save() {
    this.submitted = true;
    const scenario = this.createFromForm();
    if (scenario.id === undefined || scenario.id === null) {
      this.scenarioService.create(scenario)
        .subscribe((scenarioHttpResponse: HttpResponse<IScenario>) => {
          this.scenarioForm.reset();
          this.alertService.success( scenarioHttpResponse.statusText + ' ' + scenarioHttpResponse.body.id);
          this.toggleAddDisplay(null);
        });
    } else {
      this.scenarioService.update(scenario)
        .subscribe((scenarioHttpResponse: HttpResponse<IScenario>) => {
          this.alertService.success( scenarioHttpResponse.statusText + ' ' + scenarioHttpResponse.body.id);
          // this.scenarioForm.reset();
          this.toggleAddDisplay(null);
        });
    }
    this.submitted = false;
  }

  toggleAddDisplay(display: boolean) {
    if (display === null) {
      this.addDisplay = !this.addDisplay;
    } else {
      this.addDisplay = display;
    }
  }
}
