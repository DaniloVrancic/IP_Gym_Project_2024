import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { environment } from '../../../../environments/environment';
import { FitnessProgram } from '../fitness-program';
import { User } from '../../../user';
import { UserService } from '../../../user.service';
import { FitnessProgramTypeService } from '../fitness-program-type.service';
import { FitnessProgramType } from '../fitness-program-type';
import { FitnessProgramService } from '../fitness-program.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-new-program',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, FormsModule],
  templateUrl: './add.new.program.component.html',
  styleUrl: './add.new.program.component.css',
  providers: [UserService, FitnessProgramTypeService, FitnessProgramService]
})
export class AddNewProgramComponent implements OnInit{

  

  programForm: FormGroup;
  apiUrl: string;
  selectedCategory: any;
  fitnessProgramToAdd: FitnessProgram;
  public fitnessProgramTypes!: FitnessProgramType[];

  constructor(private fb: FormBuilder, private cdr: ChangeDetectorRef, private userService: UserService, 
    private fitnessProgramTypeService: FitnessProgramTypeService, private fitnessProgramService: FitnessProgramService,
    private router: Router) {
    this.apiUrl = environment.apiBaseUrl;


    

    this.programForm = this.fb.group({
      name: [``, Validators.required],
      description: [``,],
      location: [``, Validators.required],
      difficulty: [``, [Validators.required, Validators.min(1), Validators.max(3), Validators.pattern('^[1-3]$')]],
      category: [``, Validators.required], //CATEGORY
      price: [``, Validators.pattern("[0-9]{1,4}\.?[0-9]{0,4}")],
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
    
    this.fitnessProgramTypeService.getAllFitnessProgramTypes().subscribe(result => this.fitnessProgramTypes = result);
  }


onFileSelect(event: any)   {
  const file = event.target.files[0];
  const reader = new FileReader();
  let fitnessProgramToAdd = this.fitnessProgramToAdd;

  let fileNameElement = document.getElementById("file-name") as HTMLElement | null;
  let programImageElement = document.getElementById("displayed-photo") as HTMLElement;

  if(file === undefined || file === null)
  {
    (fileNameElement as HTMLElement).innerHTML = "";
    (fileNameElement as HTMLElement).style.display = 'inline';
    programImageElement.setAttribute("src", environment.defaultProgramImage); //returns to display the default image in the page
  }
  else if (file != undefined && fileNameElement) {
    fileNameElement.innerHTML = file.name; // Assuming you want to display the file name
    fileNameElement.style.display = 'inline-block';
  } else {
    console.error("File name element not found.");
  }
  

  reader.onloadend = (event: any) => {
      const imgBase64: string | null = event.target.result as string | null;
      const defaultPicLocation : string = environment.defaultProgramImage;
      fitnessProgramToAdd.imageUrl = imgBase64;

      this.fitnessProgramToAdd.imageUrl = imgBase64;
      document.getElementById("displayed-photo")?.setAttribute("src", imgBase64 as string); //Sets the image on the page to the selected image
      
  };

  reader.onerror = (event) => {
    document.getElementById("displayed-photo")?.setAttribute("src", environment.defaultProgramImage);
  this.fitnessProgramToAdd.imageUrl = null;};

  if(file != undefined)
  {
  reader.readAsDataURL(file);
  this.fitnessProgramToAdd.imageUrl = fitnessProgramToAdd.imageUrl;
  }
  else
  {
    this.fitnessProgramToAdd.imageUrl = null;
  }
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

      this.fitnessProgramService.addFitnessProgram(this.fitnessProgramToAdd).subscribe(
        result => {alert("Successfuly added new program."); this.router.navigate(["/main-page"]);});
  
    }
 }
}
