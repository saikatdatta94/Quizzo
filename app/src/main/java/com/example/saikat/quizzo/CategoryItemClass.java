package com.example.saikat.quizzo;

public class CategoryItemClass {

    private String title;
    private String description;
    private int priority;
    private String parentCategory;
    private int followers;

//    TODO : ********************Which items a user is following

    public CategoryItemClass() {
        //empty constructor needed
    }



    public CategoryItemClass(String title, String description, int priority, String parentCategory, int followers) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.parentCategory = parentCategory;
        this.followers = followers;
    }

    public int getFollowers() {
        return followers;
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

    public String getParentCategory() {
        return parentCategory;
    }
}