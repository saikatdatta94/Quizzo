package com.example.saikat.quizzo;

public class LeaderBoardItem {

    public LeaderBoardItem(){
//        Empty Constructor Required
    }

//    private String imageUrl;
//    private String name;
//    private int score;
//    private String category;
//    private String parent;
//    private String uid;

    private String userId;
    private String userName;
    private String photoURL;
    private int score;

    private String title;
    private String description;
    private int priority;

//    public LeaderBoardItem(String imageUrl, String name, int score, String category, String parent, String uid) {
//        this.imageUrl = imageUrl;
//        this.name = name;
//        this.score = score;
//        this.category = category;
//        this.parent = parent;
//        this.uid = uid;
//    }

    public LeaderBoardItem(String userId,String userName,String photoURL,int score) {


        this.userId = userId;
        this.userName = userName;
        this.photoURL = photoURL;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public int getScore() {
        return score;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public int getPriority() {
//        return priority;
//    }

    //    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//
//    public String getCategory() {
//        return category;
//    }
//
//    public String getParent() {
//        return parent;
//    }
//
//    public String getUid() {
//        return uid;
//    }
}
