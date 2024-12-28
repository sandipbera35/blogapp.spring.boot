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

        Profile profile = auth.getProfile(token);
        System.out.println("Profile : " + profile);
        if(profile == null) {
            return  ResponseEntity.badRequest().build();

        }
        System.out.println(entity);
        entity.setBlog_id(id);
        entity.setAuthor_id(profile.getId());
        entity.setAuthor_name(profile.getFirst_name() + " " +  profile.getLast_name());
        entity.setCreated_at(new java.util.Date());
        entity.setUpdated_at(new java.util.Date());
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
        var profile = auth.getProfile(token);
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
    @PatchMapping("/update/{id}")
    public  ResponseEntity<String> updatePost(@RequestHeader("Authorization") String token,@PathVariable long id, @RequestBody Posts entity) {

        try{
            Profile profile = auth.getProfile(token);
            if(profile == null) {
                return new  ResponseEntity<>(null, HttpStatus.valueOf(HttpStatus.UNAUTHORIZED.value()));
            }

            Posts post = postService.getPostById(id);
            if (post == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
//            System.out.println("entity : " + entity);


            postService.updatePost(entity, id);

           return new ResponseEntity<>("PostUpdated successfully", HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Exception In Update Controller: " + e);
            return  new ResponseEntity<>(null, HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

    }

    @GetMapping("/list/blogid/{id}")
    public ResponseEntity<List<Posts>> getAllPostByBlogId(@RequestHeader("Authorization") String token,@PathVariable long id){
        Profile profile = auth.getProfile(token);
        if(profile == null) {
            return  ResponseEntity.badRequest().build();

        }
        try {
            List<Posts> posts =  postService.getAllPostByBlogId(id);
            return  ResponseEntity.ok(posts);
        }
        catch (Exception e) {
            System.out.println("Exception : " + e);
            return  new ResponseEntity<>(null, HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }




    }
}
