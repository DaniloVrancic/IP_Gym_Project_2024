import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { User } from './user';
import { UserService } from './user.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { NavbarComponent } from './home/navbar/navbar/navbar.component';
import { MaterialModule } from './material/material.module';
import { RegisterFormComponent } from './register.form/register/register.form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [HttpClientModule, MaterialModule, NavbarComponent, RegisterFormComponent],
  providers: [UserService]
})
export class AppComponent implements OnInit{
  title: string = 'my-fitness-app';
 
  public loggedUser: User | null = null; //Can either be null or a User object, default set to null

  constructor(private userService: UserService){};

  ngOnInit() {
    /*
      let id : number = 1;
      this.getLoggedUser(id);
      */
     
  }

  public getLoggedUser(userId: number): void {
    this.userService.getUser(userId).subscribe({
      next: (response: User) => {
        this.loggedUser = response;
      },
      error: (error: any) => {
        alert(error.message); 
      }
    });
  }

  public logoutUser(): void
  {
    this.loggedUser = null;
  }
  

  
}
