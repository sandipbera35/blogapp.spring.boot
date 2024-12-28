package com.app.blog.tables;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, name = "comment")
    private String comment;
    @Column(name = "post_id")
    private long post_id;
    @Column(name = "author_id")
    private String author_id;
    @Column(name = "author_name")
    private String author_name;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "updated_at")
    private Date updated_at;
    @Column(name = "comment_id")
    private int comment_id;

    public Comment() {

    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment_id")
    private List<Comment> reply;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public long getPostId() {
        return post_id;
    }

    public void setPostId(int postId) {
        this.post_id = postId;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", postId=" + post_id +
                ", author_id='" + author_id + '\'' +
                ", author_name='" + author_name + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
