import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PayClientComponent} from './pay-client.component';

describe('PayClientComponent', () => {
  let component: PayClientComponent;
  let fixture: ComponentFixture<PayClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PayClientComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PayClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
