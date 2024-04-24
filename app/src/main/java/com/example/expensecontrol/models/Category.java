package com.example.expensecontrol.models;

public class Category {


    private  int categoryColor;
    private String categoryName;
    private  int categoryImage;

    public Category() {

    }
    public Category(String categoryName, int categoryImage ,int categoryColor) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;

        this.categoryColor=categoryColor;
    }

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }
}
