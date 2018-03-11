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
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


public class GUIResults extends JPanel implements ActionListener{

    JPanel thePanel, thePanel2, thePanel3, thePanel4, container, imgPanel, innerImgPanel;
    ChartPanel chartPanel;
    // Data name labels
    JLabel labelName, labelCurrentPSI, labelReadyDate, labelGraph, /*graphImgLabel,*/ labelManualPSI, labelBeerType, labelBottleDate, labelCurrentVol, labelDesiredVol, labelVolPerDay;
    // Data value labels
    JLabel valName, valCurrentPSI, valReadyDate, valManualPSI, valBeerType, valBottleDate, valCurrentVol, valDesiredVol, valVolPerDay;
    JButton buttonDelBeer, buttonEnter, buttonBack;
    ImageIcon graphImg;
    JTextField psiInput;
    Font font;
    TrackingPage tracking;
    CardLayout pages;
    Box topBox, theBox;
    Calendar dateCounter;
    Beer currentBeer;
    ArrayList<Beer> trackedBeers;
    int trackedBeersIndex;
    double low, high;


    public static void main(String[] args) {
        new GUIResults();
    }





    public GUIResults() {
        thePanel = new JPanel();
        thePanel2 = new JPanel();
        thePanel3 = new JPanel();
        thePanel4 = new JPanel();
        imgPanel = new JPanel();
        innerImgPanel = new JPanel();

        topBox = Box.createHorizontalBox();
        theBox = Box.createHorizontalBox();

        this.setLayout(new BorderLayout());         // Needed to make graph display properly
        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.PAGE_AXIS));
        thePanel2.setLayout(new GridLayout(8, 2));
        thePanel4.setLayout(new BorderLayout());

        innerImgPanel.setBorder(CarbCap.raised);

        font = new Font("Helvetica", Font.PLAIN, 17);

        labelName = new JLabel("Name", SwingConstants.CENTER);
        labelCurrentPSI = new JLabel("Current PSI", SwingConstants.CENTER);
        labelReadyDate = new JLabel("Estimated Ready Date", SwingConstants.CENTER);
        labelGraph = new JLabel("", SwingConstants.CENTER);
        labelBeerType = new JLabel("Beer Type", SwingConstants.CENTER);
        labelBottleDate = new JLabel("Bottled on", SwingConstants.CENTER);
        labelManualPSI = new JLabel("Manual PSI Input:", SwingConstants.CENTER);
        labelCurrentVol = new JLabel("Current CO2 Volume", SwingConstants.CENTER);
        labelVolPerDay = new JLabel("Average CO2 Volume Rate (from past 4 days)", SwingConstants.CENTER);
        labelDesiredVol = new JLabel("Desired CO2 Volume", SwingConstants.CENTER);

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
        buttonEnter.setToolTipText("Enter Current PSI (assume temperature is at 50 deg F)");
        buttonEnter.addActionListener(this);

        buttonBack = new JButton("Back");
        buttonBack.setToolTipText("Go back to tracking beers page");
        buttonBack.addActionListener(this);

        valName = new JLabel();
        valCurrentPSI = new JLabel();
        valReadyDate = new JLabel();
        valManualPSI = new JLabel();
        valBeerType = new JLabel();
        valBottleDate = new JLabel();
        valCurrentVol = new JLabel();
        valDesiredVol = new JLabel();
        valVolPerDay = new JLabel();


        labelName.setFont(font);
        labelCurrentPSI.setFont(font);
        labelReadyDate.setFont(font);
        labelGraph.setFont(font);
        buttonDelBeer.setFont(font);
        labelBeerType.setFont(font);
        labelManualPSI.setFont(font);
        labelBottleDate.setFont(font);
        labelCurrentVol.setFont(font);
        labelVolPerDay.setFont(font);
        labelDesiredVol.setFont(font);
        buttonEnter.setFont(font);

        valName.setFont(font);
        valCurrentPSI.setFont(font);
        valReadyDate.setFont(font);
        valManualPSI.setFont(font);
        valBeerType.setFont(font);
        valBottleDate.setFont(font);
        valCurrentVol.setFont(font);
        valDesiredVol.setFont(font);
        valVolPerDay.setFont(font);

        valName.setForeground(Color.BLACK);
        valCurrentPSI.setForeground(Color.BLACK);
        valReadyDate.setForeground(Color.BLACK);
        valManualPSI.setForeground(Color.BLACK);
        valBeerType.setForeground(Color.BLACK);
        valBottleDate.setForeground(Color.BLACK);
        valCurrentVol.setForeground(Color.BLACK);
        valDesiredVol.setForeground(Color.BLACK);
        valVolPerDay.setForeground(Color.BLACK);

        thePanel2.add(labelName);
        thePanel2.add(valName);
        thePanel2.add(labelBeerType);
        thePanel2.add(valBeerType);
        thePanel2.add(labelBottleDate);
        thePanel2.add(valBottleDate);
        thePanel2.add(labelReadyDate);
        thePanel2.add(valReadyDate);
        thePanel2.add(labelDesiredVol);
        thePanel2.add(valDesiredVol);
        thePanel2.add(labelCurrentVol);
        thePanel2.add(valCurrentVol);
        thePanel2.add(labelVolPerDay);
        thePanel2.add(valVolPerDay);
        thePanel2.add(labelCurrentPSI);
        thePanel2.add(valCurrentPSI);
