import { TestBed } from '@angular/core/testing';

import { ServerHoteService } from './server-hote.service';

describe('ServerHoteService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ServerHoteService = TestBed.get(ServerHoteService);
    expect(service).toBeTruthy();
  });
});
