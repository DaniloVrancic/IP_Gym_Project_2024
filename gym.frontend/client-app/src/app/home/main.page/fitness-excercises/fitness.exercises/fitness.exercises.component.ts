import { Component, OnInit, ViewChild } from '@angular/core';
import { FitnessProgramService } from '../../../../fitness.program.service';
import { MaterialModule } from '../../../../material/material.module';
import { MatDialog } from '@angular/material/dialog';
import { ExcerciseInformationComponent } from './excercise-information/excercise.information/excercise.information.component';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-fitness-exercises',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './fitness.exercises.component.html',
  styleUrl: './fitness.exercises.component.css'
})
export class FitnessExercisesComponent implements OnInit {
  fitnessPrograms: any[] = [];
  displayedExercises: any[] = [];


  constructor(private fitnessProgramService: FitnessProgramService, private dialog: MatDialog) { }

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

  openDialog(exercise: any) {

    this.dialog.open(ExcerciseInformationComponent, {
      width: '70%',
      data: exercise
    });
  }
}
