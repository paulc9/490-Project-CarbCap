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


public class GUIResults extends JPanel implements ActionListener{

    JPanel mainContainer, topContainer, pageImagePanel, infoPanel, graphPanel, container, imgPanel;
    ChartPanel chartPanel;
    // Data name labels
    JLabel labelName, labelCurrentPSI, labelReadyDate, labelGraph, labelManualPSI, labelBeerType, labelBottleDate, labelCurrentVol, labelDesiredVol, labelVolPerDay;
    // Data value labels
    JLabel valName, valCurrentPSI, valReadyDate, valManualPSI, valBeerType, valBottleDate, valCurrentVol, valDesiredVol, valVolPerDay;
    JButton buttonDelBeer, buttonEnter, buttonBack;
    ImageIcon graphImg;
    JTextField psiInput;
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
        mainContainer.setLayout(new GridBagLayout());
        topContainer.setLayout(new GridBagLayout());
        pageImagePanel.setLayout(new BoxLayout(pageImagePanel, BoxLayout.PAGE_AXIS));
        infoPanel.setLayout(new GridBagLayout());
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

        GridBagConstraints inner = new GridBagConstraints();
        inner.gridx = 0;
        inner.gridy = 0;
        inner.weightx = 0;
        inner.weighty = 1;
        inner.fill = GridBagConstraints.BOTH;
        topContainer.add(pageImagePanel, inner);

        inner.gridx++;
        inner.weightx = 0;
        topContainer.add(infoPanel, inner);

        inner.gridx++;
        inner.weightx = 1;
        topContainer.add(imgPanel, inner);

        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainContainer.add(topContainer, c);

