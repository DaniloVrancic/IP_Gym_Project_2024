import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { UserService } from '../../user.service';
import { User } from '../../user';
import { FitnessProgramService } from '../../fitness.program.service';
import { FitnessExercisesComponent } from './fitness-excercises/fitness.exercises/fitness.exercises.component';
import { RssNewsComponent } from "./rss.news/rss.news/rss.news.component";

@Component({
    selector: 'app-main-page',
    standalone: true,
    templateUrl: './main.page.component.html',
    styleUrl: './main.page.component.css',
    providers: [UserService, FitnessProgramService],
    imports: [MaterialModule, FitnessExercisesComponent, RssNewsComponent]
})
export class MainPageComponent implements OnInit{

  loggedUser!: User | null;
  constructor(public userService: UserService, private fitnessService: FitnessProgramService)
  {
    this.loggedUser = userService.getCurrentUser();
    console.log("IN CONSTRUCTOR OF MAIN PAGE COMPONENT:");
    console.log(userService.getCurrentUser());
    console.log(this.loggedUser);
  }

  ngOnInit(): void {

    console.log("In main page OnInit:");
    console.log(this.userService.getCurrentUser());
    
    
    this.loggedUser = {...this.userService.getCurrentUser()} as User | null;
    this.fitnessService.findAllFitnessPrograms().subscribe(response => console.log(response));
    
  }
}
