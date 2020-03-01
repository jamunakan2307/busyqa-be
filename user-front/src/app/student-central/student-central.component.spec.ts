import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentCentralComponent } from './student-central.component';

describe('StudentCentralComponent', () => {
  let component: StudentCentralComponent;
  let fixture: ComponentFixture<StudentCentralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudentCentralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentCentralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
