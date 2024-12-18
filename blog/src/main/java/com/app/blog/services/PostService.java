package com.app.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.blog.repo.PostRepo;
import com.app.blog.tables.Posts;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    public Posts createPost(Posts post) {
        return postRepo.save(post);
    }
    public Posts updatePost(Posts post) {
        return postRepo.save(post);
    }
    public void deletePost(long id) {
        try {
            postRepo.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public Posts getPostById(long id) {

        if (postRepo.findById(id).isPresent()){
            return postRepo.findById(id).get();
        }
        return null;

    }
    public Iterable<Posts> getAllPost() {
        return postRepo.findAll();
    }
    
}
