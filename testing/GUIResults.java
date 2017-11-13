/**
 * Created by Bhawley on 9/27/2017.
 */

//package start;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.net.URL;
import java.text.ParseException;
import org.jdatepicker.impl.JDatePickerImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class GUIResults extends JPanel implements ActionListener{

    JPanel thePanel, thePanel2, thePanel3, thePanel4, container;
    ChartPanel chartPanel;
    JLabel labelName, labelCurrentPSI, labelDesiredPSI, labelReadyDate, labelGraph, graphImgLabel, labelManualPSI, labelBeerType, labelBottleDate;
    JButton buttonDelBeer, buttonEnter;
    ImageIcon graphImg;
    JTextField psiInput;
    Font font;
    InputPage input;
    CardLayout pages;
    Box theBox;
    Calendar dateCounter;
    Beer currentBeer = loadBeer();


    public static void main(String[] args) {
        new GUIResults();
    }





    public GUIResults() {
        thePanel = new JPanel();
        thePanel2 = new JPanel();
        thePanel3 = new JPanel();
        thePanel4 = new JPanel();

        theBox = Box.createHorizontalBox();

        this.setLayout(new BorderLayout());         // Needed to make graph display properly
        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.PAGE_AXIS));
        thePanel2.setLayout(new GridLayout(0, 1));
        thePanel4.setLayout(new BorderLayout());

        font = new Font("Helvetica", Font.PLAIN, 22);


        labelName = new JLabel("", SwingConstants.CENTER);
        labelCurrentPSI = new JLabel("", SwingConstants.CENTER);
        labelDesiredPSI = new JLabel("", SwingConstants.CENTER);
        labelReadyDate = new JLabel("", SwingConstants.CENTER);
        labelGraph = new JLabel("", SwingConstants.CENTER);
        labelBeerType = new JLabel("", SwingConstants.CENTER);
        labelBottleDate = new JLabel("", SwingConstants.CENTER);
        labelManualPSI = new JLabel("Manual PSI Input:", SwingConstants.CENTER);

        psiInput = new JTextField(10);
        psiInput.setMaximumSize( psiInput.getPreferredSize() );
/*
        URL url = this.getClass().getClassLoader().getResource("images/graph.jpg");
        graphImg = new ImageIcon(url);
        Image image = graphImg.getImage(); // transform it
        Image newimg = image.getScaledInstance(320, 165,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        graphImg = new ImageIcon(newimg);  // transform it back
        graphImgLabel = new JLabel(graphImg);
*/
        buttonDelBeer = new JButton("Delete Beer");
        buttonDelBeer.setToolTipText("Delete Current Beer Data");
        buttonDelBeer.addActionListener(this);

        buttonEnter = new JButton("Confirm");
        buttonEnter.setToolTipText("Enter Current PSI");
        buttonEnter.addActionListener(this);


        labelName.setFont(font);
        labelCurrentPSI.setFont(font);
        labelDesiredPSI.setFont(font);
        labelReadyDate.setFont(font);
        labelGraph.setFont(font);
        buttonDelBeer.setFont(font);
        labelBeerType.setFont(font);
        labelManualPSI.setFont(font);
        labelBottleDate.setFont(font);
        buttonEnter.setFont(font);


        addComp(thePanel2, labelName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelBeerType, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelBottleDate, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelDesiredPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelReadyDate, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelGraph, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        //thePanel4.add(graphImgLabel);

        theBox.add(Box.createRigidArea(new Dimension(100,0)));
        theBox.add(labelManualPSI);
        theBox.add(psiInput);
        theBox.add(buttonEnter);

        theBox.add(Box.createRigidArea(new Dimension(150,0)));
        theBox.add(buttonDelBeer);


        thePanel.add(thePanel2);
        thePanel.add(thePanel4);
        thePanel.add(theBox);

        this.add(thePanel);
    }

    public void loadBeer(){
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("savedCurrentBeer.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            Beer inBeer = (Beer)in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

    return inBeer;
    }

    public void setPage(){
        labelName.setText("Name: " + currentBeer.getName());
        labelCurrentPSI.setText("Current PSI: "+ currentBeer.getCurrentPSI());
        labelDesiredPSI.setText("Desired PSI: " + currentBeer.getDesiredPSI());
        labelReadyDate.setText("Estimated Ready Date: " + currentBeer.getReadyDateString() );
        labelGraph.setText("Graph: ");
        labelBeerType.setText("Beer Type: " + currentBeer.getType());
        labelBottleDate.setText("Bottled on: " + currentBeer.getBottleDateString());
        dateCounter = Calendar.getInstance();
        drawGraph();
    }

    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == buttonDelBeer)
            try {
                Files.delete("savedCurrentBeer.ser");
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", path);
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }
            pages.show(container, "Input");
        else if ((JButton) action == buttonEnter && psiInput.getText().isEmpty() )
            JOptionPane.showMessageDialog(this, "Please enter PSI");
        else if ((JButton) action == buttonEnter && !psiInput.getText().isEmpty() ){
            currentBeer.setCurrentPSI(Integer.parseInt(psiInput.getText()));
            labelCurrentPSI.setText("Current PSI: "+ currentBeer.getCurrentPSI());
            drawGraph();
        }
    }

    public void linkPages(InputPage next, CardLayout change, JPanel main){
        input = next;
        pages = change;
        container = main;
    } 

    private void drawGraph(){
        thePanel4.removeAll();
        thePanel4.revalidate();
        JFreeChart lineChart = ChartFactory.createLineChart(
            "Beer PSI",
            "Date", "PSI",
            createDataset(),
            PlotOrientation.VERTICAL,
            true,true,false);
        chartPanel = new ChartPanel(lineChart);
        //chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        //chartPanel.setVisible(true);
        thePanel4.add(chartPanel, BorderLayout.CENTER);
    }

    private DefaultCategoryDataset createDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int results = currentBeer.getTrackingArrayList().size();
        ArrayList<Beer.PSItrackingObject> beer = currentBeer.getTrackingArrayList();
        for(int i = 0; i < results; i++){
            dataset.addValue(beer.get(i).getPSI(), "PSI", beer.get(i).getDateString());
        }
        return dataset;
    }

        // Sets the rules for a component destined for a GridBagLayout
        // and then adds it

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch) {

        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = xPos;
        gridConstraints.gridy = yPos;
        gridConstraints.gridwidth = compWidth;
        gridConstraints.gridheight = compHeight;
        gridConstraints.weightx = 100;
        gridConstraints.weighty = 100;
        //gridConstraints.insets = new Insets(5, 5, 5, 5);
        gridConstraints.anchor = place;
        gridConstraints.fill = stretch;

        thePanel.add(comp, gridConstraints);

    }




}