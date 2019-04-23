package com.example.ss.HomePackage;

import java.util.ArrayList;

public class ProductModel {

    private ArrayList<String>imagesLinks;
    private String ProductName,prodcutPrice,productDescribtion,color,category
            ,priceRange,uId,productId,productLikes;

    public void setProductLikes(String productLikes) {
        this.productLikes = productLikes;
    }

    public void setImagesLinks(ArrayList<String> imagesLinks) {
        this.imagesLinks = imagesLinks;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public void setProdcutPrice(String prodcutPrice) {
        this.prodcutPrice = prodcutPrice;
    }

    public void setProductDescribtion(String productDescribtion) {
        this.productDescribtion = productDescribtion;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ArrayList<String> getImagesLinks() {
        return imagesLinks;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public String getProdcutPrice() {
        return prodcutPrice;
    }

    public String getProductDescribtion() {
        return productDescribtion;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getuId() {
        return uId;
    }

    public String getProductLikes() {
        return productLikes;
    }
}
