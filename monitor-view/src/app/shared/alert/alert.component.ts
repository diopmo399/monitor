import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subject, Subscription} from 'rxjs';
import {NavigationStart, Router} from '@angular/router';
import {AlertService} from '../services/alert.service';
import {LoaderService} from '../services/loader.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html'
})
export class AlertComponent implements OnInit, OnDestroy  {
  private subscription: Subscription;
  message: any;

  constructor(private alertService: AlertService, private loaderService: LoaderService) { }

  ngOnInit() {
    this.subscription = this.alertService.getAlert()
      .subscribe(
        message => {
          switch (message && message.type) {
            case 'success':
            message.cssClass = 'alert alert-success';
            this.loaderService.stopLoading();
            break;
            case 'error':
            message.cssClass = 'alert alert-danger';
            break;
        }
          this.message = message;
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}

