import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadsCentralComponent } from './leads-central.component';

describe('LeadsCentralComponent', () => {
  let component: LeadsCentralComponent;
  let fixture: ComponentFixture<LeadsCentralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadsCentralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadsCentralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
