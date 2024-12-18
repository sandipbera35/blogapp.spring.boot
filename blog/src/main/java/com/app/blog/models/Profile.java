package com.app.blog.models;

import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Profile {
    
    private String id;
    private String first_name;
    private String last_name;
    private String email_id;


    public Profile() {
    }

    public Profile(String id, String first_name, String last_name, String email_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return this.email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public Profile id(String id) {
        setId(id);
        return this;
    }

    public Profile first_name(String first_name) {
        setFirst_name(first_name);
        return this;
    }

    public Profile last_name(String last_name) {
        setLast_name(last_name);
        return this;
    }

    public Profile email_id(String email_id) {
        setEmail_id(email_id);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Profile)) {
            return false;
        }
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(first_name, profile.first_name) && Objects.equals(last_name, profile.last_name) && Objects.equals(email_id, profile.email_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, email_id);
    }

    public Profile getObject() {
        

        return this;

    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", first_name='" + getFirst_name() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", email_id='" + getEmail_id() + "'" +
            "}";
    }

}
