package com.app.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.tables.Posts;

public interface  PostRepo extends JpaRepository<Posts, Long> {
    
}
