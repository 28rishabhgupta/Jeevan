package com.example.jeevan;

public class Home_Screen_Activity_Data_Complaints {

    private String itemName;
    private String itemProblemDescription;
    private String itemLocation;
    private int itemImage;
    private String City;

    public Home_Screen_Activity_Data_Complaints(String itemName, String itemProblemDescription, String itemLocation, int itemImage,String City) {
        this.itemName = itemName;
        this.itemProblemDescription = itemProblemDescription;
        this.itemLocation = itemLocation;
        this.itemImage = itemImage;
        this.City = City;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemProblemDescription() {
        return itemProblemDescription;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public int getItemImage() {
        return itemImage;
    }
    public String getCity() {
        return City;
    }
}
