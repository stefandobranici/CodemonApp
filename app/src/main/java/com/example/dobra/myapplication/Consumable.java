package com.example.dobra.myapplication;

public class Consumable {
    private String ImageURL;
    private Integer Cost;
    private String Name;
    private Integer Quantity;

    public Consumable() {
    }

    public Consumable(String imageURL, Integer cost, String name, Integer quantity) {
        Cost = cost;
        Name = name;
        Quantity = quantity;
        ImageURL = imageURL;
    }

    public Integer getCost() {
        return Cost;
    }

    public void setCost(Integer cost) {
        Cost = cost;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
