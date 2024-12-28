package com.app.blog.services;

import com.app.blog.repo.CommentRepo;
import com.app.blog.tables.Comment;
import kotlin.jvm.Throws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentService cs;

    public Comment createComment(Comment comment) {

        try{
        Comment  commentReturn  = cs.createComment(comment);

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
