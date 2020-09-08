import { Pipe, PipeTransform } from '@angular/core';
import {CriticiteEnum} from '../entities/CriticiteEnum';

/**
 * rend les noms de criticites plus explicite
 */
@Pipe({
  name: 'formatcriticite'
})
export class CriticitePipe implements PipeTransform {

  transform(criticite: string): string {
    switch (criticite) {
      case CriticiteEnum.CRITIC:
        return 'critique';
      case CriticiteEnum.VERYCRITIC:
        return 'tres critique';
      case CriticiteEnum.DEFAULT:
        return 'criticité par defaut';
      case CriticiteEnum.HIGH:
        return 'critique haute';
      case CriticiteEnum.LOW:
        return 'criticité basse';
      case CriticiteEnum.MEDIUM:
        return 'criticité moyenne';
      case CriticiteEnum.VERYHIGH:
        return 'criticité tres haute';
      case CriticiteEnum.VERYLOW:
        return 'criticité tres basse';
      default:
          return 'inconnue';
    }
  }

}
