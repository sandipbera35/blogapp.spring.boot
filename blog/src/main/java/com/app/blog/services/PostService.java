package com.app.blog.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.blog.repo.PostRepo;
import com.app.blog.tables.Posts;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    public Posts createPost(Posts post) {

        post.created_at(new java.util.Date());
        post.updated_at(new java.util.Date());
        return postRepo.save(post);
    }
    @Transactional
    public void updatePost(Posts post, long id) {
        try {
           postRepo.updatePost(post.getName(), post.getDescription(), id);

        } catch (Exception e) {
            System.out.println("Exception : " + e);
            throw new InternalError(e);
        }

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

    @Transactional
   public void updatePostImage(String image, long id) {
        try {
            postRepo.updatePostImage(image, id);
        } catch (Exception e) {

            System.out.println("Exception in repo: " + e);
            throw new RuntimeException(e);

        }

   }
    public List<Posts> getAllPostByBlogId(long blog_id) {return postRepo.findAllByBlog_id(blog_id); }
    
}
