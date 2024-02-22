import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { User } from './user';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'client-app';
 /*
  public loggedUser!: User | null; //Can either be null or a User object

  constructor(private userService: UserService){};

  ngOnInit(): void {
      this.loggedUser = null;
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
  */

  
}
