import { ChangeDetectorRef, Component } from '@angular/core';
import { MaterialModule } from '../../material/material.module';
import { Router, RouterLink, RouterLinkActive, RouterLinkWithHref } from '@angular/router';

import { UserService } from '../../user.service';
import { User } from '../../user';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-start-page',
  standalone: true,
  imports: [MaterialModule, RouterLink, RouterLinkActive],
  templateUrl: './start-page.component.html',
  styleUrl: './start-page.component.css',
  providers: [UserService]
})
export class StartPageComponent {

  constructor(public userService: UserService, private router: Router, private cdr: ChangeDetectorRef)
  {
    this.userService.setCurrentUser(null);
  }

  routeAsGuest() {
    let guestUser: User | null = {
      id: 0,
      username: null,
      password: null,
      firstName: null,
      lastName: null,
      city: null,
      avatar: null,
      email: null,
      activated: null,
      type: null
    } ;
    guestUser.username = "guest";
    this.userService.setCurrentUser(guestUser)

    
    console.log(this.userService.getCurrentUser());
    sessionStorage.setItem(environment.userKeyString, JSON.stringify(this.userService.getCurrentUser()));
    this.router.navigate(["/main-page"]);
    
    
}
  
}
