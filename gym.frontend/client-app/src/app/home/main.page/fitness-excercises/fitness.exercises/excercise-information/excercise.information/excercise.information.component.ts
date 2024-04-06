import { AfterViewInit, Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommentService } from '../../../../../../comment.service';
import { MaterialModule } from '../../../../../../material/material.module';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../../../../user.service';
import { Router } from '@angular/router';
import { FitnessProgram } from '../../../../fitness-program';
import { environment } from '../../../../../../../environments/environment';
import { CommonModule } from '@angular/common';
import { PurchaseService } from '../../../../purchase.service';
import { SubscribeService } from '../../../../subscriptions/subscribe.service';

@Component({
  selector: 'app-excercise-information',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './excercise.information.component.html',
  styleUrl: './excercise.information.component.css',
  providers: [CommentService, UserService, CommonModule, PurchaseService, SubscribeService]
})
export class ExcerciseInformationComponent implements AfterViewInit{




  commentTextArea!: any;
  
  

  caughtExcercise: FitnessProgram;
  commentsOnProgram: any[] | null;
  commentText: string;
  isPostCommentDisabled: boolean;
  isParticipateDisabled: boolean;
  public userHasPurchased: boolean;
  public defaultImage: string;
  public apiUrl : string;
  public isSubscribed: boolean;
  public isHovered: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) public exercise: FitnessProgram,
                                       private commentService: CommentService, 
                                       public userService: UserService, 
                                       private router: Router, 
                                       public dialogRef: MatDialogRef<ExcerciseInformationComponent>,
                                       private purchaseService: PurchaseService,
                                       private subscribeService: SubscribeService) { //Injects data about the excercise from the parent module
    this.caughtExcercise = {...exercise};
    this.defaultImage = environment.defaultProgramImage;
    this.apiUrl = environment.apiBaseUrl;
    this.userHasPurchased = false;
    this.commentsOnProgram = null;
    this.commentText = "";
    this.isPostCommentDisabled = true;
    this.isSubscribed = false;
    this.isHovered = false; //tracks if this button is hovered on with the mouse cursor
    subscribeService.userHasSubscription(userService.getCurrentUser()?.id as number, this.caughtExcercise?.fitnessProgramType?.id as number).subscribe(hasSubscribed => {console.log(hasSubscribed);this.isSubscribed = hasSubscribed;});
    if(this.userService.getCurrentUser()?.email)
    {
      this.isParticipateDisabled = false;
    }
    else
    {
      this.isParticipateDisabled = true;
    }

    if(userService.getCurrentUser()?.id == 0)
    {
      this.userHasPurchased = false;
    }
    else
    {
     purchaseService.userHasPurchase(this.userService.getCurrentUser()?.id as number, this.caughtExcercise.id as number)
                    .subscribe(hasPurchased => {this.userHasPurchased = hasPurchased;});
    }
  

    commentService.getCommentsForProgram(this.caughtExcercise.id as number).subscribe(
      response => {this.commentsOnProgram = (response);
});
  }


textChanged(event: any) {
    this.commentText = event.target.value;
    if(this.commentText.length > 0)
    {
      this.isPostCommentDisabled = false;
    }
    else
    {
      this.isPostCommentDisabled = true;
    }
}

postComment(event: MouseEvent) {
    this.commentService.addComment(this.userService.getCurrentUser()?.id as number, this.exercise.id as number, this.commentText as string).subscribe(response => { this.commentsOnProgram?.push(response);});
}

 
    

  ngAfterViewInit(): void {

    this.commentTextArea = document.getElementById('comment-text-area');
 

    if(this.userService.getCurrentUser()?.email == null)
    {
      this.commentTextArea.disabled = true;
    }
  }

 
  routeToPurchasePage() {
    this.router.navigate(["/purchase-page"]).then(() => {
      this.dialogRef.close();
    });;
    }

    subscribeToCategory(event: any) {
      console.log(this.userService.getCurrentUser()?.id as number, this.caughtExcercise.fitnessProgramType?.id as number);
        this.subscribeService.addSubscription(
          this.userService.getCurrentUser()?.id as number, this.caughtExcercise.fitnessProgramType?.id as number)
          .subscribe((result) => {this.isSubscribed=true; alert("Subscribed")});

    }

    unsubscribeFromCategory(event: Event)
    {
      this.subscribeService.removeSubscriptionByUserAndType(
        this.userService.getCurrentUser()?.id as number, this.caughtExcercise.fitnessProgramType?.id as number)
        .subscribe(() => {this.isSubscribed = false, alert("Unsubscribed")});
    }

}
