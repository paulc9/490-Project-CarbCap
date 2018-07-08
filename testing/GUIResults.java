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
import java.io.*;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.*;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ShapeUtilities;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.StandardChartTheme;

public class GUIResults extends JPanel implements ActionListener{

    JPanel mainContainer, topContainer, pageImagePanel, infoPanel, graphPanel, container, imgPanel;
    ChartPanel chartPanel;
    // Data name labels
    JLabel labelName, labelCurrentPSI, labelReadyDate, labelGraph,
        labelManualPSI, labelBeerType, labelBottleDate, labelCurrentVol, 
        labelDesiredVol, labelVolPerDay, labelTemp;
    // Data value labels
    JLabel valName, valCurrentPSI, valReadyDate, valManualPSI, 
        valBeerType, valBottleDate, valCurrentVol, valDesiredVol, 
        valVolPerDay, valTemp;
    JButton deleteButton, manualButton, backButton, editButton, simulateButton, notesButton;
    JTextField nameIn, typeIn, psiIn, tempIn, imageIn;
    ImageIcon graphImg;
    TrackingPage tracking;
    CardLayout pages;
    Box buttonContainer;
    Calendar dateCounter;
    Beer currentBeer;
    ArrayList<Beer> trackedBeers;
    int trackedBeersIndex;
    double low, high;
    Color lightRow = CarbCap.background.brighter();
    Color darkRow = CarbCap.background;

    static int pressure, temp;
    static Boolean done;
    javax.swing.Timer timer = new javax.swing.Timer(5000, null);
    JDialog simFrame;
    JPanel simContainer, simImagePanel, simLabelPanel;
    JButton simOkButton;
    JLabel simStatusLabel, simImgLabel, simPressureLabel, simTempLabel, simVolLabel;
    ImageIcon simImg;

    public static void main(String[] args) {
        new GUIResults();
    }



    public GUIResults() {
        mainContainer = new JPanel();
        pageImagePanel = new JPanel();
        infoPanel = new JPanel();
        graphPanel = new JPanel();
        imgPanel = new JPanel();
        topContainer = new JPanel();
        buttonContainer = Box.createHorizontalBox();

        this.setLayout(new BorderLayout());         // Needed to make graph display properly

        RelativeLayout rlVert = new RelativeLayout(RelativeLayout.Y_AXIS);
        rlVert.setFill(true);
        rlVert.setGap(10);
        mainContainer.setLayout(rlVert);

        RelativeLayout rlHoriz = new RelativeLayout(RelativeLayout.X_AXIS);
        rlHoriz.setFill(true);
        topContainer.setLayout(rlHoriz);

        pageImagePanel.setLayout(new BoxLayout(pageImagePanel, BoxLayout.PAGE_AXIS));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        imgPanel.setLayout(new BorderLayout());
        graphPanel.setLayout(new BorderLayout());

        pageImagePanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
        mainContainer.setBorder(CarbCap.padding);
        infoPanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
        imgPanel.setBorder(CarbCap.raised);

        pageImagePanel.setBackground(CarbCap.altBackground);
        infoPanel.setBackground(CarbCap.altBackground);
        imgPanel.setBackground(CarbCap.altBackground);
/*
        addComp(infoPanel, labelName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, valName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelBottleDate, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelCurrentPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelCurrentVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelVolPerDay, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelDesiredVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelReadyDate, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelGraph, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        addComp(infoPanel, labelName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelBeerType, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelBottleDate, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelCurrentPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelCurrentVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelVolPerDay, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelDesiredVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelReadyDate, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(infoPanel, labelGraph, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
*/
        //graphPanel.add(graphImgLabel);

        makePageImagePanel();
        makeInfoPanel();
        makeButtonBox();

        topContainer.add(pageImagePanel, new Float(10));
        topContainer.add(infoPanel, new Float(55));
        topContainer.add(imgPanel, new Float(25));

        mainContainer.add(topContainer, new Float(35));
        mainContainer.add(graphPanel, new Float(60));
        mainContainer.add(buttonContainer, new Float(5));

        this.add(mainContainer);
    }

