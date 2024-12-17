package com.app.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.blog.tables.Blog;

import jakarta.transaction.Transactional;

public interface BlogRepo extends JpaRepository<Blog, Long> {

    @Query("select b from Blog b where b.author_id = ?1 ")

    List<Blog> getAllBlogByAuthorId(String author_id);

    @Modifying
    @Transactional
    @Query("UPDATE Blog b SET b.name = :name, b.description = :description , b.updated_at = now() WHERE b.id = :id")
    int updateBlog(@Param("id") Long id, @Param("name") String name, @Param("description") String description);

}
