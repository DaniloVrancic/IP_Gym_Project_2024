import { ChangeDetectorRef, Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MyErrorStateMatcher } from '../register/register.form.component';
import { UserService } from '../../user.service';
import { User } from '../../user';
import { LoginCredentials } from './LoginCredentials';
import { HttpErrorResponse, HttpRequest } from '@angular/common/http';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule],
  templateUrl: './login.form.component.html',
  styleUrl: './login.form.component.css'
})
export class LoginFormComponent implements OnInit {

  submitButtonDisabled = true;

  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

  matcher = new MyErrorStateMatcher();
  currentUser: User | null;
  errorMessage: string;

  constructor(private userService: UserService, private cd: ChangeDetectorRef)
  {
    this.currentUser = userService.currentUser;
    this.errorMessage = '';
  }

  ngOnInit(): void {
    this.usernameFormControl.valueChanges.subscribe(() => {
      this.updateSubmitButtonState();
    });
    this.passwordFormControl.valueChanges.subscribe(() => {
      this.updateSubmitButtonState();
    });
  }

  updateSubmitButtonState() {
    const username = this.usernameFormControl.value;
    const password = this.passwordFormControl.value;

    if (username && password) {
      this.submitButtonDisabled = false; // Enable the submit button
    } else {
      this.submitButtonDisabled = true; // Disable the submit button
    }
  }



  onSubmit()
  {
    let usernameToLogin = this.usernameFormControl.value;
    let passwordToLogin = this.passwordFormControl.value;

    let userToLogin: LoginCredentials = {username: usernameToLogin,
                                          password: passwordToLogin};

    

    this.userService.loginUser(userToLogin).subscribe({
                                                       next: (response: User | string) => {
                                                          
                                                           //if the type is user
                                                          if(typeof response === 'string')
                                                          {

                                                          }
                                                          else
                                                          {
                                                            this.userService.setCurrentUser(response);
                                                            console.log(this.userService.currentUser);
                                                          }
                                                          

                                                        if(this.userService.currentUser?.activated === 0)
                                                           {
                                                             this.errorMessage = "Please activate your account via email.";
                                                             alert("A new activation link has been sent to your e-mail address.");
                                                           }
                                                           
                                                           if(this.userService.currentUser?.activated === 1)
                                                           {
                                                            this.errorMessage = '';
                                                             //TODO: REDIRECT TO MAIN
                                                             console.log("Works");
                                                           }
                                                           this.displayErrorBox(this.errorMessage);
                                                           console.log(this.errorMessage);
                                                         
                                                       },
                                                         error: (error: HttpErrorResponse) => {
                                                              
                                                            console.log(error);
                                                            
                                                          
                                                            let errorCode: string = error.error;
                                                            if(errorCode === 'incorrect_password')
                                                            {
                                                              this.errorMessage = "Password for user is incorrect.";
                                                            }
                                                            else if(errorCode === 'user_not_found')
                                                            {
                                                              this.errorMessage = "No registered username like that.";
                                                            }
                                                            else
                                                            {
                                                            this.errorMessage = "Something went wrong.";
                                                            }
                                                            console.log(this.errorMessage);
                                                            this.cd.detectChanges();
                                                              this.displayErrorBox(this.errorMessage);
                                                          
                                                            }
                                                                    
    });
  } //end of onSubmit()

  displayErrorBox(errorMessage: string) : void
  {
    var errorField = document.getElementById("errorField");

    if(errorField !== null)
    {
      if(errorMessage.length > 0)
      {
        errorField.innerHTML = errorMessage;
        errorField.style.display = "inline-block";
        errorField.style.textAlign = "left";
      }
      else
      {
        errorField.style.display = "none";
        errorField.style.visibility = "collapse";
      }
      
    }
  }
}//end of class LoginFormComponent
