import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {IServerHote, ServerHote} from '../../shared/entities/ServerHote';
import {ServerHoteService} from '../../shared/services/server-hote.service';
import {ActivatedRoute} from '@angular/router';
import {AlertService} from '../../shared/services/alert.service';
import {HttpResponse} from '@angular/common/http';
import { without  } from 'lodash';
import * as moment from 'moment';
import {HttpMethodEnum} from '../../shared/entities/HttpMethodEnum';
import {IServiceServer, ServiceServer} from '../../shared/entities/ServiceServer';
import {jsonValidator} from '../../shared/utils/jsonValidator';
import {Contact, IContact} from '../../shared/entities/IContact';

@Component({
  selector: 'app-update-server',
  templateUrl: './update-server.component.html',
  styleUrls: ['./update-server.component.css']
})
export class UpdateServerComponent implements OnInit {
  httpmethods = Object.keys(HttpMethodEnum);
  serverHote: IServerHote;
  hide = true;
  loading = false;
  submitted = false;
  selected: HttpMethodEnum;
  serverForm: FormGroup;
  serviceForm: FormGroup;
  contactForm: FormGroup;
  constructor(private alertService: AlertService,
              private fb: FormBuilder,
              private serverHoteService: ServerHoteService,
              private activatedRoute: ActivatedRoute) { }
  ngOnInit() {
    this.serverForm = this.fb.group({
      id: [],
      nom: [null, [ Validators.required, Validators.minLength(3)]],
      adressIp: [null, [ Validators.required, Validators.minLength(7), Validators.maxLength(200)]],
      port: [],
      login: [],
      password: [],
      timeout: [null, [Validators.required, Validators.min(1)]],
    });
    this.serviceForm = this.fb.group({
      id: [null],
      nom: [null, [ Validators.required, Validators.minLength(3)]],
      content: [null, [jsonValidator]],
      method: [null],
      url: [null]
    });
    this.activatedRoute.data.subscribe(({serverHote}) => {
      this.serverHote = serverHote;
      this.updateForm(serverHote);
    });
    this.contactForm = this.fb.group({
      id: [null],
      nom: [null, [ Validators.required, Validators.minLength(3)]],
      prenom: [null, [ Validators.required, Validators.minLength(3)]],
      email: [null, [ Validators.email]],
      telephone: [null, [Validators.minLength(9)]],
      fonction: [null, [Validators.minLength(3)]]
    });
  }
   createServerFromForm(): IServerHote {
    return {
      ...new ServerHote(),
      id: this.serverForm.get(['id']).value,
      nom: this.serverForm.get(['nom']).value,
      adressIp: this.serverForm.get(['adressIp']).value,
      login: this.serverForm.get(['login']).value,
      password: this.serverForm.get(['password']).value,
      port: this.serverForm.get(['port']).value,
      services: this.serverHote.services,
      contacts: this.serverHote.contacts,
      etatServers: this.serverHote.etatServers,
      token: this.serverHote.token,
      composants: this.serverHote.composants,
      timeout: moment.duration(this.serverForm.get(['timeout']).value,
      )
    };
  }
   createServiceFromForm(): IServiceServer {
   const  iserverService = {
      ...new ServiceServer(),
      id: this.serviceForm.get(['id']).value,
      nomService: this.serviceForm.get(['nom']).value,
      method: this.serviceForm.get(['method']).value,
       content: JSON.parse(this.serviceForm.get(['content']).value),
      // content: this.serviceForm.get(['content']).value,
      url: this.serviceForm.get(['url']).value
    };
   this.serviceForm.reset();
   this.serviceForm.clearValidators();
   return iserverService;
  }
  private createContactForm(): IContact {
   const  icontact = {
      ...new Contact(),
      id: this.contactForm.get(['id']).value,
      nom: this.contactForm.get(['nom']).value,
      prenom: this.contactForm.get(['prenom']).value,
      email: this.contactForm.get(['email']).value,
      telephone: this.contactForm.get(['telephone']).value,
      fonction: this.contactForm.get(['fonction']).value
    };
   this.contactForm.reset();
   this.contactForm.clearValidators();
   return icontact;
  }
  private updateForm(serverHote: ServerHote) {
    if (serverHote.id == null) {
      return;
    }
    this.serverForm.patchValue({
      id: serverHote.id,
      nom: serverHote.nom,
      adressIp: serverHote.adressIp,
      login: serverHote.login,
      password: serverHote.password,
      timeout: moment.duration(serverHote.timeout, 'millisecond').asMilliseconds(),
      port: serverHote.port
    });
  }
  save() {
    this.loading = true;
    this.submitted = true;
    if (this.serverHote.id === null) {
      this.serverHoteService.create(this.serverHote)
        .subscribe((serverHoteHttpResponse: HttpResponse<IServerHote>) => {
          this.serverForm.reset();
          this.alertService.success( serverHoteHttpResponse.statusText + ' ' + serverHoteHttpResponse.body.nom);
        });
    } else {
      this.serverHoteService.update(this.serverHote)
        .subscribe((serverHoteHttpResponse: HttpResponse<IServerHote>) => {
          this.alertService.success( serverHoteHttpResponse.statusText + ' ' + serverHoteHttpResponse.body.nom);
          this.serverForm.reset();
        });
    }
    this.loading = false;
    this.submitted = false;
  }
  addServiceServer() {
    if (this.serviceForm.invalid) {
      return;
    }
    const serviceServer = this.createServiceFromForm();
    console.log(serviceServer);
    if (this.serverHote.services == null) {
      this.serverHote.services = [];
    }
    this.serverHote.services.unshift(serviceServer);
    console.log(this.serverHote.services);
  }
  addContact() {
    if (this.contactForm.invalid) {
      return;
    }
    const contact = this.createContactForm();
    console.log(contact);
    if (this.serverHote.contacts == null) {
      this.serverHote.contacts = [];
    }
    this.serverHote.contacts.unshift(contact);
    console.log(this.serverHote.contacts);
  }
  deleteContact(icontact: IContact) {
    this.serverHote.contacts = without(this.serverHote.contacts, icontact);
    console.log(this.serverHote.contacts);
  }
  deleteServerService(iserverService: IServiceServer) {
    this.serverHote.services = without(this.serverHote.services, iserverService);
    console.log(this.serverHote.services);
  }
  saveServer() {
    this.serverHote = this.createServerFromForm();
    console.log(this.serverHote);
  }
}
