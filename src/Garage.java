//Garage.java
//Austin Trout

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Garage extends JFrame implements ActionListener, ChangeListener {

    public static int vehicleCount = 0;
    public static int manufacturerCount = 1;
    public static int avgPrice = 2;
    public static int avgMileage = 3;
    public static int avgAge = 4;

    private String file;
    private boolean vehicleUpdated = false;
    private Vector registeredListeners = new Vector();
    private VehicleCollection vehicleCollection;
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel titlePanel = new JPanel(new GridLayout(2, 1));
    JLabel statusLabel = new JLabel();
    JLabel compLabel = new JLabel("O'Brien's Garage", JLabel.CENTER);
    JTabbedPane theTab = new JTabbedPane(JTabbedPane.LEFT);
    JMenuBar menuBar = new JMenuBar();
    WindowCloser closer = new WindowCloser();


    public Garage(String f){
        addWindowListener(closer);
        theTab.addChangeListener(this);

        setSize(700, 500);

        Container c = getContentPane();
        vehicleCollection = new VehicleCollection();

        file = f;

        try{
            vehicleCollection.loadVehicles(file);
        }catch (java.io.FileNotFoundException exp){
            System.out.println("The file 'vehicles.dat' does not exist. Please create an empty file named 'vehicles.dat'");
            System.exit(0);
        }catch (java.io.EOFException exp){
        }catch (java.io.IOException exp){
            System.out.println("The file 'vehicles.dat' is possibly corrupted. Please delete it and create a new empty data file named 'vehicles.dat'");
            System.exit(0);
        }catch (Exception exp){
            System.out.println("There was an error loading 'vehicles.dat'. Try deleting and creating a new empty file named 'vehicles.dat'");
            System.exit(0);
        }

        String currentFont = compLabel.getFont().getName();
        compLabel.setFont(new Font(currentFont, Font.BOLD, 26));

        setJMenuBar(menuBar);

        titlePanel.add(compLabel);
        topPanel.add(titlePanel, "Center");

        OverviewPanel overviewPanel = new OverviewPanel(this, f);
        AddVehicle addVehPanel = new AddVehicle(this);
        AllVehicles allVehPanel = new AllVehicles(this);
        SearchViaAge searchByAgePanel = new SearchViaAge(this);
        SearchViaPriceMileage searchByOtherPanel = new SearchViaPriceMileage(this);

        theTab.add("Overview", overviewPanel);
        theTab.add("Add a Vehicle", addVehPanel);
        theTab.add("Show All Vehicles", allVehPanel);
        theTab.add("Search by Age", searchByAgePanel);
        theTab.add("Search by Price and Mileage", searchByOtherPanel);

        theTab.addChangeListener(allVehPanel);
        theTab.addChangeListener(overviewPanel);

        theTab.setSelectedIndex(0);

        //c.setLayout(new BorderLayout());
        c.add(topPanel, "North");
        c.add(theTab, "Center");
        c.add(statusLabel, "South");
    }

    public void actionPerformed(ActionEvent e){}

    public void addVehicleUpdateListener(Object listener){
        if (listener != null)
            registeredListeners.add(listener);
    }

    public int addNewVehicle(Vehicle v){
        return vehicleCollection.addVehicle(v);
    }

    public void closing(){
        if (vehicleUpdated){
            try{
                vehicleCollection.saveVehicles(file);
            }catch (java.io.IOException exp){
                System.out.println("There was an error with saving the information");

            }
        }
        System.exit(0);
    }

    //Converts the option that was chosen for ranges and sets them accordingly for the max's and min's
    public static int[] convertToRange(String range){
        //Gets rid of any blank space before and after and splits around the "-" that might have been in for the ranges
        String[] parts = range.trim().split("-");
        int[] bounds = new int[2];

        try{
            //In the form of a single number or ending with a +
            if (parts.length == 1){
                String c = range.substring(range.length() - 1);

                //Range selection ended with "+"
                if (c.equals("+")){
                    //Minimum
                    bounds[0] = Integer.parseInt(range.substring(0, range.length() - 1));
                    //No limit
                    bounds[1] = -1;
                }
                // Range is a single number
                else{
                    //min == max.
                    bounds[0] = Integer.parseInt(range);
                    bounds[1] = bounds[0];
                }
            }
            //Has a range between two numbers
            else if(parts.length == 2){
                //Minimum
                bounds[0] = Integer.parseInt(parts[0]);
                //Maximum
                bounds[1] = Integer.parseInt(parts[1]);
            }
        //Catches if the format for the max/min is incorrect
        }catch (NumberFormatException exp){
            System.out.println("There is an issue with the range formatting.");
        }
        return bounds;
    }

    public Vehicle[] getAllVehicles(){
        return vehicleCollection.getAllVehicles();
    }


    public double getStatistics(int type){
        double result = 0;

        if (type == vehicleCount){
            result	= vehicleCollection.vehicleCount();
        }else if (type == manufacturerCount){
            result = vehicleCollection.manufacturerCount();
        }else if (type == avgPrice){
            result = vehicleCollection.getAvgPrice();
        }else if (type == avgMileage){
            result = vehicleCollection.getAvgMileage();
        }else if (type == avgAge){
            result = vehicleCollection.getAvgAge();
        }

        return result;
    }

    public static void main(String[] args){
        Garage obrien = new Garage("vehicles.dat");
        obrien.setVisible(true);
    }

    public Vehicle[] searchAge(int minAge, int maxAge){
        return vehicleCollection.searchAge(minAge, maxAge);
    }


    public Vehicle[] searchPriceMileage(int minPrice, int maxPrice, double minMileage, double maxMileage){
        return vehicleCollection.search(minPrice, maxPrice, (int) minMileage,(int) maxMileage);
    }

    public void setVehicleUpdated(){
        vehicleUpdated = true;

        for (int i = 0; i < registeredListeners.size(); i++){
            Class[] paramType = {VehicleUpdate.class};
            Object[] param = { new VehicleUpdate(this)};

            try{
                //get a reference to the method which we want to invoke to the listener
                java.lang.reflect.Method callingMethod = registeredListeners.get(i).getClass().getMethod("vehiclesUpdated", paramType);
                //invoke the method with our parameters
                callingMethod.invoke(registeredListeners.get(i), param);
            }catch (Exception exp){
                System.out.println("There was an error with updating the list.");

            }
        }
    }

    public void stateChanged(ChangeEvent e){ }

    public static Vehicle[] vectorToVehicle(Vector v){
        Vehicle[] veh = new Vehicle[v.size()];

        for (int i = 0; i < v.size(); i++){
            veh[i] = (Vehicle)v.elementAt(i);
        }

        return veh;
    }

    class WindowCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e){
            closing();
        }
    }
}