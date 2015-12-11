//SearchViaAge.java
//Austin Trout

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SearchViaAge extends JPanel implements ActionListener{
    private String[] yearsOld = {"0", "1", "2-5", "6-10", "11-20", "21-40", "40+"};
    private Vehicle[] vehicleList;
    private Garage garage;
    private int currentIndex = 0;

    JLabel headingLabel = new JLabel("Search by Age");
    JLabel yearsOldLabel = new JLabel("Vehicle Age");

    private JButton searchButton = new JButton("Search");
    private JButton resetButton = new JButton("Reset");
    private JButton previousButton = new JButton("Previous");
    private JButton nextButton = new JButton("Next");
    private JComboBox yearsOldCombo = new JComboBox(yearsOld);

    JPanel topPanel = new JPanel();
    JPanel yearsOldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel searchButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel navigateButtonsPanel = new JPanel();
    private VehicleDetails vehDetails = new VehicleDetails();


    public SearchViaAge(Garage gar){
        garage = gar;
        setLayout(new BorderLayout());
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        resetButton.addActionListener(this);
        searchButton.addActionListener(this);
        yearsOldPanel.add(yearsOldLabel);
        yearsOldPanel.add(yearsOldCombo);
        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(resetButton);
        navigateButtonsPanel.add(previousButton);
        navigateButtonsPanel.add(nextButton);

        yearsOldPanel.setBorder(new javax.swing.border.EmptyBorder(new Insets(0, 5, 0, 0)));
        searchButtonsPanel.setBorder(new javax.swing.border.EmptyBorder(new Insets(0, 5, 0, 0)));

        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(headingLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(yearsOldPanel);
        topPanel.add(searchButtonsPanel);
        topPanel.add(Box.createVerticalStrut(15));
        vehDetails.add(navigateButtonsPanel, "Center");
        vehDetails.setVisible(false);

        add(topPanel, "North");
        add(vehDetails, "Center");
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == searchButton){
            searchButton();
        }else if (e.getSource() == previousButton){
            previousButton();
        }else if (e.getSource() == nextButton){
            nextButton();
        }else if (e.getSource() == resetButton){
            resetButton();
        }
    }

    //Will display the next frame if it exists
    //Will get a notification if you can't go any further
    private void nextButton(){
        if (currentIndex < vehicleList.length - 1){
            currentIndex++;
            vehDetails.displayDetails(vehicleList[currentIndex]);
        }else {
            JOptionPane.showMessageDialog(garage, "You can't go any further", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Will display the previous frame if it exists
    //Will get a notification if you can't go any further
    private void previousButton(){
        if (currentIndex > 0){
            currentIndex--;
            vehDetails.displayDetails(vehicleList[currentIndex]);
        }else
            JOptionPane.showMessageDialog(garage, "You can't go any further", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    private void resetButton(){
        currentIndex = 0;
        vehicleList = null;
        vehDetails.setVisible(false);
        yearsOldCombo.setSelectedIndex(0);
    }

    //
    private void searchButton(){
        // converts a string range to a lower and upper bounds.
        int[] range = Garage.convertToRange((String)yearsOldCombo.getSelectedItem());

        if (range[0] >= 0){
            vehicleList = garage.searchAge(range[0], range[1]);
        }

        if(vehicleList.length > 0){
            currentIndex = 0;
            vehDetails.setVisible(true);
            vehDetails.displayDetails(vehicleList[0]);

            if(vehicleList.length == 1){
                nextButton.setEnabled(false);
                previousButton.setEnabled(false);
            }else{
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
            }
        }else{
            JOptionPane.showMessageDialog(garage, "No results", "Search failed", JOptionPane.WARNING_MESSAGE);
        }
    }
}