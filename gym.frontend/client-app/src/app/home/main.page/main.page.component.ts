import { Component } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { UserService } from '../../user.service';
import { User } from '../../user';
import { FitnessProgramService } from '../../fitness.program.service';
import { FitnessExercisesComponent } from './fitness-excercises/fitness.exercises/fitness.exercises.component';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [MaterialModule, FitnessExercisesComponent],
  templateUrl: './main.page.component.html',
  styleUrl: './main.page.component.css',
  providers: [UserService, FitnessProgramService]
})
export class MainPageComponent {

  loggedUser: User;
  constructor(private userService: UserService, private fitnessService: FitnessProgramService)
  {
    this.loggedUser = {...userService.currentUser} as User;
    console.log(this.loggedUser);
    
    fitnessService.findAllFitnessPrograms().subscribe(response => console.log(response));
  }
}
