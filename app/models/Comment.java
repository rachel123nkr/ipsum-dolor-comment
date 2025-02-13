package models;

import com.fasterxml.jackson.databind.JsonNode;

public class Comment {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;

    public Comment(JsonNode comm) {
        this.postId = comm.get("postId").asInt();
        this.id = comm.get("id").asInt();
        this.name = comm.get("name").asText();
        this.email = comm.get("email").asText();
        this.body =  comm.get("body").asText();
    }

    public Comment(Integer postId, Integer id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "postId=" + postId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }


}
