import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { UserService } from '../../../user.service';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DateAdapter, provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerControl, MatDatepickerPanel } from '@angular/material/datepicker';
import { environment } from '../../../../environments/environment';
import { FitnessProgramTypeService } from '../fitness-program-type.service';
import { FitnessProgramType } from '../fitness-program-type';
import { CompletedExercise } from './completed.exercise';
import { CompletedExerciseService } from './completed.exercise.service';

@Component({
  selector: 'app-diary',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule],
  templateUrl: './diary.component.html',
  styleUrl: './diary.component.css',
  providers: [UserService, FitnessProgramTypeService, CompletedExerciseService, provideNativeDateAdapter()]
})
export class DiaryComponent implements OnInit{
  dateControl = new FormControl();
  private _locale: string;
  controlGroup: FormGroup;
  apiUrl: string;
  fitnessProgramTypes: FitnessProgramType[] = [];
  completedExerciseForUser: CompletedExercise[] = [];


  constructor(private userService: UserService, private dateAdapter: DateAdapter<Date>, 
    private _fb: FormBuilder, private fitnessProgramTypeService: FitnessProgramTypeService,
    private completedExerciseService: CompletedExerciseService)
  {
    this._locale = "en-EN"
    this.dateAdapter.setLocale(this._locale);
    this.apiUrl = environment.apiBaseUrl;

    this.controlGroup = this._fb.group({
      completedCategory: [``, [Validators.required]],
      completedDuration: [``, [Validators.required]],
      completedIntensity: [``, [Validators.required, Validators.min(1), Validators.max(3)]],
      completedWeightLoss: [``,[Validators.required]],
      completedDescription: [``,[]],
      dateControl: [``,[]]
    });
  }

  ngOnInit(): void {
      this.fitnessProgramTypeService.getAllFitnessProgramTypes().subscribe(response => {
        this.fitnessProgramTypes = response;
      })
  }

  dateInputMethod(event: any)
  {
    if (this.dateControl.value) {
      const selectedDate = new Date(this.dateControl.value);
      const year = selectedDate.getFullYear();
      const month = (selectedDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
      const day = selectedDate.getDate().toString().padStart(2, '0');
      const formattedDate = `${year}-${month}-${day}`;
    }
  }

  formatDate(dateString: string): string
  {
    let formattedDate;
    if (dateString) {
      const selectedDate = new Date(dateString);
      const year = selectedDate.getFullYear();
      const month = (selectedDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
      const day = selectedDate.getDate().toString().padStart(2, '0');
      formattedDate = `${year}-${month}-${day}`;
    }
    else
    {
      formattedDate = '';
    }
    return formattedDate;
  }

  onClick(event: any): void
  {
    let completedExerciseToAdd : CompletedExercise = {} as CompletedExercise;

    completedExerciseToAdd.type = this.controlGroup.get("completedCategory")?.value;
    completedExerciseToAdd.duration = this.controlGroup.get("completedDuration")?.value;
    completedExerciseToAdd.intensity= this.controlGroup.get("completedIntensity")?.value;
    completedExerciseToAdd.weightLoss = this.controlGroup.get("completedWeightLoss")?.value;

    completedExerciseToAdd.dayOfCompletion = this.controlGroup.get("dateControl")?.value;
    if(this.dateControl.value)
      {
        completedExerciseToAdd.dayOfCompletion = this.formatDate(this.dateControl.value);
      }
    else
    {
      completedExerciseToAdd.dayOfCompletion = "";
    }

    completedExerciseToAdd.resultDescription = this.controlGroup.get("completedDescription")?.value;
    if(!completedExerciseToAdd.resultDescription)
      {
        completedExerciseToAdd.resultDescription = "";
      }

    this.completedExerciseService.addCompletedExercise(completedExerciseToAdd).subscribe(result => {
      this.completedExerciseForUser.push(result);
    });

  }
}
