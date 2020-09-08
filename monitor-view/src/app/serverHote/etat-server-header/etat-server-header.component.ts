import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-etat-server-header',
  templateUrl: './etat-server-header.component.html',
  styleUrls: ['./etat-server-header.component.css']
})
export class EtatServerHeaderComponent implements OnInit {
  @Input() pourcentage: number;
  @Input() libelle: string;
  constructor() { }

  ngOnInit() {
  }

  getClasse(pourcentage: number) {
    if (pourcentage <=  10) {
      return 'excellent';
    }
    if (pourcentage <= 30 ) {
      return 'bonne';
    }
    if (pourcentage <= 50) {
      return 'moyenne';
    }
    if (pourcentage <= 70) {
      return 'faible';
    }
    if (pourcentage <= 90) {
      return 'mauvaise';
    }
    return 'no-connexion';
    }
  }

