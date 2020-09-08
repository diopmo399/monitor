import { Component, OnInit } from '@angular/core';
import { library } from '@fortawesome/fontawesome-svg-core';
import {faServer} from '@fortawesome/free-solid-svg-icons/faServer';

// @ts-ignore
library.add(faServer);
@Component({
  selector: 'app-menu-side',
  templateUrl: './menu-side.component.html',
  styleUrls: ['./menu-side.component.css']
})
export class MenuSideComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