/*
        addComp(thePanel2, labelName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, valName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelBottleDate, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelVolPerDay, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelDesiredVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelReadyDate, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelGraph, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        addComp(thePanel2, labelName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelBeerType, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelBottleDate, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelVolPerDay, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelDesiredVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelReadyDate, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelGraph, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
*/
        //thePanel4.add(graphImgLabel);

        theBox.add(buttonBack);
        theBox.add(Box.createRigidArea(new Dimension(100,0)));
        theBox.add(labelManualPSI);
        theBox.add(psiInput);
        theBox.add(buttonEnter);

        theBox.add(Box.createRigidArea(new Dimension(150,0)));
        theBox.add(buttonDelBeer);

        topBox.add(thePanel2);
        topBox.add(imgPanel);

        thePanel.add(topBox);
        thePanel.add(thePanel4);
        thePanel.add(theBox);

        this.add(thePanel);
    }

    public Beer loadBeer(){
        Beer inBeer = null;
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("savedCurrentBeer.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            inBeer = (Beer)in.readObject();

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

    public void setPage(Beer beer, ArrayList<Beer> tracked, int index){
        trackedBeers = tracked;
        trackedBeersIndex = index;
        setPage(beer);
    }

    public void setPage(Beer beer){
        innerImgPanel.removeAll();
        currentBeer = beer;

        //URL url = this.getClass().getClassLoader().getResource("images/"+currentBeer.getBeerImage()+".jpg");
        File check = new File(currentBeer.getBeerImage());
        ImageIcon img;
        if (!(check.exists())){
            img = new ImageIcon("images/no_image.png");
        }
        else{
            img = new ImageIcon(currentBeer.getBeerImage());
        }
        img.setImage(img.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        JLabel showImg=new JLabel(img);
        innerImgPanel.add(showImg);
        imgPanel.add(innerImgPanel);

        valName.setText(currentBeer.getName());
        valCurrentPSI.setText("" + currentBeer.getCurrentPSI());
        valCurrentVol.setText("" + CarbCap.df.format(currentBeer.getCurrentVolume()));
        valVolPerDay.setText("" + currentBeer.getAvgVolRateString());
        valDesiredVol.setText("" + currentBeer.getDesiredVolume());
        valReadyDate.setText("" + currentBeer.getReadyDateString() );
        labelGraph.setText("Graph: ");
        valBeerType.setText(currentBeer.getType());
        valBottleDate.setText(currentBeer.getBottleDateString());
        dateCounter = Calendar.getInstance();

        thePanel.revalidate();
        thePanel.repaint();
        drawGraph();
    }


    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == buttonDelBeer){
            final ImageIcon BeerIcon = new ImageIcon("images/Beer Icon.png");
            int n = JOptionPane.showConfirmDialog(
                    thePanel,
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
                //input.clearFields();
                trackedBeers.remove(trackedBeersIndex);
                saveTrackedBeers();
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
        Boolean sendMail = false;
        Boolean emailNotify = false;

        if(CarbCap.properties.getProperty("emailNotify").equals("true"))
            emailNotify = true;

        // Ready notification
        if(currentBeer.getCurrentVolume() >= currentBeer.getDesiredVolume() && currentBeer.readyCheck() == false)
        {
        	if (emailNotify == true){
	           subject = "Your Beer is Ready";
	           content = "Hello there, your " + currentBeer.getType() + " beer \"" + currentBeer.getName() + "\" is ready!!!";
               sendMail = true;
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
            currentBeer.plateauedLogged();
        }

        // Email notification
        if (sendMail == true){
            try {  
                sentmail(subject, content, imageName, email);
            } catch (Exception ex) {
                Logger.getLogger(GUIResults.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, error);
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
        thePanel4.removeAll();
        thePanel4.revalidate();
        JFreeChart lineChart = ChartFactory.createLineChart(
            "CO2 Volumes in Beer",
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
        //chart.getRenderer().setSeriesShapesVisible(0, true);
        //Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
        //chart.getRenderer().setSeriesShape(0, cross);
        renderer.setSeriesShapesVisible(0, true);

        double desiredVol = currentBeer.getDesiredVolume();
        ValueMarker danger = createMarker(CarbCap.DANGER_LEVEL, Color.RED, "Danger level for bursting!");
        ValueMarker finish = createMarker(desiredVol, Color.GREEN, "Finished carbonation level = " + desiredVol);

        chart.addRangeMarker(danger);
        chart.addRangeMarker(finish);

        chartPanel = new ChartPanel(lineChart);
        //chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        //chartPanel.setVisible(true);
        thePanel4.add(chartPanel, BorderLayout.CENTER);
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

        // At normal window size, a maximum of 10 dates fit without being cut off.
        // Because the if statement at the end may or may not add the most recent data depending on whether it's
        // already included or not, the interval must be adjusted so that at most 9 dates are in the dataset before then.
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




    public void sentmail(String subject, String content, String image, String email) throws MessagingException, Exception
    {
        Properties props = new Properties();                    
        props.setProperty("mail.transport.protocol", "smtp");   
        props.setProperty("mail.smtp.host", "smtp.gmail.com");   
        props.setProperty("mail.smtp.auth", "true"); 
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        Session session = Session.getInstance(props);
        session.setDebug(true);   
        MimeMessage message = createMimeMessage(session, "carbcap490@gmail.com", email, subject, content, image);
        Transport transport = session.getTransport();
        transport.connect("carbcap490@gmail.com", "Comp4900");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }  
    public MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String subject, String content, String image) throws Exception 
    {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "CarbCap", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "", "UTF-8"));
        message.setSubject(subject, "UTF-8");
        MimeBodyPart imageName = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(image));
        imageName.setDataHandler(dh);
        imageName.setContentID("beer image");
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");
        MimeMultipart mailBody= new MimeMultipart();
        mailBody.addBodyPart(text);
        mailBody.addBodyPart(imageName);
        mailBody.setSubType("related"); 
        message.setContent(mailBody);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    public void saveNewBeer(){
        File beerFile = new File("savedBeers.ser");
        if (beerFile.exists()){
            loadTrackedBeers();
            trackedBeers.add(currentBeer);
            saveTrackedBeers();
            trackedBeersIndex = trackedBeers.size() - 1;
        } else {
            trackedBeers = new ArrayList<Beer>();
            trackedBeers.add(currentBeer);
            saveTrackedBeers();
            trackedBeersIndex = 0;            
        }
    }

    public void saveUpdatedBeer(){
        trackedBeers.set(trackedBeersIndex, currentBeer);
        saveTrackedBeers();
    }

    public void saveTrackedBeers(){
        try{
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("savedBeers.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(trackedBeers);

            out.close();
            file.close();

            System.out.println("savedBeers.ser has been serialized");
        }
        catch(IOException ex)
        {
            System.out.println("Error while saving savedBeers.ser");
        }
    }

    public void loadTrackedBeers(){
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("savedBeers.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            trackedBeers = (ArrayList<Beer>)in.readObject();

            in.close();
            file.close();

            System.out.println("savedBeers.ser has been deserialized");

        }

        catch(IOException ex)
        {
            System.out.println("Error while loading savedBeers.ser");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
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