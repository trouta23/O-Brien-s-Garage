//SearchByOtherPanel.java
//Austin Trout

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SearchViaPriceMileage extends JPanel implements ActionListener{

    private String[] price = {"0-10000", "10000-25000", "25000-50000", "50000-75000", "75000-100000", "100000-250000", "250000-500000", "500000+"};
    private String[] mileage = {"0-10000", "10000-25000", "25000-50000", "50000-75000", "75000-100000", "100000-150000", "150000-200000", "200000+"};

    private Vehicle[] vehicleList;
    private Garage garage;
    private int currentIndex = 0;

    JLabel headingLabel = new JLabel("Search by Price and Mileage");
    JLabel priceLabel = new JLabel("Price");
    JLabel mileageLabel = new JLabel("Mileage");

    private JButton searchButton = new JButton("Search");
    private JButton resetButton = new JButton("Reset");
    private JButton previousButton = new JButton("Previous");
    private JButton nextButton = new JButton("Next");

    private JComboBox priceCombo = new JComboBox(price);
    private JComboBox mileageCombo = new JComboBox(mileage);

    JPanel topPanel = new JPanel();
    JPanel pricePanel = new JPanel();
    JPanel mileagePanel = new JPanel();
    JPanel priceMileagePanel = new JPanel();
    JPanel searchButtonsPanel = new JPanel();
    JPanel navigateButtonsPanel = new JPanel();

    private VehicleDetails vehDetails = new VehicleDetails();

    public SearchViaPriceMileage(Garage gar){
        garage = gar;
        setLayout(new BorderLayout());
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        resetButton.addActionListener(this);
        searchButton.addActionListener(this);
        pricePanel.add(priceLabel);
        pricePanel.add(priceCombo);
        mileagePanel.add(mileageLabel);
        mileagePanel.add(mileageCombo);
        priceMileagePanel.add(pricePanel);
        priceMileagePanel.add(mileagePanel);

        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(resetButton);
        navigateButtonsPanel.add(previousButton);
        navigateButtonsPanel.add(nextButton);

        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(headingLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(priceMileagePanel);
        topPanel.add(searchButtonsPanel);
        topPanel.add(Box.createVerticalStrut(15));
        vehDetails.add(navigateButtonsPanel, "Center");
        vehDetails.setVisible(false);

        add(topPanel, "North");
        add(vehDetails, "Center");
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == searchButton)
            searchButton();
        else if (e.getSource() == resetButton)
            resetButton();
        else if (e.getSource() == previousButton)
            previousButton();
        else if (e.getSource() == nextButton)
            nextButton();
    }

    //Will display the next frame if it exists
    //Will get a notification if you can't go any further
    private void nextButton(){
        if (currentIndex < vehicleList.length - 1){
            currentIndex++;
            vehDetails.displayDetails(vehicleList[currentIndex]);
        }else{
            JOptionPane.showMessageDialog(garage, "You can't go any further", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Will display the previous frame if it exists
    //Will get a notification if you can't go any further
    private void previousButton(){
        if (currentIndex > 0){
            currentIndex--;
            vehDetails.displayDetails(vehicleList[currentIndex]);
        }
        else
            JOptionPane.showMessageDialog(garage, "You can't go any further", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    private void resetButton(){
        currentIndex = 0;
        vehicleList = null;
        vehDetails.setVisible(false);
        priceCombo.setSelectedIndex(0);
        mileageCombo.setSelectedIndex(0);
    }

    private void searchButton(){
        //Convert distance and price combo box text into a range
        int[] mileageRange = Garage.convertToRange((String)mileageCombo.getSelectedItem());
        int[] priceRange = Garage.convertToRange((String)priceCombo.getSelectedItem());

        //[0] is minimum and [1] is maximum
        if (priceRange[0] >= 0 && mileageRange[0] >= 0){
            vehicleList = garage.searchPriceMileage(priceRange[0], priceRange[1], mileageRange[0], mileageRange[1]);
        }

        if (vehicleList.length > 0){
            currentIndex = 0;
            vehDetails.setVisible(true);
            vehDetails.displayDetails(vehicleList[0]);

            //If there's only one car in the list the prev and next buttons won't enable
            if (vehicleList.length == 1){
                nextButton.setEnabled(false);
                previousButton.setEnabled(false);
            }else{
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
            }
        }else
            JOptionPane.showMessageDialog(garage, "No results", "Search failed", JOptionPane.WARNING_MESSAGE);
    }
}