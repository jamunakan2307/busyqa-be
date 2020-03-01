import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SalesInternComponent} from './sales-intern.component';

describe('SalesInternComponent', () => {
  let component: SalesInternComponent;
  let fixture: ComponentFixture<SalesInternComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SalesInternComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInternComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
