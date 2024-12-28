package com.app.blog.tables;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;


@Entity
@Component
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "content")
    private String content;

    @Column(name = "blog_id")
    private long blog_id;
    @Column(name = "image")
    private String image;
   
    @Column(name = "author_id")
    private String author_id;
    @Column(name = "author_name")
    private String author_name;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "updated_at")
    private Date updated_at;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post_id")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public Posts() {
    }

 

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getBlog_id() {
        return this.blog_id;
    }

    public void setBlog_id(long blog_id) {
        this.blog_id = blog_id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor_id() {
        return this.author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return this.author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Posts id(long id) {
        setId(id);
        return this;
    }

    public Posts name(String name) {
        setName(name);
        return this;
    }

    public Posts description(String description) {
        setDescription(description);
        return this;
    }

    public Posts content(String content) {
        setContent(content);
        return this;
    }

    public Posts blog_id(long blog_id) {
        setBlog_id(blog_id);
        return this;
    }

    public Posts image(String image) {
        setImage(image);
        return this;
    }

    public Posts author_id(String author_id) {
        setAuthor_id(author_id);
        return this;
    }

    public Posts author_name(String author_name) {
        setAuthor_name(author_name);
        return this;
    }

    public Posts created_at(Date created_at) {
        setCreated_at(created_at);
        return this;
    }

    public Posts updated_at(Date updated_at) {
        setUpdated_at(updated_at);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Posts)) {
            return false;
        }
        Posts posts = (Posts) o;
        return id == posts.id && Objects.equals(name, posts.name) && Objects.equals(description, posts.description) && Objects.equals(content, posts.content) && blog_id == posts.blog_id && Objects.equals(image, posts.image) && Objects.equals(author_id, posts.author_id) && Objects.equals(author_name, posts.author_name) && Objects.equals(created_at, posts.created_at) && Objects.equals(updated_at, posts.updated_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, content, blog_id, image, author_id, author_name, created_at, updated_at);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", content='" + getContent() + "'" +
            ", blog_id='" + getBlog_id() + "'" +
            ", image='" + getImage() + "'" +
            ", author_id='" + getAuthor_id() + "'" +
            ", author_name='" + getAuthor_name() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            "}";
    }
    

    
}
