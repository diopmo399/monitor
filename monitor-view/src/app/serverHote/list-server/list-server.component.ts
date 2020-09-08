import {Component, OnDestroy, OnInit} from '@angular/core';
import {ServerHoteService} from '../../shared/services/server-hote.service';
import {IServerHote} from '../../shared/entities/ServerHote';
import {HttpResponse} from '@angular/common/http';
import {AlertService} from '../../shared/services/alert.service';
import { without  } from 'lodash';
import {ModalComponent} from '../../shared/modal/modal.component';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';

@Component({
  selector: 'app-list-server',
  templateUrl: './list-server.component.html',
  styleUrls: ['./list-server.component.css']
})
export class ListServerComponent implements OnInit, OnDestroy {
  public serverHotes: IServerHote[] ;
  private openedId: number;
  public showedServer: IServerHote;
  private interval;

  constructor(private serverHoteService: ServerHoteService,
              private alertService: AlertService,
              public matDialog: MatDialog,
  ) {
    this.loadAll() ;
  }

  ngOnInit() {
    this.interval = setInterval(() =>  this.loadAll(), 5000);
  }

  /**
   * cette fonction permet de recuperer tous les serverHotes de facon periodique
   */
  loadAll() {
    this.serverHoteService.query().subscribe((res: HttpResponse<IServerHote[]>) => this.serverHotes = res.body  );
  }

  /**
   * cette fonction permet de supprimer un serverHote
   * serverHote
   */
  delete(serverHote: IServerHote ) {
    this.serverHoteService.delete(serverHote.id).subscribe(
      (response: HttpResponse<number>) => {
        this.alertService.success(  'server ' + response.body + ' est supprime');
        this.serverHotes = without(this.serverHotes, serverHote);
      }
    );
  }

  /**
   * on arrete le rafraichissement lors de la fermeture du component
   */
  ngOnDestroy() {
    clearInterval(this.interval);
  }

  /**
   * fonction permettant de supprimer un serverHote avec confirmation
   * serverHote
   */
  openModalDelete(serverHote: IServerHote) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'modal-component';
    dialogConfig.data = {
      name: 'supprimer-serverHote' ,
      title: 'Etes vous sur de vouloir supprimer ?',
      description: 'Veuillez confirmer la suppression du server ' + serverHote.nom,
      actionButtonText: 'supprimer'
    };
    const modalDialog = this.matDialog.open(ModalComponent, dialogConfig);
    modalDialog.componentInstance.output.subscribe(
      (deleted: boolean) => this.delete(serverHote)
    );
  }

  openGraph(serverHote: IServerHote) {
    this.showedServer = serverHote;
    this.openedId = serverHote.id;
  }
}
