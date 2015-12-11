//VehicleCollection.java
//Austin Trout

import java.util.*;
import java.io.*;

public class VehicleCollection{

    public static int noError = 0;

    public static int vehMaxReached = 1;
    public static int manuMaxReached = 2;

    int maxManufacturers = 50;
    int maxVehicles = 50;

    private Manufacturer[] vehMake = new Manufacturer[0];

    public VehicleCollection(){}

    public int addVehicle(Vehicle v){
        Manufacturer manu;
        String name = v.getManufacturer();
        int index = -1;
        int result = noError;

        for(int i = 0; i < vehMake.length; i++){
            //If manufacturer is already in the system
            if (vehMake[i].getManufacturerName().equalsIgnoreCase(name)) {
                index = i;
            }
        }

        //If manufacturer doesn't exist
        //Checks to see if the manufacturer max has been reached
        if(index == -1){
            if (vehMake.length < maxManufacturers){
                manu = new Manufacturer(name, v);
                addManufacturer(manu);
            }else {
                result = manuMaxReached;
            }

        //Checks to see if the vehicle max has been reached
        }else{
            if(vehMake[index].vehicleCount() < maxVehicles){
                vehMake[index].addVehicle(v);
            }else{
                result = vehMaxReached;
            }
        }
        return result;
    }

    private void addManufacturer(Manufacturer manu){
        vehMake = resizeArray(vehMake, 1);
        vehMake[vehMake.length - 1] = manu;
    }

    public int vehicleCount(){
        int count = 0;

        for(int i = 0; i < vehMake.length; i++){
            count += vehMake[i].vehicleCount();
        }
        return count;
    }

    public int manufacturerCount(){
        return vehMake.length;
    }

    public Vehicle[] getAllVehicles(){
        Vector result = new Vector();
        Vehicle[] veh;

        for(int i = 0; i < vehMake.length; i++){
            veh = vehMake[i].getAllVehicles();
            for (int j = 0; j < veh.length; j++){
                result.addElement(veh[j]);
            }
        }
        return Garage.vectorToVehicle(result);
    }

    public double getAvgAge(){
        Vehicle[] veh;
        int result = 0;
        int count = 0;

        for (int i = 0; i < vehMake.length; i++){
            veh = vehMake[i].getAllVehicles();
            for (int j = 0; j < veh.length; j++){
                result += veh[j].getAge();
                count++;
            }
        }
        //If there's nothing there then the age will default to zero
        if (count == 0) {
            return 0;
        }else {
            return (result / count);
        }
    }

    public double getAvgMileage(){
        Vehicle[] veh;
        double result = 0;
        int count = 0;

        for (int i = 0; i < vehMake.length; i++){
            veh = vehMake[i].getAllVehicles();
            for (int j = 0; j < veh.length; j++){
                result += veh[j].getMiles();
                count++;
            }
        }
        //If there's nothing there then the age will default to zero
        if (count == 0) {
            return 0;
        }else {
            return (result / count);
        }
    }

    public double getAvgPrice(){
        Vehicle[] veh;
        double result = 0;
        int count = 0;

        for(int i = 0; i < vehMake.length; i++){
            veh = vehMake[i].getAllVehicles();
            for (int j = 0; j < veh.length; j++){
                result += veh[j].getPrice();
                count++;
            }
        }
        //If there's nothing there then the age will default to zero
        if (count == 0) {
            return 0;
        }
        //Otherwise you'll get the average age
        else {
            return (result / count);
        }
    }

    public void loadVehicles(String file) throws IOException, ClassNotFoundException{
        ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file));
        vehMake = (Manufacturer[])inp.readObject();
        inp.close();
    }

    //Reallocates an array with a new size, and copies the contents of the old array to the new array
    private Manufacturer[] resizeArray(Manufacturer[] m, int size){
        Manufacturer[] newArray = new Manufacturer[m.length + size];

        for(int i = 0; i < m.length; i++){
            newArray[i] = m[i];
        }
        return newArray;
    }


    public void saveVehicles(String file) throws IOException{
        int counter;
        int vehMakeNumber = vehMake.length;
        Manufacturer temp;

        if (vehMake.length > 0){
            do{
                counter = 0;
                for (int i = 0; i < vehMakeNumber; i++){
                    if (i + 1 < vehMakeNumber){
                        if (vehMake[i].getManufacturerName().compareTo(vehMake[i + 1].getManufacturerName()) > 0){
                            temp = vehMake[i];
                            vehMake[i] = vehMake[i + 1];
                            vehMake[i + 1] = temp;
                            counter++;
                        }
                    }
                }
            }while (counter > 0);

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

            out.writeObject(vehMake);
            out.close();
        }
    }

    public Vehicle[] search(int minPrice, int maxPrice, double minMileage, double maxMileage){
        Vector result = new Vector();
        int price;
        double mileage;
        Vehicle[] veh;
        veh = getAllVehicles();

        for(int i = 0; i < veh.length; i++) {
            price = veh[i].getPrice();
            mileage = veh[i].getMiles();

            if (price >= minPrice && price <= maxPrice) {
                if (mileage >= minMileage && mileage <= maxMileage) {
                    result.add(veh[i]);
                }
            }
        }
        return Garage.vectorToVehicle(result);
    }

    //
    public Vehicle[] searchAge(int minAge, int maxAge){
        Vehicle[] veh;
        veh = getAllVehicles();
        Vector result = new Vector();

        //If the maxAge is infinite
        if(maxAge == -1){
            for(int i = 0; i < veh.length; i++){
                if(veh[i].getAge() >= minAge){
                    result.addElement(veh[i]);
                }
            }
        }else{
            for(int i = 0; i < veh.length; i++){
                if(veh[i].getAge() >= minAge && veh[i].getAge() <= maxAge){
                    result.addElement(veh[i]);
                }
            }
        }
        return Garage.vectorToVehicle(result);
    }
}

