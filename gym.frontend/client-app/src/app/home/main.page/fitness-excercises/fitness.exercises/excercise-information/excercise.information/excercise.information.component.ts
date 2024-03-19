import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-excercise-information',
  standalone: true,
  imports: [],
  templateUrl: './excercise.information.component.html',
  styleUrl: './excercise.information.component.css'
})
export class ExcerciseInformationComponent {

  caughtExcercise: any;
  

  constructor(@Inject(MAT_DIALOG_DATA) public exercise: any) { 
    this.caughtExcercise = {...exercise};
  }


}
