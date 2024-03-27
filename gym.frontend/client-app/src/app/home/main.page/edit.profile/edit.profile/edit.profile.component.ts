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
  apiUrl: string;

  constructor(private fb: FormBuilder, public userService: UserService, private cdr: ChangeDetectorRef) {
    this.currentUser = JSON.parse(sessionStorage.getItem(environment.userKeyString) as string);
    this.apiUrl = environment.apiBaseUrl;
    
    //userService.getUser(14).subscribe(response => {this.currentUser = response; console.log(response)});

    //ONLY FOR TESTING, TODO: DELETE THIS CODE SEGMENT LATER:


    this.userDisplay  = {...this.currentUser};
    console.log("THIS USER DISPLAY");
    console.log(this.userDisplay);

    if(this.userDisplay?.avatar == null || this.userDisplay.avatar.length === 0)
    {
      this.userDisplay.avatar = environment.defaultUserImageURL; // if there is no profile picture uploaded, sets it to the default image
    }
    
    if(this.currentUser != null)
    {
      this.currentUser.password = "";
    }

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
    let newUserInformation : User = {...this.userDisplay};
    if(newUserInformation.avatar == environment.defaultUserImageURL)
    {
      newUserInformation.avatar = null;
    }
    newUserInformation.firstName = this.userForm.get('firstName')?.value;
    newUserInformation.lastName = this.userForm.get('lastName')?.value;
    newUserInformation.city = this.userForm.get('city')?.value;
    newUserInformation.email = this.userForm.get('email')?.value;
    newUserInformation.password = this.userForm.get('password')?.value;

    if (this.userForm.valid) {
      this.userService.updateUser(newUserInformation).subscribe( response => {this.userService.setCurrentUser(response); location.reload()});
    }
  }

  
onFileSelect(event: any) //selects and reads the file and assigns it to the avatar variable inside currentUser (or null if the file can't be read)
 {
   const file = event.target?.files[0];
   console.log("EVENT TARGET:");
   console.log(event);
   const reader = new FileReader();
   var currentUser = this.currentUser;
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
   

   reader.onloadend = () => {
       const imgBase64 = reader.result as string | null;
       
       this.currentUser.avatar = imgBase64;
       this.userDisplay.avatar = imgBase64;
   };

   if(file != null)
   {
    reader.readAsDataURL(file); 
   }
   else
   {
    this.userDisplay.avatar = environment.defaultUserImageURL;
   }
   //this.currentUser.avatar = userForRegister.avatar;
   
 }
}
