package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.CommentEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.CommentRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {


    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentEntity addComment(CommentEntity newComment)
    {
        CommentEntity savedComment = null;
        try
        {
            newComment.setCommentTime(LocalDateTime.now());
            savedComment = commentRepository.save(newComment);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
        }

        return savedComment;
    }

    public CommentEntity updateComment(CommentEntity newComment)
    {
        return commentRepository.save(newComment);
    }

    public List<CommentEntity> findAllComments()
    {
        return commentRepository.findAll();
    }

    public List<CommentEntity> findCommentsForProgramId(Integer id)
    {
        return commentRepository.findAll().stream().
                                                    filter(comment -> comment.getTargetFitnessProgram().getId().equals(id)).
                                                    collect(Collectors.toList());
    }

    public List<CommentEntity> findCommentsMadeByUserId(Integer id)
    {
        return commentRepository.findAll().stream().
                filter(comment -> comment.getUser_commenter().getId().equals(id)).
                collect(Collectors.toList());
    }

    public void deleteComment(Integer id)
    {
        commentRepository.deleteById(id);
    }



}
