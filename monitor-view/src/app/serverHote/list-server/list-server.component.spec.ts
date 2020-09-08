import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListServerComponent } from './list-server.component';

describe('ListServerComponent', () => {
  let component: ListServerComponent;
  let fixture: ComponentFixture<ListServerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListServerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
