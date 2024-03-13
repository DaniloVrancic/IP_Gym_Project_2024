import { Component, OnChanges, SimpleChanges } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MyErrorStateMatcher } from '../register/register.form.component';
import { UserService } from '../../user.service';
import { User } from '../../user';
import { LoginCredentials } from './LoginCredentials';
import { HttpRequest } from '@angular/common/http';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule],
  templateUrl: './login.form.component.html',
  styleUrl: './login.form.component.css'
})
export class LoginFormComponent implements OnChanges {
  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

  matcher = new MyErrorStateMatcher();
  currentUser: User | null;
  errorMessage: string;

  constructor(private userService: UserService)
  {
    this.currentUser = userService.currentUser;
    this.errorMessage = '';
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.currentUser = this.userService.currentUser
  }



  onSubmit()
  {
    let usernameToLogin = this.usernameFormControl.value;
    let passwordToLogin = this.passwordFormControl.value;

    let userToLogin: LoginCredentials = {username: usernameToLogin,
                                          password: passwordToLogin};

    

    this.userService.loginUser(userToLogin).subscribe(response => {  //TODO: FIX THIS METHOD, next SECTION NOT WORKING PROPERLY
                                                                    next: (response: User) => {
                                                                                              this.userService.setCurrentUser(response);
                                                                                              console.log("ihihii");
                                                                                               if(this.userService.currentUser === null) 
                                                                                              {this.errorMessage = "Requested user hasn't been found."}
                                                                                            else if(this.userService.currentUser.activated === 0)
                                                                                          {
                                                                                            this.errorMessage = "Please activate your account via email.";
                                                                                            alert("A new activation link has been sent to your e-mail address.");
                                                                                          }
                                                                                          else
                                                                                          {
                                                                                            //TODO: REDIRECT TO MAIN
                                                                                            console.log("Works");
                                                                                          }
                                                                                        };
                                                                    error: (error: any) => {
                                                                      console.log(response);
                                                                        console.log(error);
                                                                    }
                                                                    
    });
  }
}
