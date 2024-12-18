package com.app.blog.controllers;

import com.app.blog.authenticator.AuthRequest;
import com.app.blog.models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.blog.services.PostService;
import com.app.blog.tables.Posts;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostControllers {

    @Autowired
    private PostService postService;
    @Autowired
    private AuthRequest auth;
    
    @PostMapping("/add/blogid/{id}")
    public ResponseEntity<Posts>  createPost(@RequestHeader("Authorization") String token,@PathVariable long id, @RequestBody Posts entity) {
        //TODO: process POST request
        Profile profile = auth.getProfile(token);
        System.out.println("Profile : " + profile);
        if(profile == null) {
            return  ResponseEntity.badRequest().build();

        }
        System.out.println(entity);
        entity.setBlog_id(id);
        Posts post = postService.createPost(entity);

        if(post == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(post);
    }
    @GetMapping("/list")
    public ResponseEntity<Iterable<Posts>> getAllPost(@RequestHeader("Authorization") String token){
        Profile profile = auth.getProfile(token);
        if(profile == null) {
            return  ResponseEntity.badRequest().build();

        }
       Iterable<Posts> posts =  postService.getAllPost();

        return  ResponseEntity.ok(posts);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Posts> getById(@PathVariable long id,@RequestHeader("Authorization") String token){
        Profile profile = auth.getProfile(token);
        if(profile == null) {
            return  ResponseEntity.badRequest().build();
        }
       Posts post =  postService.getPostById(id);
       if (post == null) {

           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
       return ResponseEntity.ok(post);

    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id,@RequestHeader("Authorization") String token){
        Profile profile = auth.getProfile(token);
        if(profile == null) {
            return  ResponseEntity.badRequest().build();
        }
        Posts post =  postService.getPostById(id);
        if (post == null) {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        try {
            postService.deletePost(id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Post deleted successfully");

    }
}
