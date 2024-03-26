import { Component } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { UserService } from '../../../user.service';
import { Router } from '@angular/router';
import { User } from '../../../user';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  providers: [UserService]
})
export class NavbarComponent {


  currentUser: User | null = null;
  apiUrl: string;

  constructor(public userService: UserService, private router: Router)
  {
   this.currentUser = this.userService.getCurrentUser();
   console.log('THIS CURRENT USER:');
   console.log(this.currentUser);
   this.apiUrl = environment.apiBaseUrl;
   console.log("NAV REFRESHED!");
  }

  public getCurrentUserAvatar() : string
  {
    if(this.userService.getCurrentUser() == null || this.userService.getCurrentUser()?.avatar == null || (this.userService.getCurrentUser()?.avatar?.length as number < 1))
    {
     return '../../../../assets/default_images/defaultUser.png'
    }
    else
    {
      return this.userService.getCurrentUser()?.avatar as string;
    }

  }

  settingsClick() {
    this.router.navigate(["/settings"]);
  }
logoutClick() {
 this.userService.setCurrentUser(null);
 sessionStorage.removeItem(environment.userKeyString);
 this.router.navigate(["/start-page"]);
}

registerClick() {
  this.router.navigate(["/register-form"]);
  }
  loginClick() {
  this.router.navigate(["/login-form"]);
  }

  takeToMainPage() {
    console.log("TO MAIN PAGE!");
    this.router.navigate(["/main-page"]);
    }



}
