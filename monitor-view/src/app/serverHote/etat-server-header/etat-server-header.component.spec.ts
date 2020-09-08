import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EtatServerHeaderComponent } from './etat-server-header.component';

describe('EtatServerHeaderComponent', () => {
  let component: EtatServerHeaderComponent;
  let fixture: ComponentFixture<EtatServerHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EtatServerHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EtatServerHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
