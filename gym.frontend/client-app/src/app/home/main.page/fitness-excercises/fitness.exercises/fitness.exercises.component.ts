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
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldControl } from '@angular/material/form-field';


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
upperSelectedDuration: number | null = 4000;
categoriesGroup!: FormGroup<any>;


  formatLabel(value: number): string {
    return `${value}`;
  }

  fitnessPrograms: FitnessProgram[] = [];
  displayedExercises: FitnessProgram[] = [];
  public fitnessProgramTypes: FitnessProgramType[] = [];
  public apiUrl: string;

  lowerSelectedPrice: number | null = 0;
  upperSelectedPrice: number | null = 10_000;

  


  constructor(public userService: UserService, 
    private fitnessProgramService: FitnessProgramService,
    private fitnessProgramTypeService: FitnessProgramTypeService,
    private dialog: MatDialog) {
      this.apiUrl = environment.apiBaseUrl;
     }

  ngOnInit(): void {
    this.loadFitnessPrograms();
    this.loadFitnessProgramTypes();
  }

  loadFitnessPrograms() {
    this.fitnessProgramService.findAllFitnessPrograms().subscribe(response => {
      this.fitnessPrograms = response;
      this.displayedExercises = this.fitnessPrograms.slice(0, 5);
    });
  }

  loadFitnessProgramTypes()
  {
    this.fitnessProgramTypeService.getAllFitnessProgramTypes().subscribe(response => {
      this.fitnessProgramTypes = response;
    }
    )
  }

  pageChanged(event: any) {
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
    this.displayedExercises = this.fitnessPrograms.slice(startIndex, endIndex);
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


        console.log("Lower Selected Price:", this.lowerSelectedPrice);
        console.log("Upper Selected Price:", this.upperSelectedPrice);
        console.log("Lower Selected Duration:", this.lowerSelectedDuration);
        console.log("Upper Selected Duration:", this.upperSelectedDuration);
}


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
