import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerSearchComponent } from './server-search.component';

describe('ServerSearchComponent', () => {
  let component: ServerSearchComponent;
  let fixture: ComponentFixture<ServerSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServerSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