        c.gridy++;
        c.insets = new Insets(10, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        mainContainer.add(graphPanel, c);

        c.gridy++;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainContainer.add(buttonContainer, c);

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
    	labelName = new JLabel("Name", SwingConstants.CENTER);
        labelCurrentPSI = new JLabel("Current PSI", SwingConstants.CENTER);
        labelReadyDate = new JLabel("Estimated Ready Date", SwingConstants.CENTER);
        labelBeerType = new JLabel("Beer Type", SwingConstants.CENTER);
        labelBottleDate = new JLabel("Bottled on", SwingConstants.CENTER);
        labelCurrentVol = new JLabel("Current CO2 Volume", SwingConstants.CENTER);
        labelVolPerDay = new JLabel("Average CO2 Volume Rate (past 4 days)", SwingConstants.CENTER);
        labelDesiredVol = new JLabel("Desired CO2 Volume", SwingConstants.CENTER);

        valName = new JLabel();
        valCurrentPSI = new JLabel();
        valReadyDate = new JLabel();
        valManualPSI = new JLabel();
        valBeerType = new JLabel();
        valBottleDate = new JLabel();
        valCurrentVol = new JLabel();
        valDesiredVol = new JLabel();
        valVolPerDay = new JLabel();

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

        vals.add(valName);
        vals.add(valBeerType);
        vals.add(valBottleDate);
        vals.add(valReadyDate);
        vals.add(valCurrentVol);
        vals.add(valDesiredVol);
        vals.add(valVolPerDay);
        vals.add(valCurrentPSI);

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
            val.setForeground(Color.WHITE);

            if(i % 2 == 0){
                label.setBackground(lightRow);
                val.setBackground(lightRow);
            }
            else{
                label.setBackground(darkRow);
                val.setBackground(darkRow);
            }

            infoPanel.add(label, c);
            c.gridx++;
            infoPanel.add(val, c);

            c.gridx = 0;
            c.gridy++;
        }
    }

    public void makeButtonBox(){
    	labelManualPSI = new JLabel("Manual PSI Input:  ", SwingConstants.CENTER);
    	labelManualPSI.setFont(CarbCap.font);
    	valManualPSI.setFont(CarbCap.font);
        valManualPSI.setForeground(Color.BLACK);    	

    	psiInput = new JTextField(10);
        psiInput.setMaximumSize( psiInput.getPreferredSize() );

        buttonDelBeer = new JButton("Delete Beer");
        buttonDelBeer.setToolTipText("Delete Current Beer Data");
        buttonDelBeer.addActionListener(this);

        buttonEnter = new JButton("Confirm");
        buttonEnter.setToolTipText("Enter Current PSI (assume temperature is at 50 deg F)");
        buttonEnter.addActionListener(this);

        buttonBack = new JButton("Back");
        buttonBack.setToolTipText("Go back to tracking beers page");
        buttonBack.addActionListener(this);

        //buttonDelBeer.setFont(CarbCap.font);
        //buttonEnter.setFont(CarbCap.font);

        buttonContainer.add(Box.createRigidArea(CarbCap.edgeSpace));
        buttonContainer.add(buttonBack);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(labelManualPSI);
        buttonContainer.add(psiInput);
        buttonContainer.add(Box.createRigidArea(CarbCap.space));
        buttonContainer.add(buttonEnter);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(buttonDelBeer);
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

        JLabel showImg = Util.showBeerImage(currentBeer, -1, imgPanel.getHeight() * 9 / 10);
        imgPanel.add(showImg);

        valName.setText(currentBeer.getName());
        valCurrentPSI.setText("" + currentBeer.getCurrentPSI());
        valCurrentVol.setText("" + CarbCap.df.format(currentBeer.getCurrentVolume()));
        valVolPerDay.setText("" + currentBeer.getAvgVolRateString());
        valDesiredVol.setText("" + currentBeer.getDesiredVolume());
        valReadyDate.setText("" + currentBeer.getReadyDateString() );
        valBeerType.setText(currentBeer.getType());
        valBottleDate.setText(currentBeer.getBottleDateString());
        dateCounter = Calendar.getInstance();

        psiInput.setText("");

        mainContainer.revalidate();
        mainContainer.repaint();
        drawGraph();
    }


    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == buttonDelBeer){
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
        else if ((JButton) action == buttonEnter && psiInput.getText().isEmpty() )
            JOptionPane.showMessageDialog(this, "Please enter PSI.");
        else if ((JButton) action == buttonEnter && !psiInput.getText().isEmpty() ){
            try {
                currentBeer.setCurrentTracking(Integer.parseInt(psiInput.getText()));
                currentBeer.adjustAvgVolRate();
                currentBeer.adjustReadyDate();
                //currentBeer.saveCurrentBeerStateToFile();
                notifyCheck();
                updatePage();
                saveUpdatedBeer();
            } catch(NumberFormatException exx) {
                JOptionPane.showMessageDialog(this, "Input error. Please enter a whole integer for PSI."); 
            }
        }
        else if ((JButton) action == buttonBack){
            tracking.setBeerArray(trackedBeers);
            tracking.displayTrackedBeers();
            pages.show(container, "Tracking");
        }
    }

    public void notifyCheck(){
        String imageName = currentBeer.getBeerImage();
        String email = CarbCap.properties.getProperty("email");
        String error = "Error sending email. Check to make sure you are connected online and the email in the options page is correct.";
        String subject = null;
        String content = null;
        String twitterMsg = null;

        Boolean sendMail = false;
        Boolean emailNotify = false;
        Boolean sendTwitterDirect = false;
        Boolean twitterDirectNotify = false;
        Boolean sendTwitterStatus = false;
        Boolean twitterStatusNotify = false;

        if(CarbCap.properties.getProperty("emailNotify", "").equals("true"))
            emailNotify = true;
        if(CarbCap.properties.getProperty("twitterDirectNotify", "").equals("true"))
            twitterDirectNotify = true;
        if(CarbCap.properties.getProperty("twitterStatusNotify", "").equals("true"))
            twitterStatusNotify = true;

        // Ready notification
        if(currentBeer.getCurrentVolume() >= currentBeer.getDesiredVolume() && currentBeer.readyCheck() == false)
        {
        	if (emailNotify == true){
	           subject = "Your Beer is Ready";
	           content = "Hello there, your " + currentBeer.getType() + " beer \"" + currentBeer.getName() + "\" is ready!!!";
               sendMail = true;
		    }
            if (twitterDirectNotify == true || twitterStatusNotify == true){
                twitterMsg = "Your " + currentBeer.getType() + " beer \"" + currentBeer.getName() + "\" is ready!";
                if (twitterDirectNotify == true)
                    sendTwitterDirect = true;
                if (twitterStatusNotify == true)
                    sendTwitterStatus = true;
            }
            currentBeer.readyLogged();
        }

        // Warning notification
        else if(currentBeer.getCurrentVolume() > CarbCap.DANGER_LEVEL && currentBeer.warningCheck() == false)
        {
        	if (emailNotify == true){
                subject = "Beer Warning";
                content = "Your "  + currentBeer.getType() + " beer \"" + currentBeer.getName() + "\" is at CO2 level " + CarbCap.df.format(currentBeer.getCurrentVolume()) + " and is in danger of bursting!";
	            sendMail = true;
		    }
            if (twitterDirectNotify == true || twitterStatusNotify == true){
                twitterMsg = "Danger! Your " + currentBeer.getType() + " beer \"" + currentBeer.getName() + "\" may burst soon!";
                if (twitterDirectNotify == true)
                    sendTwitterDirect = true;
                if (twitterStatusNotify == true)
                    sendTwitterStatus = true;
            }
            currentBeer.warningLogged();
        }

        // Plateaued notification
        else if(currentBeer.plateauedCheck() == false && currentBeer.weekPlateaued() == true)
        {
            if (emailNotify == true){
                subject = "Beer Plateaued";
                content = "Your "  + currentBeer.getType() + " beer \"" + currentBeer.getName() + "\" has plateaued at CO2 level " + CarbCap.df.format(currentBeer.getCurrentVolume()) + " and will likely not carbonate much more.";
                sendMail = true;
            }
            if (twitterDirectNotify == true || twitterStatusNotify == true){
                twitterMsg = "Your " + currentBeer.getType() + " beer \"" + currentBeer.getName() + "\" has plateaued at CO2 level" + CarbCap.df.format(currentBeer.getCurrentVolume()) + ".";
                if (twitterDirectNotify == true)
                    sendTwitterDirect = true;
                if (twitterStatusNotify == true)
                    sendTwitterStatus = true;
            }
            currentBeer.plateauedLogged();
        }

        // Email notification
        if (sendMail == true){
            try {  
                Util.sendMail(subject, content, imageName, email);
            } catch (Exception ex) {
                Logger.getLogger(GUIResults.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, error);
            }
        }

        // Twitter notifications
        if (sendTwitterDirect == true || sendTwitterStatus == true){
            if (sendTwitterDirect == true){
                try{
                    Util.sendTwitterDirectMessage(twitterMsg);
                } catch (Exception ex){
                    System.out.println("There's been an error sending the Twitter direct message from the results page!");
                }
            }

            if (sendTwitterStatus == true){
                twitterMsg = twitterMsg.concat(" @" + CarbCap.properties.getProperty("twitterUsername"));
                try{
                    Util.postStatus(twitterMsg);
                } catch (Exception ex){
                    System.out.println("There's been an error posting the Twitter status message from the results page!");
                }
            }
        }
    }

    public void updatePage(){
        valCurrentPSI.setText("" + currentBeer.getCurrentPSI());
        valCurrentVol.setText("" + CarbCap.df.format(currentBeer.getCurrentVolume()));
        valVolPerDay.setText("" + currentBeer.getAvgVolRateString());
        if(currentBeer.readyCheck() == true)
            valReadyDate.setText("Now ready!");
        else if(currentBeer.getAvgVolRate() < 0.005 && currentBeer.rateExistsCheck() == true)
        	valReadyDate.setText("Too slow...");
        else
            valReadyDate.setText("" + currentBeer.getReadyDateString() );
        drawGraph();
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
        //NumberAxis num = (NumberAxis)lineChart.getCategoryPlot().getRangeAxis();
        CategoryPlot chart = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) chart.getRenderer();
        chart.getRangeAxis().setRange(low * 0.80, high * 1.20);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.5f));
        renderer.setSeriesShapesVisible(0, true);

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