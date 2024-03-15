import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExcerciseInformationComponent } from './excercise.information.component';

describe('ExcerciseInformationComponent', () => {
  let component: ExcerciseInformationComponent;
  let fixture: ComponentFixture<ExcerciseInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExcerciseInformationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExcerciseInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
