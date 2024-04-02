import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { environment } from '../../../../environments/environment';
import { FitnessProgram } from '../fitness-program';
import { User } from '../../../user';
import { UserService } from '../../../user.service';

@Component({
  selector: 'app-add-new-program',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, FormsModule],
  templateUrl: './add.new.program.component.html',
  styleUrl: './add.new.program.component.css',
  providers: [UserService]
})
export class AddNewProgramComponent implements OnInit{
onFileSelect($event: Event) {
throw new Error('Method not implemented.');
}


  programForm: FormGroup;
  apiUrl: string;
  selectedCategory: any;
  fitnessProgramToAdd: FitnessProgram;

  

  constructor(private fb: FormBuilder, private cdr: ChangeDetectorRef, private userService: UserService) {
    this.apiUrl = environment.apiBaseUrl;

    
    //userService.getUser(14).subscribe(response => {this.currentUser = response; console.log(response)});

    //ONLY FOR TESTING, TODO: DELETE THIS CODE SEGMENT LATER: 


    

    this.programForm = this.fb.group({
      titleName: [``, Validators.required],
      description: [``,],
      location: [``, Validators.required],
      difficulty: [``, [Validators.required, Validators.min(1), Validators.max(3), Validators.pattern('^[1-3]$')]],
      category: [``, Validators.required], //CATEGORY
      price: [``, Validators.pattern("[0-9]+\.?[0-9]*")],
      duration: [``, Validators.pattern("[1-3]?[0-9]{1,3}")],
      programImage: [] // You can initialize with a default image URL if needed
    });

    
    this.selectedCategory = null;

    this.fitnessProgramToAdd = {
      name: null,
      description: null,
      difficultyLevel: null,
      duration: null,
      fitnessProgramType: null,
      imageUrl: null,
      locationOfEvent: null,
      price: null,
      status: null,
      user_creator: null
    } as FitnessProgram;

   }

  ngOnInit(): void {
  }

  onSubmit() {
    
    if (this.programForm.valid) {
      this.fitnessProgramToAdd.name = this.programForm.get("name")?.value;
      this.fitnessProgramToAdd.description = this.programForm.get("description")?.value;
      this.fitnessProgramToAdd.locationOfEvent = this.programForm.get("location")?.value;
      this.fitnessProgramToAdd.difficultyLevel = this.programForm.get("difficulty")?.value;
      this.fitnessProgramToAdd.price = this.programForm.get("price")?.value;
      this.fitnessProgramToAdd.duration = this.programForm.get("duration")?.value;
      this.fitnessProgramToAdd.status = 0;
      this.fitnessProgramToAdd.user_creator = this.userService.getCurrentUser();
      this.fitnessProgramToAdd.fitnessProgramType = this.programForm.get("category")?.value;

      this.fitnessProgramToAdd.imageUrl = this.programForm.get("photo")?.value;

      console.log(this.fitnessProgramToAdd);
    }

 }
}
