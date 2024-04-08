import { Component, OnInit, ViewChild } from '@angular/core';
import { FitnessProgramService } from '../../fitness-program.service';
import { MaterialModule } from '../../../../material/material.module';
import { MatDialog } from '@angular/material/dialog';
import { ExcerciseInformationComponent } from './excercise-information/excercise.information/excercise.information.component';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { Subject } from 'rxjs';
import { UserService } from '../../../../user.service';
import { FitnessProgram } from '../../fitness-program';
import { CommonModule } from '@angular/common';
import { environment } from '../../../../../environments/environment';
import { FitnessProgramTypeService } from '../../fitness-program-type.service';
import { FitnessProgramType } from '../../fitness-program-type';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldControl } from '@angular/material/form-field';
import { MatCheckboxChange } from '@angular/material/checkbox';


@Component({
  selector: 'app-fitness-exercises',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './fitness.exercises.component.html',
  styleUrl: './fitness.exercises.component.css',
  providers: [FitnessProgramService, FitnessProgramTypeService]
})
export class FitnessExercisesComponent implements OnInit {


  lowerSelectedDuration: number | null = 0;
  upperSelectedDuration: number | null = 2000;
  categoriesGroup: FormGroup<any> = new FormGroup([]);
  searchFormControl = new FormControl('', []);
  paginatorControl = new FormControl();


  formatLabel(value: number): string {
    return `${value}`;
  }

  fitnessPrograms: FitnessProgram[] = [];
  bufferedFitnessPrograms: FitnessProgram[] = [];
  displayedExercises: FitnessProgram[] = [];
  public fitnessProgramTypes: FitnessProgramType[] = [];
  public checkBoxControl: any[] = [];
  public apiUrl: string;
  typesMap: Map<string, boolean> = new Map<string, boolean>();

  lowerSelectedPrice: number | null = 0;
  upperSelectedPrice: number | null = 2_000;

  


  constructor(public userService: UserService, 
    private fitnessProgramService: FitnessProgramService,
    private fitnessProgramTypeService: FitnessProgramTypeService,
    private dialog: MatDialog,
    private _formBuilder: FormBuilder) {
      this.apiUrl = environment.apiBaseUrl;
     }


  ngOnInit(): void {
    this.loadFitnessPrograms();
    this.loadFitnessProgramTypes();
  }

  loadFitnessPrograms() {
    this.fitnessProgramService.findAllFitnessPrograms().subscribe(response => {
      this.fitnessPrograms = response;
      this.bufferedFitnessPrograms = [...this.fitnessPrograms];
      this.displayedExercises = this.bufferedFitnessPrograms.slice(0, 5);
    });
  }

  loadFitnessProgramTypes()
  {
    this.fitnessProgramTypeService.getAllFitnessProgramTypes().subscribe(response => {
      this.fitnessProgramTypes = response;
      for(let fitnessType of this.fitnessProgramTypes)
        {
          this.typesMap.set(fitnessType.name, true);
        }
    }
    )
  }

  pageChanged(event: any) {
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
    this.displayedExercises = this.bufferedFitnessPrograms.slice(startIndex, endIndex);
  }

  openDialog(exercise: FitnessProgram) {

    const dialogRef = this.dialog.open(ExcerciseInformationComponent, {
      width: '70%',
      data: exercise
    });

    this.fitnessProgramService.setCurrentFitnessProgram(exercise);

    

    
    dialogRef.afterClosed().subscribe(result => {}).unsubscribe();
  }

  filterClick() {
        let newDisplayArray: FitnessProgram[] = [];

        for(let fitnessProgram of this.fitnessPrograms)
          {
            if(this.filterNameCheck(fitnessProgram) && this.filterCategoryCheck(fitnessProgram) && 
               this.filterPriceRangeCheck(fitnessProgram) && this.filterDurationRangeCheck(fitnessProgram))
              {
                newDisplayArray.push(fitnessProgram);
              }
            else
            {
              this.bufferedFitnessPrograms = [...this.fitnessPrograms];
              this.displayedExercises = this.bufferedFitnessPrograms.slice(0, 5);
            }
          }
        this.bufferedFitnessPrograms = newDisplayArray;
        this.displayedExercises = this.bufferedFitnessPrograms.slice(0, 5);
  }

  filterNameCheck(excercise: FitnessProgram): boolean
  {
    let searchValue = this.searchFormControl.value as string;

    if(searchValue.length == 0)
      {
        return true;
      }
    else
    {
      if((excercise.name?.toLowerCase())?.includes(searchValue.toLowerCase()))
        {
          return true;
        }
      else
      {
        return false;
      }
    }
  }

  filterCategoryCheck(excercise: FitnessProgram): boolean
  {
    let flag : boolean = false;
    this.typesMap.forEach((value,key) => {
      if(this.typesMap.get(key))
        {
          if(key == excercise.fitnessProgramType?.name)
            {
              flag = true;
              return;
            }
        }
    });
    return flag;
  }

  filterPriceRangeCheck(excercise: FitnessProgram) : boolean
  {
    if((this.lowerSelectedPrice as number) < (excercise.price as number) && (this.upperSelectedPrice as number) > (excercise.price as number))
      {
        return true;
      }
    else
    {
      return false;
    }
  }

  filterDurationRangeCheck(excercise: FitnessProgram) : boolean
  {
    if((this.lowerSelectedDuration as number) < (excercise.duration as number) && (this.upperSelectedDuration as number) > (excercise.duration as number))
      {
        return true;
      }
    else
    {
      return false;
    }
  }

  checkboxChange(fitnessProgramType: FitnessProgramType, event: any)
  {
    this.typesMap.set(fitnessProgramType.name, event.checked);
  }


//CHANGE VALUES ON SLIDER

changeLowerPrice(event: any) {
  this.lowerSelectedPrice = event.target.value as number;
  }

changeUpperPrice(event: any) {
  this.upperSelectedPrice = event.target.value as number;
  }

changeLowerDuration(event: any) {
  this.lowerSelectedDuration = event.target.value as number;
  }
changeUpperDuration(event: any) {
    this.upperSelectedDuration = event.target.value as number;
  }
}
