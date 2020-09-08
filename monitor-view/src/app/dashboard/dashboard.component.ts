import { Component, OnInit } from '@angular/core';
import {IServerHote} from '../shared/entities/ServerHote';
import {ServerHoteService} from '../shared/services/server-hote.service';
import {HttpResponse} from '@angular/common/http';
import {LoaderService} from '../shared/services/loader.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  serverHotes: IServerHote[] ;

  constructor(private serverHoteService: ServerHoteService, private loaderService: LoaderService) {
    this.loadAll();
  }

  ngOnInit() {
  }
  /**
   * cette fonction permet de recuperer tous les serverHotes de facon periodique
   */
  loadAll() {
    this.loaderService.startLoading();
    this.serverHoteService.query().subscribe((res: HttpResponse<IServerHote[]>) => {
      this.loaderService.stopLoading();
      this.serverHotes = res.body;
    }
);
  }
}
