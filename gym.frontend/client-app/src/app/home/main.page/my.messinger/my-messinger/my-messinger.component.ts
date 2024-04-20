import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import { MyMessingerService } from './my-messinger.service';
import { UserService } from '../../../../user.service';

@Component({
  selector: 'app-my-messinger',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './my-messinger.component.html',
  styleUrl: './my-messinger.component.css',
  providers: [MyMessingerService, UserService]
})
export class MyMessingerComponent implements OnInit{

isMessageAdvisorButtonDisabled: boolean;
isMessageUserButtonDisabled: boolean;

constructor(private myMessingerService: MyMessingerService,
            private userService: UserService
){
  this.isMessageUserButtonDisabled = true;
  this.isMessageAdvisorButtonDisabled = false;
}

ngOnInit()
{
  this.myMessingerService.getAllReceiverChats(this.userService.getCurrentUser()?.id as number).subscribe(response => console.log(response));
}

prepareForMessageFriend() {
  let chatUserButton : HTMLButtonElement = document.getElementById("chatUserButton") as HTMLButtonElement;
  let chatAdvisorButton : HTMLButtonElement = document.getElementById("chatAdvisorButton") as HTMLButtonElement;
  let toTextBox : HTMLInputElement = document.getElementById("to") as HTMLInputElement;

  this.isMessageAdvisorButtonDisabled = false;
  this.isMessageUserButtonDisabled = true;
  toTextBox.value = '';
}
prepareForMessageAdvisor() {
  let chatUserButton : HTMLButtonElement = document.getElementById("chatUserButton") as HTMLButtonElement;
  let chatAdvisorButton : HTMLButtonElement = document.getElementById("chatAdvisorButton") as HTMLButtonElement;
  let toTextBox : HTMLInputElement = document.getElementById("to") as HTMLInputElement;

  this.isMessageAdvisorButtonDisabled = true;
  this.isMessageUserButtonDisabled = false;

  toTextBox.value = 'advisor@mail.com';

}

}
