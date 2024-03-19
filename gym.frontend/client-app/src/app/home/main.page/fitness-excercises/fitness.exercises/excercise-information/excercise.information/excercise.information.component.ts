import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommentService } from '../../../../../../comment.service';
import { MaterialModule } from '../../../../../../material/material.module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-excercise-information',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './excercise.information.component.html',
  styleUrl: './excercise.information.component.css',
  providers: [CommentService]
})
export class ExcerciseInformationComponent {
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
}

  caughtExcercise: any;
  commentsOnProgram: any[] | null;
  commentText: string;
  isPostCommentDisabled: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) public exercise: any, private commentService: CommentService) { 
    this.caughtExcercise = {...exercise};
    this.commentsOnProgram = null;
    this.commentText = "";
    this.isPostCommentDisabled = true;
    commentService.getCommentsForProgram(this.caughtExcercise.id).subscribe(
      response => {this.commentsOnProgram = (response);
                    console.log(response)});
  }


}
