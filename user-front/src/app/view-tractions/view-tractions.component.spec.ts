import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewTractionsComponent} from './view-tractions.component';

describe('ViewTractionsComponent', () => {
  let component: ViewTractionsComponent;
  let fixture: ComponentFixture<ViewTractionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ViewTractionsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewTractionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
