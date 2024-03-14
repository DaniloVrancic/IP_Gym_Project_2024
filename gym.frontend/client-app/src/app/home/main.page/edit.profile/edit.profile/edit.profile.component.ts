import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../../../user.service';
import { User } from '../../../../user';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-edit-profile',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule],
  templateUrl: './edit.profile.component.html',
  styleUrl: './edit.profile.component.css',
  providers: [UserService]
})
export class EditProfileComponent implements OnInit {
  userForm: FormGroup;
  currentUser: User;
  userDisplay: User; //Serves to display appropriate values on the form

  constructor(private fb: FormBuilder, private userService: UserService, private cdr: ChangeDetectorRef) {
    this.currentUser = userService.currentUser as User;
    this.userDisplay  =
    {
      username: "",
      password: "",
      firstName: "",
      lastName: "",
      city: "",
      avatar: "",
      email: "",
    } as User;

    //ONLY FOR TESTING, TODO: DELETE THIS CODE SEGMENT LATER:
    this.currentUser = {
      username: "angularSlayer69",
      firstName: "Danilo",
      lastName: "Vrancic",
      id: 525,
      password: "",
      city: "Banja Luka",
      email: "danilov98@hotmail.com",
      avatar: "",
    type: 3,
    activated: 1}
    
    //

    if(this.currentUser?.avatar === null || this.currentUser?.avatar.length <= 0)
    {
      this.userDisplay.avatar = environment.defaultUserImageURL; // if there is no profile picture uploaded, sets it to the default image
    }
    
    this.currentUser.password = "";

    this.userForm = this.fb.group({
      firstName: [`${this.currentUser?.firstName}`, Validators.required],
      lastName: [`${this.currentUser?.lastName}`, Validators.required],
      city: [`${this.currentUser?.city}`, Validators.required],
      email: [`${this.currentUser?.email}`, [Validators.required, Validators.email]],
      password: [`${this.currentUser?.password}`, Validators.required],
      profileImage: [] // You can initialize with a default image URL if needed
    });

    
   }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.userForm.valid) {
      // Submit form data here
      console.log(this.userForm.value);

      let userToUpdate: User = this.userService.currentUser as User;
      if(userToUpdate !== null)
      {
        this.userDisplay.firstName = this.userForm.get("firstName")?.value;
        this.userDisplay.lastName = this.userForm.get("lastName")?.value;
        this.userDisplay.city = this.userForm.get("city")?.value;
        this.userDisplay.email = this.userForm.get("email")?.value;
        this.userDisplay.password = this.userForm.get("password")?.value;
        //this.userDisplay is automatically updated in the onFileSubmit() method this is automatically updated in the onFileSelect method
        console.log(this.userDisplay);
      }

    }
  }

  
async onFileSelect(event: any) //selects and reads the file and assigns it to the avatar variable inside currentUser (or null if the file can't be read)
 {
   const file = event.target.files[0];
   console.log("OVO STO ME ZANIMA");
   console.log(this.userForm.get("profileImage")?.value);
   const reader = new FileReader();
   let currentUser = this.currentUser;
   var userForRegister : User = {
     avatar: null,
     id: null,
     username: null,
     password: null,
     firstName: null,
     lastName: null,
     city: null,
     email: null,
     activated: null,
     type: null
   };

   let fileNameElement = document.getElementById("file-name") as HTMLElement | null;

   if(file === undefined)
   {
     (fileNameElement as HTMLElement).innerHTML = "";
     (fileNameElement as HTMLElement).style.display = 'inline';
   }
   else if (file != undefined && fileNameElement) {
     fileNameElement.innerHTML = file.name; // Assuming you want to display the file name
     fileNameElement.style.display = 'inline-block';
   } else {
     console.error("File name element not found.");
   }
   

   reader.onloadend = async function(event : any) {
       const imgBase64 = event.target.result as string | null;
       

       // Assign the BASE64 string to this.userForRegister.avatar
       currentUser.avatar =  await imgBase64;
   };

   if(file != null)
   {
     reader.readAsDataURL(file);
     this.userDisplay.avatar = this.userForm.get("profileImage")?.value; //TODO: FIX THIS, INPUT REAL PATH TO IMAGE
     this.currentUser.avatar = null;
     this.cdr.detectChanges();
     
   }
   else
   {

   }
   //this.currentUser.avatar = userForRegister.avatar;
   console.log("FINISHED LOADING IMAGE");
      console.log(this.userDisplay.avatar);
   
   

 }
}
