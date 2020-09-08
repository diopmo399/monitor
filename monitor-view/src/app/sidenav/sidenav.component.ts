import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {Menu} from '../shared/entities/Menu';
import {MockedMenu} from '../../assets/MockedMenu';
import {KeycloakService} from 'keycloak-angular';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ModalComponent} from '../shared/modal/modal.component';


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  sideMenus: Menu[];
  mobileQuery: MediaQueryList;
  public appDrawer: any;

  // @ts-ignore
  @ViewChild('appDrawer') appDrawer: ElementRef;


  // tslint:disable-next-line:variable-name
  private _mobileQueryListener: () => void;
  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
              private keycloakService: KeycloakService, public matDialog: MatDialog) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.sideMenus = MockedMenu;
  }
  public closeNav() {
    this.appDrawer.close();
  }

  public openNav() {
    this.appDrawer.open();
  }

  logout() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.id = 'modal-component';
    dialogConfig.data = {
      name: 'supprimer-serverHote' ,
      title: 'Etes vous sur de vouloir se deconnecter ?',
      description: 'Etes vous sur de vouloir se deconnecter' ,
      actionButtonText: 'deconnexion'
    };
    const modalDialog = this.matDialog.open(ModalComponent, dialogConfig);
    modalDialog.componentInstance.output.subscribe(
      (deleted: boolean) => this.keycloakService.logout()
    );
  }
}
