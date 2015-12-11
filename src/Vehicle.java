//Vehicle.java
//Austin Trout

import java.io.Serializable;
import java.util.*;

public class Vehicle implements Serializable {
    private String model;
    private String manufacturer;
    private String information;
    private int year;
    private int price;
    private double miles;

    public Vehicle(String vehMake, String mod, String info){
        model = mod.toUpperCase();
        manufacturer = vehMake.toUpperCase();
        information = info;
    }

    public int getAge(){
        GregorianCalendar calendar = new GregorianCalendar();
        return (calendar.get(Calendar.YEAR) - year);
    }

    public String getInformation(){
        return information;
    }

    public double getMiles(){
        return miles;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public String getModel(){
        return model;
    }

    public int getPrice(){
        return price;
    }

    public int getYear(){
        return year;
    }

    public void setMiles(double miles){
        this.miles = miles;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setYear(int year){
        this.year = year;
    }
}
