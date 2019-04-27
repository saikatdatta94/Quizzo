package com.example.saikat.quizzo;

public class FollowingCategoryItemClass {

    private String title;
    private String description;
    private int priority;

//    TODO : ********************Which items a user is following

    public FollowingCategoryItemClass() {
        //empty constructor needed
    }

    public FollowingCategoryItemClass(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}