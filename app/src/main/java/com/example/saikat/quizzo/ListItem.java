package com.example.saikat.quizzo;

public class ListItem {



//    private String imageURL;
    private String heading;
    private String descriptionText;
//    private String parentCategory;
    private int priority;



    public ListItem(){

    }




    public ListItem(String heading, String descriptionText, int priority) {
        this.heading = heading;
        this.descriptionText = descriptionText;
        this.priority = priority;
    }

//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

//    public String getParentCategory() {
//        return parentCategory;
//    }
//
//    public void setParentCategory(String parentCategory) {
//        this.parentCategory = parentCategory;
//    }

//    public ListType getType() {
//        return type;
//    }
//
//    public void setType(ListType type) {
//        this.type = type;
//    }
}
