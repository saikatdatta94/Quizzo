package com.example.saikat.quizzo;

public class FollowingCategoryItemClass {
    private String title;
    private String description;
    private int priority;
    private String isFollowing;
    private int highScore;
    private String parent;
    private int xp;
    private int level;
    private String photoURL;
    private String color;

//    TODO : ********************Which items a user is following

    public FollowingCategoryItemClass() {
        //empty constructor needed
    }


    public String getParent() {
        return parent;
    }

    public FollowingCategoryItemClass(String title, String description, int priority, String isFollowing,
                                      int highScore, String parent,int level,int xp,String photoURL,String color) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.isFollowing = isFollowing;
        this.highScore = highScore;
        this.parent = parent;
        this.level = level;
        this.xp = xp;
        this.photoURL = photoURL;
        this.color = color;
    }

    public String getPhotoURL() {
        return photoURL;
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


    public int getPriority() {
        return priority;
    }

    public String getIsFollowing() {
        return isFollowing;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

}