    public void makePageImagePanel(){
    	ImageIcon img = new ImageIcon("images/beerInfo.png");
		img.setImage(img.getImage().getScaledInstance(-1, (int)(CarbCap.height * 0.15), Image.SCALE_SMOOTH));
		JLabel imgLabel = new JLabel(img);

		JLabel text = new JLabel("Beer Info");

		imgLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		text.setFont(new Font("Helvetica", Font.BOLD, 20));
		//text.setBackground(CarbCap.panelTitle);
		//text.setOpaque(true);

		pageImagePanel.add(text);
		pageImagePanel.add(imgLabel);
    }

    public void makeInfoPanel(){
        JScrollPane scroll = new JScrollPane();
        ScrollablePanel insideScroll = new ScrollablePanel();
        insideScroll.setLayout(new GridBagLayout());
        insideScroll.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);

        scroll.setViewportView(insideScroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setPreferredSize(new Dimension(this.getWidth(), (int)(CarbCap.height * 0.3)));

        infoPanel.add(scroll);

    	labelName = new JLabel("Name", SwingConstants.CENTER);
        labelCurrentPSI = new JLabel("Current PSI", SwingConstants.CENTER);
        labelReadyDate = new JLabel("Estimated Ready Date", SwingConstants.CENTER);
        labelBeerType = new JLabel("Beer Type", SwingConstants.CENTER);
        labelBottleDate = new JLabel("Bottled on", SwingConstants.CENTER);
        labelCurrentVol = new JLabel("Current CO2 Volume", SwingConstants.CENTER);
        labelVolPerDay = new JLabel("Average CO2 Volume Rate (past 4 days)", SwingConstants.CENTER);
        labelDesiredVol = new JLabel("Desired CO2 Volume", SwingConstants.CENTER);
        labelTemp = new JLabel("Temperature (\u00B0F)", SwingConstants.CENTER);

        valName = new JLabel();
        valCurrentPSI = new JLabel();
        valReadyDate = new JLabel();
        valManualPSI = new JLabel();
        valBeerType = new JLabel();
        valBottleDate = new JLabel();
        valCurrentVol = new JLabel();
        valDesiredVol = new JLabel();
        valVolPerDay = new JLabel();
        valTemp = new JLabel();

        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        ArrayList<JLabel> vals = new ArrayList<JLabel>();

        labels.add(labelName);
        labels.add(labelBeerType);
        labels.add(labelBottleDate);
        labels.add(labelReadyDate);  
        labels.add(labelCurrentVol);
        labels.add(labelDesiredVol);
        labels.add(labelVolPerDay);
        labels.add(labelCurrentPSI);
        labels.add(labelTemp);

        vals.add(valName);
        vals.add(valBeerType);
        vals.add(valBottleDate);
        vals.add(valReadyDate);
        vals.add(valCurrentVol);
        vals.add(valDesiredVol);
        vals.add(valVolPerDay);
        vals.add(valCurrentPSI);
        vals.add(valTemp);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 100;
        c.ipady = 5;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;

        for(int i = 0; i < labels.size(); i++){
            JLabel label = labels.get(i);
            JLabel val = vals.get(i);

            label.setFont(CarbCap.infoFont);
            label.setOpaque(true);
            val.setFont(CarbCap.infoFont);
            val.setOpaque(true);
            val.setForeground(CarbCap.valueColor);

            if(i % 2 == 0){
                label.setBackground(lightRow);
                val.setBackground(lightRow);
            }
            else{
                label.setBackground(darkRow);
                val.setBackground(darkRow);
            }

            insideScroll.add(label, c);
            c.gridx++;
            insideScroll.add(val, c);

            c.gridx = 0;
            c.gridy++;
        }
    }

