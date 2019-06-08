package com.example.ss.HomeFragmentV2Package;

import com.example.ss.ProductSort;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductModel {

    private ArrayList<String> imagesLinks;
    private HashMap<String, String> imageLinksWithKeys;
    private String ProductName, prodcutPrice, productDescribtion, color,
            category, priceRange, uId, productId, productLikes, numOfFollowers, numOfFollowing, productNumber, proudctDate;
    private int productNumberAsInt;


 public void setImageLinksWithKeys(HashMap<String, String> imageLinksWithKeys) {
        this.imageLinksWithKeys = imageLinksWithKeys;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public void setProductNumberAsInt(int productNumberAsInt) {
        this.productNumberAsInt = productNumberAsInt;
    }

    public void setProudctDate(String proudctDate) {
        this.proudctDate = proudctDate;
    }

    public int getProductNumberAsInt() {
        return productNumberAsInt;
    }

    public String getNumOfFollowers() {
        return numOfFollowers;
    }

    public String getNumOfFollowing() {
        return numOfFollowing;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public String getProudctDate() {
        return proudctDate;
    }


    public HashMap<String, String> getImageLinksWithKeys() {
        return imageLinksWithKeys;
    }

    public void setNumOfFollowers(String numOfFollowers) {
        this.numOfFollowers = numOfFollowers;
    }

    public void setNumOfFollowing(String numOfFollowing) {
        this.numOfFollowing = numOfFollowing;
    }

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
