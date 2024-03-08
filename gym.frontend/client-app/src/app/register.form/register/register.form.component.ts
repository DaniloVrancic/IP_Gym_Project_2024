import { Component } from '@angular/core';
import { FormControl, FormGroupDirective, FormsModule, NgForm, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { MaterialModule } from '../../material/material.module';
import { ErrorStateMatcher } from '@angular/material/core';
import { RecaptchaModule, RecaptchaValueAccessorDirective } from 'ng-recaptcha';
import { UserService } from '../../user.service';
import { User } from '../../user';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule, RecaptchaModule],
  templateUrl: './register.form.component.html',
  styleUrl: './register.form.component.css',
  providers: [RecaptchaValueAccessorDirective, UserService]
})
export class RegisterFormComponent {
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
  loggedUser : User;

  constructor(private userService: UserService)
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
    this.loggedUser = {} as User;
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
  
      console.log(this.userForRegister);
      this.userForRegister.type = 3; // 3 = USER accounts
      this.userForRegister.activated = 0; // Default value
      this.userService.addUser(this.userForRegister).subscribe({
        next: (response: User) => {
          this.loggedUser = response;
          console.log('REGISTERED USER: ');
          console.log(this.loggedUser as User);
        },
        error: (error: any) => {
          console.log(error.message);
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

    reader.onloadend = function(event : any) {
        const imgBase64 = event.target.result as string | null;
        

        // Assign the BASE64 string to this.userForRegister.avatar
        userForRegister.avatar = imgBase64

    };

    reader.readAsDataURL(file);
    this.userForRegister.avatar = userForRegister.avatar;
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }


}