import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { environment } from '../../../../environments/environment';
import { FitnessProgram } from '../fitness-program';

@Component({
  selector: 'app-add-new-program',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, FormsModule],
  templateUrl: './add.new.program.component.html',
  styleUrl: './add.new.program.component.css'
})
export class AddNewProgramComponent implements OnInit{
onFileSelect($event: Event) {
throw new Error('Method not implemented.');
}
onSubmit() {
throw new Error('Method not implemented.');
}

  userForm: FormGroup;
  apiUrl: string;
  selectedCategory: FitnessProgram | null;
  

  constructor(private fb: FormBuilder, private cdr: ChangeDetectorRef) {
    this.apiUrl = environment.apiBaseUrl;
    
    //userService.getUser(14).subscribe(response => {this.currentUser = response; console.log(response)});

    //ONLY FOR TESTING, TODO: DELETE THIS CODE SEGMENT LATER:


    

    this.userForm = this.fb.group({
      titleName: [``, Validators.required],
      description: [``,],
      location: [``, Validators.required],
      difficulty: [``, [Validators.required, Validators.min(1), Validators.max(3), Validators.pattern('^[1-3]$')]],
      category: [``, Validators.required],
      programImage: [] // You can initialize with a default image URL if needed
    });

    
    this.selectedCategory = null;

   }

  ngOnInit(): void {
  }

}
