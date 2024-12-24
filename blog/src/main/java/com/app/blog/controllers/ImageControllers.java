package com.app.blog.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.blog.authenticator.AuthRequest;
import com.app.blog.minio.MinioRequest;
import com.app.blog.models.Profile;
import com.app.blog.services.PostService;
import com.app.blog.tables.Posts;

@RestController
@RequestMapping("/images")
public class ImageControllers {


    private AuthRequest auth;


    private MinioRequest mr;

    private  PostService ps;
    @Value("${minio.bucket.name}")
    private String bucket;

    public ImageControllers( @Autowired PostService ps ,@Autowired AuthRequest auth , @Autowired MinioRequest mr ,@Value("${minio.bucket.name}") String bucket ) {
        this.auth = auth;
        this.mr = mr;
        this.ps = ps;
        this.bucket = bucket;
    }

    @PostMapping("/post/add/{post_id}")

    public ResponseEntity<String> addImage(@RequestHeader("Authorization") String token, @RequestParam("image") MultipartFile image, @PathVariable(value = "post_id") long post_id){
        {
            Profile profile = auth.getProfile(token);
            if (profile == null) {
                return ResponseEntity.badRequest().build();
            }
            if (image.isEmpty()) {
                return new ResponseEntity<>("Image cannot be empty", HttpStatus.BAD_REQUEST);
            }
            Posts post = ps.getPostById(post_id);
            if(post == null) {
                return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
            }
            String objname = "post/" + post.getId() + "/" + image.getOriginalFilename();
            InputStream inputStream = null;


            try {
                inputStream = image.getInputStream();
            } catch (IOException e) {
                System.out.println("IOException Exception : " + e);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            try {
                mr.uploadFile(bucket, objname, inputStream, image.getContentType());
                ps.updatePostImage(objname, post.getId());
                return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
            } catch (Exception e) {
                System.out.println("Exception In Image Controller: " + e);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }
    }
    @GetMapping("/post/{post_id}")
    public ResponseEntity<InputStreamResource> streamImage(@RequestHeader("Authorization") String token, @PathVariable(value = "post_id") long post_id) {
        try {
            Profile profile = auth.getProfile(token);
            if (profile == null) {
                return ResponseEntity.badRequest().build();
            }
            Posts post = ps.getPostById(post_id);
            if(post == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            if(post.getImage() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            InputStream inputStream = mr.downloadFile(bucket, post.getImage());
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type for your image

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .body(inputStreamResource);

        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage(), e);
        }
    
    }
    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<String> deleteImage(@RequestHeader("Authorization") String token, @PathVariable(value = "post_id") long post_id) {
        try {
            Profile profile = auth.getProfile(token);
            if (profile == null) {
                return ResponseEntity.badRequest().build();
            }
            Posts post = ps.getPostById(post_id);
            if(post == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            mr.deleteFile(bucket, post.getImage());
            ps.updatePostImage(null, post.getId());
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage(), e);
        }
    }
}