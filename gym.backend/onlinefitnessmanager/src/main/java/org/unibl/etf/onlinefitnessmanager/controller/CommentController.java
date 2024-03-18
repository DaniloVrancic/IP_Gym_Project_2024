package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.model.entities.CommentEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.service.CommentService;

import javax.xml.stream.events.Comment;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {


    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/find_all")
    public ResponseEntity<?> findAllComments()
    {
        List<CommentEntity> allComments = commentService.findAllComments();

        if(!allComments.isEmpty())
        {
            return new ResponseEntity<>(allComments, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("no comments found",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/find_for_program/{id}")
    public ResponseEntity<?> findAllCommentsForProgram(@PathVariable("id") Integer program_id)
    {
        List<CommentEntity> listOfComments = commentService.findCommentsForProgramId(program_id);

        if(!listOfComments.isEmpty())
        {
            return new ResponseEntity<>(listOfComments, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Program has no comments.",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/find_for_user/{id}")
    public ResponseEntity<?> findAllCommentsFromUser(@PathVariable("id") Integer user_id)
    {
        List<CommentEntity> listOfComments = commentService.findCommentsMadeByUserId(user_id);

        if(!listOfComments.isEmpty())
        {
            return new ResponseEntity<>(listOfComments, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Program has no comments.",HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Integer commentId)
    {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>("Comment has been deleted", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createComment(@RequestParam("user_id") Integer user_id, @RequestParam("program_id") Integer program_id, @RequestBody String comment)
    {
        CommentEntity newComment = new CommentEntity();
        try
        {


        newComment.setComment(comment);
        newComment.setUser_commenter((new UserEntity()));
        newComment.getUser_commenter().setId(user_id);
        newComment.setTargetFitnessProgram(new FitnessProgramEntity());
        newComment.getTargetFitnessProgram().setId(program_id);

        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("An error occured: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
