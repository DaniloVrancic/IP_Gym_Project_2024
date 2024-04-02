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

@Component({
  selector: 'app-fitness-exercises',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './fitness.exercises.component.html',
  styleUrl: './fitness.exercises.component.css',
  providers: [FitnessProgramService]
})
export class FitnessExercisesComponent implements OnInit {
  fitnessPrograms: FitnessProgram[] = [];
  displayedExercises: FitnessProgram[] = [];


  constructor(public userService: UserService, 
    private fitnessProgramService: FitnessProgramService, 
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadFitnessPrograms();
  }

  loadFitnessPrograms() {
    this.fitnessProgramService.findAllFitnessPrograms().subscribe(response => {
      this.fitnessPrograms = response;
      this.displayedExercises = this.fitnessPrograms.slice(0, 5);
    });
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

    

    
    dialogRef.afterClosed().subscribe(result => {console.log("DIALOG CLOSED", result)})
  }
}
