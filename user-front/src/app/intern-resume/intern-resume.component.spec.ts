import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {InternResumeComponent} from './intern-resume.component';

describe('InternResumeComponent', () => {
  let component: InternResumeComponent;
  let fixture: ComponentFixture<InternResumeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InternResumeComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InternResumeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
