import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { UserService } from '../../../user.service';
import { FitnessProgramService } from '../fitness-program.service';
import { FitnessProgram } from '../fitness-program';
import { PurchaseService } from '../purchase.service';
import { User } from '../../../user';
import { environment } from '../../../../environments/environment';
import { ExcerciseInformationComponent } from '../fitness-excercises/fitness.exercises/excercise-information/excercise.information/excercise.information.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-my-programs',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './my-programs.component.html',
  styleUrl: './my-programs.component.css',
  providers:[UserService, FitnessProgramService, PurchaseService]
})
export class MyProgramsComponent implements OnInit{


  private purchases: any[];
  public myPrograms: FitnessProgram[];
  public apiUrl: string;
  public currentUser: User;

  

  constructor(private userService: UserService, private purchaseService: PurchaseService, private fitnessProgramService: FitnessProgramService,
    private dialog: MatDialog
  )
  {
    this.purchases = [];
    this.myPrograms = [];
    this.apiUrl = environment.apiBaseUrl;
    this.currentUser = userService.getCurrentUser() as User;
    
  }

  ngOnInit(): void {
      let allRows: any[] = [];
      this.fitnessProgramService.findFitnessProgramByUserId(this.userService.getCurrentUser()?.id as number).subscribe(response => this.myPrograms = response);
      
  }


  openDialog(exercise: FitnessProgram) {

    const dialogRef = this.dialog.open(ExcerciseInformationComponent, {
      width: '70%',
      data: exercise
    });

    this.fitnessProgramService.setCurrentFitnessProgram(exercise);

    

    
    dialogRef.afterClosed().subscribe(result => {}).unsubscribe();
  }

  deleteExcercise(event: Event, programToDelete: FitnessProgram) {

    event.stopPropagation();

    const confirmation = window.confirm(`Are you sure you want to delete program\nID: ${programToDelete.id}\nname: '${programToDelete.name}'?`);

    if(confirmation) //if user clicked OK
    {
        const index : number = this.myPrograms.indexOf(programToDelete);
        if (index !== -1) {
          this.fitnessProgramService.deleteFitnessProgram(programToDelete.id as number).subscribe(response => {
              this.myPrograms.splice(index, 1); // Remove just the program from the array
              alert(`Successfully deleted: ${programToDelete.name}`);
          });
      } else {
          console.error('Program not found in the array');
      }
    }
    else //if user clicked Cancel
    {
      
    }
  }
}
