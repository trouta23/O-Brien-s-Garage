//AllVehicles.java
//Austin Trout

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class AllVehicles extends JPanel implements ActionListener, ChangeListener{
    private Garage garage;
    private Vehicle[] vehicleList;
    private int currentIndex = 0;

    JLabel headingLabel = new JLabel("Show all Vehicles");
    private JButton previousButton = new JButton("Previous");
    private JButton nextButton = new JButton("Next");

    JPanel buttonPanel = new JPanel();
    private VehicleDetails vehDetails = new VehicleDetails();
    private boolean vehicleUpdated = false;


    public AllVehicles(Garage gar){
        garage = gar;
        vehicleList = garage.getAllVehicles();

        gar.addVehicleUpdateListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        headingLabel.setAlignmentX(CENTER_ALIGNMENT);

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        add(Box.createVerticalStrut(10));
        add(headingLabel);
        add(Box.createVerticalStrut(15));
        vehDetails.add(buttonPanel, "Center");
        add(vehDetails);

        if (vehicleList.length > 0){
            vehDetails.displayDetails(vehicleList[0]);
        }

        //vehicleList = garage.getAllVehicles();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == previousButton){
            previousButton();
        }else if(e.getSource() == nextButton){
            nextButton();
        }
    }

    public void vehiclesUpdated(VehicleUpdate e){
        if (e.getSource() == garage){
            vehicleUpdated = true;
        }
    }

    //Updates the vehicle list so that all the correct vehicles are shown
    public void stateChanged(ChangeEvent e){
        //Tests to see if getSource is a subtype of JTabbedPane
        if (e.getSource() instanceof JTabbedPane){
            JTabbedPane tab = (JTabbedPane)e.getSource();
            //If selected panel is the AllVehicles
            if (tab.getSelectedIndex() == 2){
                if (vehicleUpdated){
                    vehicleList = garage.getAllVehicles();
                    if (vehicleList != null){
                        vehDetails.displayDetails(vehicleList[currentIndex]);
                    }
                    vehicleUpdated = false;
                }
            }
        }
    }

    //Shows next vehicle
    //If you can't go any further you get a notification
    private void nextButton(){
        if (currentIndex < vehicleList.length - 1){
            currentIndex++;
            vehDetails.displayDetails(vehicleList[currentIndex]);
        }
        else {
            JOptionPane.showMessageDialog(garage, "You can't go any further", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Shows previous vehicle
    //If you can't go any further you get a notification
    private void previousButton(){
        if (currentIndex > 0){
            currentIndex--;
            vehDetails.displayDetails(vehicleList[currentIndex]);
        }
        else {
            JOptionPane.showMessageDialog(garage, "You can't go any further", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
}