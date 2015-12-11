//MainPanel.java
//Austin Trout

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class OverviewPanel extends JPanel implements ChangeListener{

    private Garage garage;
    private JLabel vehicleLabel = new JLabel();
    private JLabel manufacturersLabel = new JLabel();
    private JLabel avgPriceLabel = new JLabel();
    private JLabel avgMiLabel = new JLabel();
    private JLabel avgAgeLabel = new JLabel();

    JPanel overviewPanel = new JPanel();
    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

    private boolean vehicleUpdated = false;
    private String file;

    public OverviewPanel(Garage gar, String data){
        garage = gar;
        file = data;
        setLayout(new BorderLayout(0, 10));
        gar.addVehicleUpdateListener(this);

        //Stacks overview titles on top of one another on the Y-Axis
        overviewPanel.setLayout(new BoxLayout(overviewPanel, BoxLayout.Y_AXIS));
        centerPanel.add(overviewPanel);

        updateStats();

        overviewPanel.add(vehicleLabel);
        overviewPanel.add(manufacturersLabel);
        overviewPanel.add(avgPriceLabel);
        overviewPanel.add(avgMiLabel);
        overviewPanel.add(avgAgeLabel);
        overviewPanel.add(Box.createVerticalStrut(20));

        add(centerPanel, "Center");
    }

    public void vehiclesUpdated (VehicleUpdate e){
        if (e.getSource() == garage){
            vehicleUpdated = true;
        }
    }

    //If a vehicle gets added, will update the information on the Main page
    public void stateChanged(ChangeEvent e){
        //Tests to see if getSource is a subtype of JTabbedPane
        if (e.getSource() instanceof JTabbedPane){
            JTabbedPane tab = (JTabbedPane)e.getSource();
            //If selected panel is the OverviewPanel
            if (tab.getSelectedIndex() == 0){
                //If the stats are not up to date
                if (vehicleUpdated){
                    updateStats();
                    //Won't update the stats  unless a vehicle is added to the system
                    vehicleUpdated = false;
                }
            }
        }
    }

    //Updates all the data regarding the vehicles
    private void updateStats(){
        int veh = (int)garage.getStatistics(Garage.vehicleCount);
        int vehMakes = (int)garage.getStatistics(Garage.manufacturerCount);
        double avgPrice = Math.round(garage.getStatistics(Garage.avgPrice)*100d)/100d;
        double avgMi = Math.round(garage.getStatistics(Garage.avgMileage)*100d)/100d;
        double avgAge = Math.round(garage.getStatistics(Garage.avgAge)*100d)/100d;
        java.io.File f = new java.io.File(file);

        vehicleLabel.setText("Total Number of Vehicles: " + String.valueOf(veh));
        manufacturersLabel.setText("Total Number of Manufacturers: " + String.valueOf(vehMakes));
        avgPriceLabel.setText("Average Price per Vehicle: $" + String.valueOf(avgPrice));
        avgMiLabel.setText("Average Mileage per Vehicle: " + String.valueOf(avgMi) + " miles");
        avgAgeLabel.setText("Average Age per Vehicle: " + String.valueOf(avgAge) + " yeas old");
    }
}