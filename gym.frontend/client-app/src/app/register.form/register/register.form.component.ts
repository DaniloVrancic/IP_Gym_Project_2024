import { Component } from '@angular/core';
import { FormControl, FormGroupDirective, FormsModule, NgForm, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { MaterialModule } from '../../material/material.module';
import { ErrorStateMatcher } from '@angular/material/core';
import { RecaptchaModule, RecaptchaValueAccessorDirective } from 'ng-recaptcha';


@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule, RecaptchaModule],
  templateUrl: './register.form.component.html',
  styleUrl: './register.form.component.css',
  providers: [RecaptchaValueAccessorDirective]
})
export class RegisterFormComponent {
  firstNameFormControl = new FormControl('', [Validators.required]);
  lastNameFormControl = new FormControl('', [Validators.required]);
  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);
  cityFormControl = new FormControl('', [Validators.required]);
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);

  allControls : FormControl[] = [this.firstNameFormControl, this.lastNameFormControl,
                                 this.usernameFormControl, this.passwordFormControl, 
                                 this.cityFormControl, this.emailFormControl];

  matcher = new MyErrorStateMatcher();

  captcha: string | null;

  constructor()
  {
    this.captcha = null;
  }

  resolved(captchaResponse: string | null)
  {
    this.captcha = captchaResponse;
    console.log('Captcha resolved with response: ' + this.captcha);
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }


}