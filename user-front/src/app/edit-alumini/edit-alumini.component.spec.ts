import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EditAluminiComponent} from './edit-alumini.component';

describe('EditAluminiComponent', () => {
  let component: EditAluminiComponent;
  let fixture: ComponentFixture<EditAluminiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditAluminiComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditAluminiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
