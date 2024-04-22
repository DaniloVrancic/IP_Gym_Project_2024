import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import { MyMessingerService } from './my-messinger.service';
import { UserService } from '../../../../user.service';
import { ChatroomEntity } from './chatroomEntity';
import { User } from '../../../../user';

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
isSendButtonDisabled: boolean;
public allReceivedChats: ChatroomEntity[] = [];

usernameReceiver: string;

constructor(private myMessingerService: MyMessingerService,
            private userService: UserService
){
  this.isMessageUserButtonDisabled = true;
  this.isMessageAdvisorButtonDisabled = false;
  this.isSendButtonDisabled = true;
  this.usernameReceiver = "";
}

ngOnInit()
{
  this.myMessingerService.getAllReceiverChats(this.userService.getCurrentUser()?.id as number).subscribe(response => this.allReceivedChats = response);
}

prepareForMessageFriend() {
  let chatUserButton : HTMLButtonElement = document.getElementById("chatUserButton") as HTMLButtonElement;
  let chatAdvisorButton : HTMLButtonElement = document.getElementById("chatAdvisorButton") as HTMLButtonElement;
  let toTextBox : HTMLInputElement = document.getElementById("to") as HTMLInputElement;

  this.isMessageAdvisorButtonDisabled = false;
  this.isMessageUserButtonDisabled = true;
  toTextBox.value = '';
  this.checkIfFormValid();
}
prepareForMessageAdvisor() {
  let chatUserButton : HTMLButtonElement = document.getElementById("chatUserButton") as HTMLButtonElement;
  let chatAdvisorButton : HTMLButtonElement = document.getElementById("chatAdvisorButton") as HTMLButtonElement;
  let toTextBox : HTMLInputElement = document.getElementById("to") as HTMLInputElement;

  this.isMessageAdvisorButtonDisabled = true;
  this.isMessageUserButtonDisabled = false;

  toTextBox.value = 'advisor@mail.com';
  this.checkIfFormValid()
}

checkIfFormValid()
{
  let toTextBox : HTMLInputElement = document.getElementById("to") as HTMLInputElement;
  let messageTextBox : HTMLInputElement = document.getElementById("text-message") as HTMLInputElement;

  

  if(toTextBox.value.length > 0 && messageTextBox.value.length > 0)
    {
      this.isSendButtonDisabled = false;
    }
  else
  {
    this.isSendButtonDisabled = true;
  }
}

sendMessage() {
  let toTextBox : HTMLInputElement = document.getElementById("to") as HTMLInputElement;
  let messageTextBox : HTMLInputElement = document.getElementById("text-message") as HTMLInputElement;
  let errorMessageDiv = document.getElementById("errorMessage");
  this.usernameReceiver = toTextBox.value;
  let textToSend = messageTextBox.value;
  if(this.isMessageAdvisorButtonDisabled)
    {
      let newMessageForAdvisors: ChatroomEntity = {text: textToSend, user_sender: this.userService.getCurrentUser() as User, readMsg: false} as any;
      this.myMessingerService.addChatToAllUsersWithType(newMessageForAdvisors, 2).subscribe(result => {console.log("Sent message to advisors."); console.log(result); alert("Sent message to advisors.")});
      return;
    }

  

  this.userService.findUserByUsername(this.usernameReceiver).subscribe(
    resultingUser => {
      let newMessage: ChatroomEntity = {text: textToSend, user_sender: this.userService.getCurrentUser() as User, user_receiver: resultingUser as User, readMsg: false} as any;
      this.myMessingerService.addChat(newMessage).subscribe(resultMessage => {console.log("SENT");
                                                                              console.log(resultMessage);
                                                                              if (errorMessageDiv) {
                                                                                errorMessageDiv.style.display = "none";
                                                                              }
                                                                              alert("Successfully sent message!")
                                                                            })
    },
  error => { // This is if the username is not found in the database
    console.log(error);
    this.showErrorMessage();
  });

  //let newChatroomEntity : ChatroomEntity = {text: textToSend, }
  //this.myMessingerService.addChat()
  }

  showErrorMessage()
  {
    let errorMessageDiv = document.getElementById("errorMessage");

    if (errorMessageDiv) {
      errorMessageDiv.style.display = "block";
    }
  }

  showCompleteMessage(completeMessage: ChatroomEntity) {
    let selectedMessageFrom = document.getElementById("selectedMessageFrom");
    let selectedMessageTime = document.getElementById("selectedMessageTime");
    let selectedMessageText = document.getElementById("selectedMessageText");

    if(selectedMessageFrom)
      {
        selectedMessageFrom.innerHTML = "FROM: " + completeMessage.user_sender.username as string;
      }
    if(selectedMessageTime)
      {
          selectedMessageTime.innerHTML = "TIME OF SENDING: " + completeMessage.timeOfSend as string;
      }
    if(selectedMessageText)
      {
        selectedMessageText.innerHTML = completeMessage.text as string;
      }
  

    let selectedMessageDisplay = document.getElementById("selected-message-display");

      if(selectedMessageDisplay)
        {
          selectedMessageDisplay.style.display = "block";
        }


    }

  clearSelectedMessageDisplay() {
      let selectedMessageDisplay = document.getElementById("selected-message-display");

      if(selectedMessageDisplay)
        {
          selectedMessageDisplay.style.display = "none";
        }
    }

}
