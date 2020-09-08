import { Pipe, PipeTransform } from '@angular/core';

/**
 * transforme un entier en pourcentage
 * on prend 100 - les pourcentages d echecs
 */
@Pipe({
  name: 'pourcentage'
})
export class PourcentagePipe implements PipeTransform {

  transform(value: number): string {
    return String(100 - Math.round(value) )  ;
  }

}
