import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, FormsModule, NgForm, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { MaterialModule } from '../../material/material.module';
import { ErrorStateMatcher } from '@angular/material/core';
import { RecaptchaModule, RecaptchaValueAccessorDirective } from 'ng-recaptcha';
import { UserService } from '../../user.service';
import { User } from '../../user';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule, RecaptchaModule],
  templateUrl: './register.form.component.html',
  styleUrl: './register.form.component.css',
  providers: [RecaptchaValueAccessorDirective, UserService]
})
export class RegisterFormComponent implements OnInit{
  firstNameFormControl = new FormControl('', [Validators.required]);
  lastNameFormControl = new FormControl('', [Validators.required]);
  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);
  cityFormControl = new FormControl('', [Validators.required]);
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  profilePictureFormControl = new FormControl(null);

  allControls : FormControl[] = [this.firstNameFormControl, this.lastNameFormControl,
                                 this.usernameFormControl, this.passwordFormControl, 
                                 this.cityFormControl, this.emailFormControl,
                                this.profilePictureFormControl];

  matcher = new MyErrorStateMatcher();

  captcha: string | null;
  public userForRegister : User;

  ngOnInit(): void {
      let selectedFile : HTMLElement | null = document.getElementById("file-name");
      if(selectedFile != undefined && selectedFile?.innerHTML.length < 1)
      {
        selectedFile.style.display = 'inline';
      }
  }

  constructor(private userService: UserService, private router: Router)
  {
    this.captcha = 'a'; //SET TO NULL WHEN PRODUCT IS FINISHED
    this.userForRegister = 
                          {
                            username: "",
                            password: "",
                            firstName: "",
                            lastName: "",
                            city: "",
                            avatar: "",
                            email: "",
                          } as User;
    
  }

  resolved(captchaResponse: string | null)
  {
    this.captcha = captchaResponse;
    //console.log('Captcha resolved with response: ' + this.captcha);
  }

  onSubmit() {
    if (this.checkIfFormValid(this.allControls)) {
      this.userForRegister.username = this.usernameFormControl.value;
      this.userForRegister.password = this.passwordFormControl.value;
      this.userForRegister.firstName = this.firstNameFormControl.value;
      this.userForRegister.lastName = this.lastNameFormControl.value;
      this.userForRegister.city = this.cityFormControl.value;
      this.userForRegister.email = this.emailFormControl.value;
  
      if (this.profilePictureFormControl.value != null && (this.profilePictureFormControl.value as string).length > 0) {
       
      } else {
        this.userForRegister.avatar = null;
      }
  
      
      this.userForRegister.type = 3; // 3 = USER accounts
      this.userForRegister.activated = 0; // Default value
      this.userService.addUser(this.userForRegister).subscribe({
        next: (response: User) => {
          this.userForRegister = (response);
          alert("Activation link has been sent to E-mail:\n" + this.userForRegister.email);
          //redirects user to login form or main form
          if (response.activated === 0) {
            this.router.navigate(['/login-form']); // Redirect to login page if activated is 0
          } else {
            this.userService.setCurrentUser(response);
            this.router.navigate(['/main-page']); // Redirect to main page if activated is 1
          }
        },
        error: (error: any) => {
          alert(error.message);
        },
      });
    }
  }
  

  private checkIfFormValid(allControls: FormControl[]): boolean
  {

    let allValid: boolean = true;

    for(let control of allControls)
    {
      allValid &&= (control.status === "VALID");
    }

    return allValid;
  }

  onChangeFile(event: any)
  {
    const file = event.target.files[0];
    const reader = new FileReader();
    let userForRegister = this.userForRegister;

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
    

    reader.onloadend = function(event : any) {
        const imgBase64 = event.target.result as string | null;
        

        // Assign the BASE64 string to this.userForRegister.avatar
        userForRegister.avatar = imgBase64

    };

    if(file != undefined)
    {
    reader.readAsDataURL(file);
    this.userForRegister.avatar = userForRegister.avatar;
    }
    else
    {
      this.userForRegister.avatar = null;
    }

  }
}
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}