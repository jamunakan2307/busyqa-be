import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AluminiListComponent} from './alumini-list.component';

describe('AluminiListComponent', () => {
  let component: AluminiListComponent;
  let fixture: ComponentFixture<AluminiListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AluminiListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AluminiListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
