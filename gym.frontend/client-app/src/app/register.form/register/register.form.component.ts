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
  profilePictureFormControl = new FormControl('', []);

  allControls : FormControl[] = [this.firstNameFormControl, this.lastNameFormControl,
                                 this.usernameFormControl, this.passwordFormControl, 
                                 this.cityFormControl, this.emailFormControl,
                                this.profilePictureFormControl];

  matcher = new MyErrorStateMatcher();

  captcha: string | null;
  userForRegister : User;

  constructor(private userService: UserService)
  {
    this.captcha = null;
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

  onSubmit()
  {
    console.log(this.checkIfFormValid(this.allControls));

    if(this.checkIfFormValid(this.allControls))
    {
      this.userService.addUser(this.userForRegister);
    }
    else
    {

    }
  }

  private checkIfFormValid(allControls: FormControl[]): boolean
  {
    console.log(this.userForRegister);

    let allValid: boolean = true;

    for(let control of allControls)
    {
      allValid &&= (control.status === "VALID");
    }

    return allValid;
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }


}