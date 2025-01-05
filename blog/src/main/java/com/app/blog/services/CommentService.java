package com.app.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.blog.repo.CommentRepo;
import com.app.blog.tables.Comment;

@Service
public class CommentService {

    @Autowired
    private CommentRepo cr;

    public Comment createComment(Comment comment) {

        try{
        Comment  commentReturn  = cr.save(comment);

        if (commentReturn != null) {
            throw  new Exception("Comment creation failed");
        } else {
            return commentReturn;
        }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
