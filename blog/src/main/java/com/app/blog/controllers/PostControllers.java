package com.app.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.services.PostService;
import com.app.blog.tables.Posts;


@RestController
@RequestMapping("/posts")
public class PostControllers {

    @Autowired
    private PostService postService;
    
    @PostMapping("/add")
    public ResponseEntity<Posts>  createPost(@RequestBody Posts entity) {
        //TODO: process POST request

        System.out.println(entity);
        Posts post = postService.createPost(entity);

        if(post == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(post);
    }
    
}
