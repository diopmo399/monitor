import * as moment from 'moment';

export class IchartElement {
  date: string;
  etatService: number;
  etatConnexion: number;

  constructor(date: string, etatService: number, etatConnexion: number) {
    // this.date = date.format('h:mm:ss');
    // this.date = moment(date.toString())).moment.format('HH:mm:ss');
    this.date = moment(date).format('HH:mm:ss');
    this.etatService   = 100 - etatService;
    this.etatConnexion = 100 - etatConnexion;
  }
}
