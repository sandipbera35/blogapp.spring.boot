package com.app.blog.repo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.tables.Posts;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  PostRepo extends JpaRepository<Posts, Long> {

     @Query("select p from Posts p where p.blog_id = ?1")
     List <Posts> findAllByBlog_id(long blog_id);

     @Modifying
     @Query("UPDATE Posts p set p.name = :name, p.description = :description, p.updated_at = now() WHERE p.id = :id")
     void updatePost(@Param("name") String name,@Param("description") String description, @Param("id") long id);;

     @Modifying
     @Query("UPDATE Posts p set p.image = :image, p.updated_at = now() WHERE p.id = :id")
     void updatePostImage(@Param("image") String image, @Param("id") long id);;
}