    public void makeButtonBox(){
        deleteButton = new JButton("Delete Beer");
        deleteButton.setToolTipText("Delete Current Beer Data");
        deleteButton.addActionListener(this);

        manualButton = new JButton("Manual volume input");
        manualButton.setToolTipText("Type in pressure and temperature to input volume");
        manualButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setToolTipText("Go back to tracking beers page");
        backButton.addActionListener(this);

        editButton = new JButton("Edit beer");
        editButton.setToolTipText("Change beer name, type, and/or image");
        editButton.addActionListener(this);

        simulateButton = new JButton("Start simulation");
        simulateButton.setToolTipText("Simulate beer tracking");
        simulateButton.addActionListener(this);

        notesButton = new JButton("Notes");
        notesButton.setToolTipText("View or edit your beer notes");
        notesButton.addActionListener(this);

        //deleteButton.setFont(CarbCap.font);
        //enterButton.setFont(CarbCap.font);

        buttonContainer.add(Box.createRigidArea(CarbCap.edgeSpace));
        buttonContainer.add(backButton);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(manualButton);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(notesButton);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(editButton);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(simulateButton);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(deleteButton);
        buttonContainer.add(Box.createRigidArea(CarbCap.edgeSpace));
    }

    public void setPage(Beer beer, ArrayList<Beer> tracked, int index){
        trackedBeers = tracked;
        trackedBeersIndex = index;
        setPage(beer);
    }

