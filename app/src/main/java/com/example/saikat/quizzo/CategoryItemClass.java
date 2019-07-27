package com.example.saikat.quizzo;

public class CategoryItemClass {

    private String title;
    private String description;
    private int priority;
    private String parentCategory;
    private int followers;
    private String photoURL;
    private String isProduction;
    private String color;

//    TODO : ********************Which items a user is following

    public CategoryItemClass() {
        //empty constructor needed
    }




    public CategoryItemClass(String title, String description, int priority, String parentCategory, int followers, String photoURL, String isProduction,String color) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.parentCategory = parentCategory;
        this.followers = followers;
        this.photoURL = photoURL;
        this.isProduction = isProduction;
        this.color = color;
    }

    public int getFollowers() {
        return followers;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getIsProduction() {
        return isProduction;
    }

    public int getPriority() {
        return priority;
    }

    public String getParentCategory() {
        return parentCategory;
    }
}