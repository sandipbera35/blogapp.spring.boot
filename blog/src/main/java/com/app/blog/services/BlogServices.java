package com.app.blog.services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.blog.repo.BlogRepo;
import com.app.blog.tables.Blog;

@Service
public class BlogServices {

    @Autowired
    private BlogRepo blogRepo;

    public List<Blog> getAllBlog() {

        return blogRepo.findAll();
    }
    public  Optional<Blog> getBlogById(long id ){

        return blogRepo.findById(id);
    }
    public Blog createBlog(Blog blog){
        
        blog.setCreated_at(Date.from(Instant.now()));
        blog.setUpdated_at(Date.from(Instant.now()));
        return blogRepo.save(blog);
    }
    public Blog updateBlog(Blog blog) {
        
        return blogRepo.save(blog);
    }
    public List<Blog> getAllBlogByauthorId(String author_id) {


        return blogRepo.getAllBlogByAuthorId(author_id);
    }

    public int  updateBlogById(String name, String description, long id) {

        return blogRepo.updateBlog(id, name, description);
    }


}