    public void setPage(Beer beer){

    	//labelGraph = new JLabel("Graph:", SwingConstants.CENTER);
    	//labelGraph.setFont(CarbCap.font);
        
        imgPanel.removeAll();
        currentBeer = beer;
        /*
        JLabel showImg = Util.showBeerImage(currentBeer, -1, imgPanel.getHeight() * 9 / 10);
        imgPanel.add(showImg);
        */
        (new Util.LoadImage(currentBeer, false, -1, imgPanel.getHeight() * 9 / 10, imgPanel)).execute();
        
        valReadyDate.setForeground(CarbCap.valueColor);
        valName.setText(currentBeer.getName());
        valCurrentPSI.setText("" + currentBeer.getCurrentPSI());
        valCurrentVol.setText("" + CarbCap.df.format(currentBeer.getCurrentVolume()));
        valVolPerDay.setText("" + currentBeer.getAvgVolRateString());
        valDesiredVol.setText("" + currentBeer.getDesiredVolume());
        valReadyDate.setText("" + currentBeer.getReadyDateString() );
        valTemp.setText("" + currentBeer.getCurrentTemp());
        valBeerType.setText(currentBeer.getType());
        valBottleDate.setText(currentBeer.getBottleDateString());
        dateCounter = Calendar.getInstance();


        //disable simulator for sensor beers
        int id = currentBeer.getBeerId();
        if(id >= 0 && id < 3)
            simulateButton.setEnabled(false);
        else
            simulateButton.setEnabled(true);
  
        mainContainer.revalidate();
        mainContainer.repaint();
        drawGraph();
    }


    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == deleteButton){
            final ImageIcon BeerIcon = new ImageIcon("images/Beer Icon.png");
            int n = JOptionPane.showConfirmDialog(
                    mainContainer,
                    "Are you sure you want to delete your beer?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION, 2,
                    BeerIcon);

            if(n==0) {
                /*
                Path path = Paths.get("savedCurrentBeer.ser");

                try {
                    Files.delete(path);
                } catch (NoSuchFileException x) {
                    System.err.format("%s: no such" + " file or directory%n", path);
                } catch (DirectoryNotEmptyException x) {
                    System.err.format("%s not empty%n", path);
                } catch (IOException x) {
                    // File permission problems are caught here.
                    System.err.println(x);
                }
                */
                trackedBeers.remove(trackedBeersIndex);
                Util.saveTrackedBeers(trackedBeers);
                tracking.setBeerArray(trackedBeers);
                tracking.displayTrackedBeers();
                pages.show(container, "Tracking");
            }
        }
        else if ((JButton) action == manualButton){
        	int result = JOptionPane.showConfirmDialog(null, makeInputPanel(currentBeer), "Manual input", JOptionPane.OK_CANCEL_OPTION, 2, new ImageIcon("images/Beer Icon.png"));
        	if (result == JOptionPane.OK_OPTION){
	            try {
	            	Double psi = Double.parseDouble(psiIn.getText());
	            	Double temp = Double.parseDouble(tempIn.getText());

	                currentBeer.setCurrentTracking(psi, temp);
	                currentBeer.adjustAvgVolRate();
	                currentBeer.adjustReadyDate();
	                currentBeer = Util.notifyCheck(currentBeer);

	                updatePage();
	                saveUpdatedBeer();
	            } catch(NumberFormatException exx) {
	                JOptionPane.showMessageDialog(this, "Input error. Please enter decimal or non-decimal numbers for pressure and temperature."); 
	            }
        	}
        }
        else if ((JButton) action == editButton){
        	int result = JOptionPane.showConfirmDialog(null, makeEditPanel(currentBeer), "Edit beer", JOptionPane.OK_CANCEL_OPTION, 2, new ImageIcon("images/Beer Icon.png"));
            if(result == JOptionPane.OK_OPTION){
                if(!currentBeer.getName().equals(nameIn.getText())){
                    currentBeer.setName(nameIn.getText());
                    valName.setText(currentBeer.getName());
                }
                if(!currentBeer.getType().equals(typeIn.getText())){
                    currentBeer.setType(typeIn.getText());
                    valBeerType.setText(currentBeer.getType());
                }
                if(!currentBeer.getBeerImage().equals(imageIn.getText())){
                    currentBeer.setBeerImage(imageIn.getText());
                    if(Util.checkImageDirectory(currentBeer) == false){
                        currentBeer.setBeerImage(Util.copyToImageDir(currentBeer));
                    }

                    imgPanel.removeAll();
                    JLabel showImg = Util.showBeerImage(currentBeer, -1, imgPanel.getHeight() * 9 / 10);
                    imgPanel.add(showImg);
                }

                mainContainer.revalidate();
                mainContainer.repaint();

                saveUpdatedBeer();
            }
        }
        else if ((JButton) action == simulateButton){
        	if(timer.isRunning()){
        		simulatorDone("Simulation stopped", "images/simStop.png");
        	}
        	else{
                if(simFrame != null && simFrame.isVisible())
                    simFrame.dispose();
        		simulator();
            }
        }
        else if ((JButton) action == backButton){
            tracking.setBeerArray(trackedBeers);
            tracking.displayTrackedBeers();
            if(simFrame != null && simFrame.isVisible())
            	simFrame.dispose();
            pages.show(container, "Tracking");
        }
        else if ((JButton) action == simOkButton){
	        if(timer.isRunning())
	        	simulatorDone("Simulation stopped", "images/simStop.png");
	        else
	        	simFrame.dispose();
        }
        else if ((JButton) action == notesButton){
            JTextArea ta = new JTextArea(20, 30);
            ta.setText(currentBeer.getNotes());
            int result = JOptionPane.showConfirmDialog(null, new JScrollPane(ta), "View/Edit Notes", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION){
                currentBeer.setNotes(ta.getText());
                saveUpdatedBeer();
            }
        }
    }

    public void simulator(){
 		pressure = 0;
 		temp = 70;
		done = false;

    	deleteButton.setEnabled(false);
    	manualButton.setEnabled(false);
    	editButton.setEnabled(false);
    	backButton.setEnabled(false);
    	simulateButton.setText("Stop simulation");

    	mainContainer.revalidate();
    	mainContainer.repaint();

    	makeSimFrame();

    	timer = new javax.swing.Timer(2500, null);
    	timer.setRepeats(true);
    	timer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                Random ran = new Random();
                int ranPressure = ran.nextInt(5);	// random pressure added between 0 and 4, inclusive
                int ranTemp = ran.nextInt(11) - 5;	// random temp change between -5 and 5, inclusive

                pressure = pressure + ranPressure;
                temp = temp + ranTemp;

                simPressureLabel.setText("Current pressure: " + pressure + " psi");
                simTempLabel.setText("Current temperature: " + temp + " deg F");
                currentBeer.setCurrentTracking(pressure, temp);
                currentBeer.adjustAvgVolRate();
	            currentBeer.adjustReadyDate();
	            currentBeer = Util.notifyCheck(currentBeer);
	            updatePage();
	            saveUpdatedBeer();
	            if(currentBeer.readyCheck()){
	            	simulatorDone("Carbonation finished!", "images/simDone.png");
	            }
            }
        });
    	timer.start();
    }

    public void makeSimFrame(){
    	JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    	simFrame = new JDialog(new JFrame(), "Simulation");
        simFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
            	if(timer.isRunning())
            		simulatorDone("", "");
                simFrame.dispose();
            }
        });
    	simFrame.setLocation(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());
    	simFrame.setSize(300, 450);

    	simContainer = new JPanel();
    	simContainer.setLayout(new BoxLayout(simContainer, BoxLayout.PAGE_AXIS));
    	simImagePanel = new JPanel();
    	simImagePanel.setLayout(new BorderLayout());
    	simLabelPanel = new JPanel();
    	simLabelPanel.setLayout(new BoxLayout(simLabelPanel, BoxLayout.PAGE_AXIS));

    	simImgLabel = new JLabel("", SwingConstants.CENTER);
    	simImg = new ImageIcon("images/simulator.gif");
    	simImgLabel.setIcon(simImg);

    	simStatusLabel = new JLabel("Carbonating...");
    	simPressureLabel = new JLabel("Current pressure: 0 psi");
    	simTempLabel = new JLabel("Current temperature: 70 deg F");
    	simOkButton = new JButton("Stop simulation");

    	simStatusLabel.setFont(CarbCap.titleFont);
    	simPressureLabel.setFont(CarbCap.font);
    	simTempLabel.setFont(CarbCap.font);
    	simStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	simPressureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	simTempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	simOkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    	simOkButton.addActionListener(this);

    	simImagePanel.add(simImgLabel);
    	simLabelPanel.add(simStatusLabel);
    	simLabelPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    	simLabelPanel.add(simPressureLabel);
    	simLabelPanel.add(simTempLabel);
    	simLabelPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    	simLabelPanel.add(simOkButton);

    	simContainer.add(simImagePanel);
    	simContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    	simContainer.add(simLabelPanel);
    	simContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    	simFrame.add(simContainer);
    	simFrame.setVisible(true);
    }

    public void simulatorDone(String message, String image){
    	timer.stop();
    	simImagePanel.removeAll();
    	simImg = new ImageIcon(image);
    	simImgLabel.setIcon(simImg);
    	simImagePanel.add(simImgLabel);

    	simStatusLabel.setText(message);
   	    deleteButton.setEnabled(true);
    	manualButton.setEnabled(true);
    	editButton.setEnabled(true);
    	backButton.setEnabled(true);
    	simOkButton.setText("Close this window");

    	simContainer.revalidate();
    	simContainer.repaint();

    	simulateButton.setText("Start simulation");
    }

    public void updatePage(){
        valCurrentPSI.setText("" + currentBeer.getCurrentPSI());
        valCurrentVol.setText("" + CarbCap.df.format(currentBeer.getCurrentVolume()));
        valVolPerDay.setText("" + currentBeer.getAvgVolRateString());
        valTemp.setText("" + currentBeer.getCurrentTemp());
        if(currentBeer.warningCheck() == true){
            valReadyDate.setForeground(CarbCap.errorColor);
            valReadyDate.setText("<html><b>Burst danger!</b></html>");
        }
        else if(currentBeer.readyCheck() == true){
            valReadyDate.setForeground(CarbCap.readyColor);
            valReadyDate.setText("<html><b>Now ready!</b></html>");
        }
        else if(currentBeer.getAvgVolRate() < 0.005 && currentBeer.rateExistsCheck() == true){
            valReadyDate.setForeground(CarbCap.plateauedColor);
        	valReadyDate.setText("<html><b>Rate plateaued</b></html>");
        }
        else{
            valReadyDate.setForeground(CarbCap.valueColor);
            valReadyDate.setText("" + currentBeer.getReadyDateString() );
        }
        drawGraph();
    }

    public JPanel makeInputPanel(Beer beer){
    	JPanel ret = new JPanel();
        ret.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        psiIn = new JTextField(10);
        tempIn = new JTextField(10);

        JLabel header = new JLabel("<html><div style='text-align: center;'>Type in the beer's pressure and <br/>temperature for " + beer.getTrackingDateString() + ".</html>", SwingConstants.RIGHT);
        JLabel psi = new JLabel("Pressure (PSI)");
        JLabel temp = new JLabel("Temperature (\u00B0F)");
        
        header.setFont(CarbCap.dialogTitleFont);
        psi.setFont(CarbCap.dialogBodyFont);
        temp.setFont(CarbCap.dialogBodyFont);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 10, 10);

        ret.add(header, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;
        c.gridy++;
        ret.add(psi, c);

        c.gridy++;
        ret.add(temp, c);

        c.gridy = 1;
        c.gridx++;
        c.insets = new Insets(0, 10, 0, 10);
        ret.add(psiIn, c);

        c.gridy++;
        ret.add(tempIn, c);

        return ret;
    }

    public JPanel makeEditPanel(Beer beer){
        JPanel ret = new JPanel();
        ret.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton imageChoose = new JButton("Choose image");

        nameIn = new JTextField(15);
        typeIn = new JTextField(15);
        imageIn = new JTextField(15);

        JLabel name = new JLabel("Beer name  ");
        JLabel type = new JLabel("Beer type  ");
        JLabel image = new JLabel("Beer image  ");

        name.setFont(CarbCap.dialogBodyFont);
        type.setFont(CarbCap.dialogBodyFont);
        image.setFont(CarbCap.dialogBodyFont);

        //typeIn.setMaximumSize(typeIn.getPreferredSize());
        //volumeIn.setMaximumSize(volumeIn.getPreferredSize());
        //imageIn.setMaximumSize(imageIn.getPreferredSize());

        imageChoose.setPreferredSize(new Dimension(120, imageIn.getPreferredSize().height));

        nameIn.setText(beer.getName());
        typeIn.setText(beer.getType());
        imageIn.setText(beer.getBeerImage());

        imageChoose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                final JFileChooser fc = new JFileChooser("images/");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
                fc.setFileFilter(filter);
                fc.setAccessory(new FileChooserThumbnail(fc));

                int ret = fc.showOpenDialog(GUIResults.this);

                if (ret == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();

                    imageIn.setText(file.getPath());
                }
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 0;

        ret.add(name, c);
        c.gridx++;
        ret.add(nameIn, c);

        c.gridx = 0;
        c.gridy++;
        ret.add(type, c);
        c.gridx++;
        ret.add(typeIn, c);

        c.gridx = 0;
        c.gridy++;
        ret.add(image, c);
        c.gridx++;
        ret.add(imageIn, c);
        c.gridx = 4;
        ret.add(imageChoose, c);

        return ret;
    }


    public void linkPages(TrackingPage track, CardLayout change, JPanel main){
        tracking = track;
        pages = change;
        container = main;
    } 





    private void drawGraph(){
        graphPanel.removeAll();
        graphPanel.revalidate();
        JFreeChart lineChart = ChartFactory.createLineChart(
            "Beer CO2 Volume",
            "Date", "Volumes CO2",
            createDataset(),
            PlotOrientation.VERTICAL,
            true,true,false);

        CarbCap.chartTheme.apply(lineChart);
        //NumberAxis num = (NumberAxis)lineChart.getCategoryPlot().getRangeAxis();
        CategoryPlot chart = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) chart.getRenderer();
        chart.getRangeAxis().setRange(low * 0.80, high * 1.20);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.5f));
        renderer.setSeriesShapesVisible(0, true);

        //lineChart.getPlot().setBackgroundPaint(CarbCap.background);

        double desiredVol = currentBeer.getDesiredVolume();
        ValueMarker danger = createMarker(CarbCap.DANGER_LEVEL, Color.RED, "Danger level for bursting!");
        ValueMarker finish = createMarker(desiredVol, Color.GREEN, "Finished carbonation level = " + desiredVol);

        chart.addRangeMarker(danger);
        chart.addRangeMarker(finish);
        lineChart.removeLegend();

        chartPanel = new ChartPanel(lineChart);
        //chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        //chartPanel.setVisible(true);
        graphPanel.add(chartPanel, BorderLayout.CENTER);
    }

    private ValueMarker createMarker(double value, Color color, String message){
        ValueMarker marker = new ValueMarker(value);
        marker.setPaint(color);
        marker.setStroke(new BasicStroke(2.5f));
        marker.setLabel(message);
        marker.setLabelAnchor(RectangleAnchor.LEFT);
        marker.setLabelTextAnchor(TextAnchor.TOP_LEFT);
        return marker;
    }

    private DefaultCategoryDataset createDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int results = currentBeer.getTrackingArrayList().size();

        /* 
            At normal window size, a maximum of 10 dates fit without being cut off.
            Because the if statement at the end may or may not add the most recent data depending on whether it's
            already included or not, the interval must be adjusted so that at most 9 dates are in the dataset before then.
        */
        Double getInterval = results / 9.0;

        int interval = (int)Math.ceil(getInterval);
        int lastDataIndex = 0;
        ArrayList<Beer.TrackingObject> beer = currentBeer.getTrackingArrayList();
        if (results == 0){
            low = 0.0;
            high = 1.0;
        }
        else
            low = high = beer.get(0).getVolume();
        for(int i = 0; i < results; i = i + interval){
            double vol = beer.get(i).getVolume();
            dataset.addValue(vol, "Volumes CO2", beer.get(i).getDateString());
            if (low > vol)
                low = vol;
            if (high < vol)
                high = vol;
            lastDataIndex = i;
        }

        // Add the most recent result to the chart if it hasn't been added
        if(results > 0 && lastDataIndex != (results - 1)){
        	int current = results - 1;
        	dataset.addValue(beer.get(current).getVolume(), "Volumes CO2", beer.get(current).getDateString());
        }
        return dataset;
    }

    public void saveNewBeer(){
        File beerFile = new File("savedBeers.ser");
        if (beerFile.exists()){
            trackedBeers = Util.loadTrackedBeers();
            trackedBeers.add(currentBeer);
            Util.saveTrackedBeers(trackedBeers);
            trackedBeersIndex = trackedBeers.size() - 1;
        } else {
            trackedBeers = new ArrayList<Beer>();
            trackedBeers.add(currentBeer);
            Util.saveTrackedBeers(trackedBeers);
            trackedBeersIndex = 0;            
        }
    }

    public void saveUpdatedBeer(){
        trackedBeers.set(trackedBeersIndex, currentBeer);
        Util.saveTrackedBeers(trackedBeers);
    }

        // Sets the rules for a component destined for a GridBagLayout
        // and then adds it

    private void addComp(JPanel mainContainer, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch) {

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

        mainContainer.add(comp, gridConstraints);

    }
}