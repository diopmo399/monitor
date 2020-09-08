import {Component, Input, OnChanges, OnDestroy} from '@angular/core';
import {EtatServerService} from '../../shared/services/etat-server.service';
import {HttpResponse} from '@angular/common/http';
import {EtatServer, IEtatServer} from '../../shared/entities/EtatServer';
import {IchartElement} from '../../shared/entities/ichart.element';
import {ChartDataSets, ChartOptions, ChartType} from 'chart.js';
import {Label} from 'ng2-charts/lib/base-chart.directive';
import {IServerHote} from '../../shared/entities/ServerHote';

@Component({
  selector: 'app-detail-server',
  templateUrl: './detail-server.component.html',
  styleUrls: ['./detail-server.component.css']
})
export class DetailServerComponent implements  OnChanges, OnDestroy {
  @Input() serverHote: IServerHote;
  private interval;
  barChartOptions: ChartOptions;
  public barChartLabels: Label[] = [];
  barChartType: ChartType = 'line';
  barChartLegend = true;
  barChartPlugins = [];
  etat = true  ;

  barChartData: ChartDataSets[] = [
    { data: [],
      label: 'Connectivite',
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { data: [], label: 'Service' },
  ];
  constructor(private etatServerService: EtatServerService) {
    this.interval = setInterval(() =>  this.load(this.serverHote.id), 5000);
  }

  /**
   * detecte len cas de changement des donnees
   *  changes
   */
  ngOnChanges() {
    this.initGraph();
    this.load(this.serverHote.id);
  }
  /**
   * cette fonction permet de recuperer les etats du server
   */
  load(id: number) {
    this.etatServerService.query(id).subscribe(
      (res: HttpResponse<IEtatServer[]>) =>
         this.convertData(res.body.reverse())
    );
  }

  /**
   * cette fonction permet de mettre a jour les informations du graph
   * si un element est present dans le graph no need to add it a gain
   */
  updateChar(ichart: IchartElement) {
    if (this.barChartLabels.indexOf(ichart.date) > -1) {
      return;
    }
    if (this.barChartLabels.length > 19) {
      this.reinitGraph();
    }
    this.barChartLabels.push(ichart.date);
    this.barChartData[0].data.push(ichart.etatConnexion);
    this.barChartData[1].data.push(ichart.etatService);
    this.etat = (ichart.etatConnexion > 50 && ichart.etatService > 50);
  }

  /**
   * cette fonction permet de convertir les etatServer en donnee pour le graph
   *  etatServers
   */
  convertData(etatServers: IEtatServer[]) {
    etatServers.forEach(
      (etatServer: EtatServer) => {
        this.updateChar(new IchartElement(etatServer.dateAnalyse.toString(), etatServer.tauxEchecHttp, etatServer.etatConnexion));
      }
    );
  }

  /**
   * cette fonction permet d initialiser le graph a zero
   */
  initGraph() {
    this.barChartData.forEach((chardata: ChartDataSets) => chardata.data = []);
    this.barChartLabels = [];
    this.barChartOptions = {
      responsive: true,
        scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    },
      title: {
         // text: this.serverHote.nom.toString() ,
          position: 'top',
          display: true,
          fontFamily: 'Serif',
          fontSize: 15
      }
    };
  }

  reinitGraph() {
    this.barChartData.forEach((chardata: ChartDataSets) => chardata.data.shift());
    this.barChartLabels.shift();
  }
  /**
   * on arrete le rafraichissement lors de la fermeture du component
   */
  ngOnDestroy() {
    clearInterval(this.interval);
  }
}
