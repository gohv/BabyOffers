package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgy on 7/27/2016.
 */
public class Offer implements Serializable
{
    public static List<Offer> offers = load();



    private double oldPrice;
    private double newPrice;
    private String shopName;
    private String productPhoto;
    private String productName;
    private String category;
    private String brand;
    private String linkToItem;
    private static int counter;

    @Override
    public String toString() {
        return "Offer" + '{' +
                "oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                ", shopName='" + shopName + '\'' +
                ", productPhoto='" + productPhoto + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", linkToItem='" + linkToItem + '\'' +
                '}';
    }

    public static List<Offer> getOffers() {
        return offers;
    }

    public static void setOffers(List<Offer> offers) {
        Offer.offers = offers;
    }

    public String getLinkToItem() {
        return linkToItem;
    }

    public void setLinkToItem(String linkToItem) {
        counter++;
        this.linkToItem = linkToItem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }



    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public static void save(){
        try{
            FileOutputStream fileStream = new FileOutputStream("file/offers.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileStream);
            out.writeObject(offers);
            out.close();
            fileStream.close();
        }
        catch (Exception e){
                e.printStackTrace();
        }

    }

    private static List<Offer> load() {

        List<Offer> tempOffers = new ArrayList<>();

        try
        {
            FileInputStream fileInputStream = new FileInputStream("file/offers.txt");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            tempOffers =(List<Offer>) inputStream.readObject();

            inputStream.close();
            fileInputStream.close();

        }
        catch (FileNotFoundException e){ e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ClassNotFoundException e){e.printStackTrace();}

        return tempOffers;
    }

    public Offer(){

    }

    public static Integer getCounter(){
        return counter;
    }

}
