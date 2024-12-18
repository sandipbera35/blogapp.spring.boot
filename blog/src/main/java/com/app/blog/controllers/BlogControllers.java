package com.app.blog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.authenticator.AuthRequest;
import com.app.blog.models.Profile;
import com.app.blog.services.BlogServices;
import com.app.blog.tables.Blog;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blogs")
public class BlogControllers {

    @Autowired
    private BlogServices bs;

    @Autowired
    private AuthRequest auth;

    @GetMapping("/all")
    public ResponseEntity<List<Blog>> getAllBlog(HttpServletRequest request) {

        List<Blog> blg = null;
        String token = request.getHeader("Authorization");
        System.out.println("token data : " + token);
        if (token.length() == 0) {
            return new ResponseEntity<>(blg, HttpStatus.valueOf(401));
        }
        if (auth.verifyToken(token)) {
            return new ResponseEntity<>(blg, HttpStatus.valueOf(401));
        }

        blg = bs.getAllBlog();

        return new ResponseEntity<>(blg, HttpStatus.valueOf(200));

    }

    @GetMapping("/all/owned")
    public ResponseEntity<List<Blog>> getAllBlogOwned(HttpServletRequest request) {

        List<Blog> blg = null;
        String token = request.getHeader("Authorization");
        System.out.println("token data : " + token);
        if (token.length() == 0) {
            return new ResponseEntity<>(blg, HttpStatus.valueOf(401));
        }
        if (auth.verifyToken(token.toString())) {
            return new ResponseEntity<>(blg, HttpStatus.valueOf(401));
        }

        blg = bs.getAllBlogByauthorId(auth.getProfile(token).getId());

        return new ResponseEntity<>(blg, HttpStatus.valueOf(200));

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Blog>> geBlogById(@PathVariable long id) {
        try {
            Optional<Blog> blg = bs.getBlogById(id);
            if (blg.isEmpty()) {
                return new ResponseEntity<>(blg, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(blg, HttpStatus.valueOf(200));
        } catch (Exception e) {
            System.out.println("Error creating blog" + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @SuppressWarnings("null")
    @PostMapping("/add")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, HttpServletRequest request) {

        try {

            String token = request.getHeader("Authorization");
            System.out.println("token data : " + token);
            if (token.length() == 0) {
                return new ResponseEntity<>(null, HttpStatus.valueOf(401));
            }
            if (auth.verifyToken(token)) {
                return new ResponseEntity<>(null, HttpStatus.valueOf(401));
            }
            Profile user = auth.getProfile(token);
            if (user == null) {
                return new ResponseEntity<>(null, HttpStatus.valueOf(401));
            }
            blog.setAuthor_id(user.getId());
            blog.setAuthor_name(user.getFirst_name() + " " + user.getLast_name());
            System.out.println("Request Blog: {}" + blog.toString());
            Blog blogReturn = bs.createBlog(blog);
            System.out.println("Blog Created: {}" + blogReturn.toString());
            return new ResponseEntity<>(blogReturn, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error creating blog" + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @SuppressWarnings("null")
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateBlog(@RequestBody Blog blog, HttpServletRequest request, @PathVariable long id) {

        try {

            String token = request.getHeader("Authorization");
            System.out.println("token data : " + token);
            if (token.length() == 0) {
                return new ResponseEntity<>(null, HttpStatus.valueOf(401));
            }
            if (auth.verifyToken(token)) {
                return new ResponseEntity<>(null, HttpStatus.valueOf(401));
            }
            Profile user = auth.getProfile(token);
            if (user == null) {
                return new ResponseEntity<>(null, HttpStatus.valueOf(401));
            }

            blog.setAuthor_id(user.getId());
            blog.setAuthor_name(user.getFirst_name() + " " + user.getLast_name());
            Optional<Blog> blogreturn = bs.getBlogById(id);
            if (blogreturn.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            if (bs.updateBlogById(blog.getName(), blog.getDescription(), id) > 0) {
         
                
               return new ResponseEntity<>("Blog Updated", HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);    
            }

        } catch (Exception e) {
            System.out.println("Error creating blog" + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
