package com.example.app.Models;

import java.time.LocalDateTime;

public class Notice {
    private int id;
    private String title;
    private String content;
    private int postedBy;
    private LocalDateTime postDate;
    private String targetRoles;
    private String targetDepartments;

    // Constructors
    public Notice() {}

    public Notice(int id, String title, String content, int postedBy,
                  LocalDateTime postDate, String targetRoles, String targetDepartments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postedBy = postedBy;
        this.postDate = postDate;
        this.targetRoles = targetRoles;
        this.targetDepartments = targetDepartments;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getPostedBy() { return postedBy; }
    public void setPostedBy(int postedBy) { this.postedBy = postedBy; }

    public LocalDateTime getPostDate() { return postDate; }
    public void setPostDate(LocalDateTime postDate) { this.postDate = postDate; }

    public String getTargetRoles() { return targetRoles; }
    public void setTargetRoles(String targetRoles) { this.targetRoles = targetRoles; }

    public String getTargetDepartments() { return targetDepartments; }
    public void setTargetDepartments(String targetDepartments) { this.targetDepartments = targetDepartments; }
}