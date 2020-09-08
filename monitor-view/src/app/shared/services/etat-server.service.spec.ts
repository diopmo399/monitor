import { TestBed } from '@angular/core/testing';

import { EtatServerService } from './etat-server.service';

describe('EtatServerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EtatServerService = TestBed.get(EtatServerService);
    expect(service).toBeTruthy();
  });
});
