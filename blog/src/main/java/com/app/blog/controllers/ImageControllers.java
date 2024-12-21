package com.app.blog.controllers;

import com.app.blog.authenticator.AuthRequest;
import com.app.blog.minio.MinioRequest;
import com.app.blog.models.Profile;
import com.app.blog.services.PostService;
import com.app.blog.tables.Posts;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/images")
public class ImageControllers {

    @Autowired
    private AuthRequest auth;

    @Autowired
    private MinioRequest mr;
    @Autowired
    private PostService ps;
    @Value("${minio.bucket.name}")
    private String bucket;

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
    public ResponseEntity<InputStreamResource> getImage(@RequestHeader("Authorization") String token,@RequestParam(value = "token", required = false) String token2, @PathVariable(value = "post_id") long post_id){

        if(token.isEmpty()){
            System.out.println(token2);
            token = token2;
        }
        Profile profile = auth.getProfile(token);
        if (profile == null) {
            return ResponseEntity.badRequest().build();
        }
        Posts post = ps.getPostById(post_id);
        if(post == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
       if(post.getImage().isEmpty()) {

           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }

        GetObjectResponse objres = null;
        try  {
            objres =  mr.downloadFile(bucket, post.getImage());
          InputStreamResource inputStreamResource = new InputStreamResource(objres);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust as needed
            headers.setContentLength(objres.available());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(inputStreamResource);

        } catch (Exception e) {
            System.out.println("Exception In Image Controller: " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
