//Manufacturer.java
//Austin Trout

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private String manufacturer;
    private Vehicle[] veh = new Vehicle[0];

    public Manufacturer(String name, Vehicle v){
        //Made uppercase in case the user types in the name a weird way (Ex: JagUaR)
        manufacturer = name.toUpperCase();
        addVehicle(v);
    }

    public void addVehicle(Vehicle v){
        veh = resizeArray(veh, 1);
        veh[veh.length - 1] = v;
    }

    public int vehicleCount(){
        return veh.length;
    }

    public Vehicle[] getAllVehicles(){
        return veh;
    }

    public String getManufacturerName(){
        return manufacturer;
    }

    //Reallocates an array with a new size, and copies the contents of the old array to the new array
    private Vehicle[] resizeArray(Vehicle[] v, int size){
        Vehicle[] newArray = new Vehicle[v.length + size];

        for (int i = 0; i < v.length; i++){
            newArray[i] = v[i];
        }
        return newArray;
    }
}
