//AddVehiclePanel.java
//Austin Trout

import java.awt.event.*;
import javax.swing.*;

public class AddVehicle extends JPanel implements ActionListener{
    private Garage garage;

    JLabel headingLabel = new JLabel("Add a Vehicle");

    private JButton clearButton = new JButton("Clear");
    private JButton saveButton = new JButton("Save");

    JPanel buttonPanel = new JPanel();

    private VehicleDetails vehDetails = new VehicleDetails();

    public AddVehicle(Garage gar){
        garage = gar;

        clearButton.addActionListener(this);
        saveButton.addActionListener(this);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Spacing between "Add a Vehicle" and the top
        add(Box.createVerticalStrut(10));
        add(headingLabel);
        //Spacing between "Add a Vehicle" and the content below
        add(Box.createVerticalStrut(15));
        vehDetails.add(buttonPanel, "Center");
        add(vehDetails);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == clearButton)
            clearButton();
        else if (e.getSource() == saveButton)
            saveButton();
    }

    private void clearButton(){
        vehDetails.clearText();
    }

    private void saveButton(){
        String vehMake = "";
        String model = "";
        String info = "";
        int miles = 0;
        int price = 0;
        int year = 0;
        boolean isValid = false;

        try {
            //Retrieve all the values from the text field, and converts the string into an integer if needed
            //trim gets rid of any white space that might be added to the beginning/end
            vehMake = vehDetails.getManufacturerText().trim();
            model = vehDetails.getModelText().trim();
            info = vehDetails.getInfoText().trim();
            miles = Integer.parseInt(vehDetails.getMilesText().trim());
            price = Integer.parseInt(vehDetails.getPriceText().trim());
            year = Integer.parseInt(vehDetails.getYearText().trim());

            //Checks to make sure the year is in a plausible range
            //Extended it enough to where they can make up stuff that is futuristic if needed
            //Years that are past the current one will change how the average age will function
            if (year >= 1000 && year <= 5000) {
                isValid = true;
            } else {
                JOptionPane.showMessageDialog(garage, "There was an error with the input for Year.\nThis text field must be in the form XXXX", "Invalid field", JOptionPane.ERROR_MESSAGE);
            }

        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(garage, "An error has occurred with your input:\n" +
                    "Year must contain: four numeric digits only\n" +
                    "Price must contain: a valid integer with no decimal places\n" +
                    "Mileage must contain: a number which can have a maximum of one decimal place", "Invalid field", JOptionPane.ERROR_MESSAGE);
        }

        if (isValid){
            Vehicle myVehicle = new Vehicle(vehMake, model, info);

            myVehicle.setMiles(miles);
            myVehicle.setPrice(price);
            myVehicle.setYear(year);

            //Attempt to add the new vehicle to the system.
            int result = garage.addNewVehicle(myVehicle);

            //If the vehicle was added, you'll get a confirmation and the system will update
            if (result == VehicleCollection.noError){
                garage.setVehicleUpdated();
                JOptionPane.showMessageDialog(garage, "Vehicle Added", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                //Will clear the inputted text for the user so another vehicle can be easily added
                clearButton();
            }
        }
    }
}