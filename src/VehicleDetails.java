//VehicleDetails.java
//Austin Trout

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VehicleDetails extends JPanel implements ComponentListener{

    JLabel manufacturerLabel = new JLabel("Manufacturer");
    JLabel yearLabel = new JLabel("Year");
    JLabel modelLabel = new JLabel("Model");
    JLabel priceLabel = new JLabel("Price");
    JLabel mileLabel = new JLabel("Mileage");
    JLabel infoLabel = new JLabel("More Info");

    private JTextField manufacturerTextInput = new JTextField();
    private JTextField yearTextInput = new JTextField();
    private JTextField modelTextInput = new JTextField();
    private JTextField priceTextInput = new JTextField();
    private JTextField mileTextInput = new JTextField();
    private JTextArea infoAreaInput = new JTextArea(4, 0);

    public VehicleDetails(){
        Insets inset;
        GridBagConstraints gridBagConstraints;
        setLayout(new BorderLayout(0, 20));
        JPanel compPanel = new JPanel(new GridBagLayout());
        String currentFont = manufacturerLabel.getFont().getName();

        //Spacing for the titles on the left side of the input/display boxes
        //top,left,bottom,right
        inset =  new Insets(0, 10, 0, 45);

        /**************************************************************************************************************/

        //The details for the titles on the left side of the input/display boxes
        manufacturerLabel.setFont(new Font(currentFont, Font.BOLD, 14));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = inset;
        compPanel.add(manufacturerLabel, gridBagConstraints);

        yearLabel.setFont(new Font(currentFont, Font.BOLD, 14));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = inset;
        compPanel.add(yearLabel, gridBagConstraints);

        modelLabel.setFont(new Font(currentFont, Font.BOLD, 14));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = inset;
        compPanel.add(modelLabel, gridBagConstraints);

        priceLabel.setFont(new Font(currentFont, Font.BOLD, 14));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = inset;
        compPanel.add(priceLabel, gridBagConstraints);

        mileLabel.setFont(new Font(currentFont, Font.BOLD, 14));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = inset;
        compPanel.add(mileLabel, gridBagConstraints);

        infoLabel.setFont(new Font(currentFont, Font.BOLD, 14));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = inset;
        compPanel.add(infoLabel, gridBagConstraints);

        /**************************************************************************************************************/

        //The details for the text input/display boxes
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(manufacturerTextInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(yearTextInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(modelTextInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(priceTextInput, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = gridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(mileTextInput, gridBagConstraints);

        infoAreaInput.setLineWrap(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = gridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(new JScrollPane(infoAreaInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), gridBagConstraints);

        /**************************************************************************************************************/

        addComponentListener(this);
        add(compPanel, "North");
    }

    public void clearText(){
        manufacturerTextInput.setText("");
        yearTextInput.setText("");
        modelTextInput.setText("");
        priceTextInput.setText("");
        mileTextInput.setText("");
        infoAreaInput.setText("");
    }

    public void componentHidden(ComponentEvent e) {}

    public void componentMoved(ComponentEvent e) {}

    public void componentResized(ComponentEvent e){
        if (e.getSource() == this){
            int width = getWidth();

            if (width >= 0){
                manufacturerTextInput.setColumns(width / 23);
                yearTextInput.setColumns(width / 23);
                modelTextInput.setColumns(width / 23);
                priceTextInput.setColumns(width / 23);
                mileTextInput.setColumns(width / 23);
                infoAreaInput.setColumns((width / 23));
            }
        }
    }

    public void componentShown(ComponentEvent e){}

    public void displayDetails(Vehicle v){
        manufacturerTextInput.setText(v.getManufacturer());
        yearTextInput.setText(Integer.toString(v.getYear()));
        modelTextInput.setText(v.getModel());
        priceTextInput.setText(Integer.toString(v.getPrice()));
        mileTextInput.setText(Double.toString(v.getMiles()));
        infoAreaInput.setText(v.getInformation());
    }

    public String getManufacturerText(){
        return manufacturerTextInput.getText();
    }

    public String getYearText(){
        return yearTextInput.getText();
    }

    public String getModelText(){
        return modelTextInput.getText();
    }

    public String getPriceText(){
        return priceTextInput.getText();
    }

    public String getMilesText(){
        return mileTextInput.getText();
    }

    public String getInfoText(){
        return infoAreaInput.getText();
    }

}
