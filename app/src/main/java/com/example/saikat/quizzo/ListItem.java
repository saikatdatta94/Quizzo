package com.example.saikat.quizzo;

public class ListItem {

    public enum ListType{
        ONE,TWO;
    }


    private ListType type;
    private String imageURL;
    private String heading;
    private String descriptionText;
    private String parentCategory;



    public ListItem(){

    }



    public ListItem(ListType type, String imageURL, String heading, String descriptionText, String parentCategory) {
        this.imageURL = imageURL;
        this.heading = heading;
        this.descriptionText = descriptionText;
        this.type = type;
        this.parentCategory = parentCategory;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

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

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public ListType getType() {
        return type;
    }

    public void setType(ListType type) {
        this.type = type;
    }
}
