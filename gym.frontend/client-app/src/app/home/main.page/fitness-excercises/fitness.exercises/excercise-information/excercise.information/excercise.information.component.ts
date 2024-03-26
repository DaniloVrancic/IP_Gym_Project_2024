import { AfterViewInit, Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommentService } from '../../../../../../comment.service';
import { MaterialModule } from '../../../../../../material/material.module';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../../../../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-excercise-information',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './excercise.information.component.html',
  styleUrl: './excercise.information.component.css',
  providers: [CommentService, UserService]
})
export class ExcerciseInformationComponent implements AfterViewInit{



  commentTextArea!: any;
  

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
// TODO: IMPLEMENT THIS METHOD THAT WILL TAKE THE TEXT FROM this.commentText and post it accordingly, with the logged in user

this.commentService.addComment(this.userService.getCurrentUser()?.id as number, this.exercise.id as number, this.commentText as string).subscribe(response => { this.commentsOnProgram?.push(response)});
}

  caughtExcercise: any;
  commentsOnProgram: any[] | null;
  commentText: string;
  isPostCommentDisabled: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) public exercise: any, private commentService: CommentService, public userService: UserService, private router: Router, public dialogRef: MatDialogRef<ExcerciseInformationComponent>) { //Injects data about the excercise from the parent module
    this.caughtExcercise = {...exercise};
    this.commentsOnProgram = null;
    this.commentText = "";
    this.isPostCommentDisabled = true;
  

    commentService.getCommentsForProgram(this.caughtExcercise.id).subscribe(
      response => {this.commentsOnProgram = (response);
});
  }
    

  ngAfterViewInit(): void {
    console.log("THIS NEXt ELEMENT IS comment-text-area:");
    this.commentTextArea = document.getElementById('comment-text-area');
    console.log(this.commentTextArea);

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


}
