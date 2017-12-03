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

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GUIResults extends JPanel implements ActionListener{

    JPanel thePanel, thePanel2, thePanel3, thePanel4, container;
    ChartPanel chartPanel;
    JLabel labelName, labelCurrentPSI, labelReadyDate, labelGraph, graphImgLabel, labelManualPSI, labelBeerType, labelBottleDate, labelCurrentVol, labelDesiredVol, labelVolPerDay;
    JButton buttonDelBeer, buttonEnter;
    ImageIcon graphImg;
    JTextField psiInput;
    Font font;
    InputPage input;
    CardLayout pages;
    Box theBox;
    Calendar dateCounter;
    Beer currentBeer;


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

        font = new Font("Helvetica", Font.PLAIN, 17);


        labelName = new JLabel("", SwingConstants.CENTER);
        labelCurrentPSI = new JLabel("", SwingConstants.CENTER);
        labelReadyDate = new JLabel("", SwingConstants.CENTER);
        labelGraph = new JLabel("", SwingConstants.CENTER);
        labelBeerType = new JLabel("", SwingConstants.CENTER);
        labelBottleDate = new JLabel("", SwingConstants.CENTER);
        labelManualPSI = new JLabel("Manual PSI Input:", SwingConstants.CENTER);
        labelCurrentVol = new JLabel("", SwingConstants.CENTER);
        labelVolPerDay = new JLabel("", SwingConstants.CENTER);
        labelDesiredVol = new JLabel("", SwingConstants.CENTER);

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


        addComp(thePanel2, labelName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelBeerType, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelBottleDate, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelVolPerDay, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelDesiredVol, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
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

    public void setPage(){
        currentBeer = loadBeer();
        labelName.setText("Name: " + currentBeer.getName());
        labelCurrentPSI.setText("Current PSI: "+ currentBeer.getCurrentPSI());
        labelCurrentVol.setText("Current CO2 Volume: " + currentBeer.getCurrentVolume());
        labelVolPerDay.setText("Average CO2 Volume Rate (from past 4 days): " + currentBeer.getAvgVolRateString());
        labelDesiredVol.setText("Desired CO2 Volume: " + currentBeer.getDesiredVolume());
        labelReadyDate.setText("Estimated Ready Date: " + currentBeer.getReadyDateString() );
        labelGraph.setText("Graph: ");
        labelBeerType.setText("Beer Type: " + currentBeer.getType());
        labelBottleDate.setText("Bottled on: " + currentBeer.getBottleDateString());
        dateCounter = Calendar.getInstance();
        drawGraph();
    }





    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == buttonDelBeer){
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
            input.clearFields();
            pages.show(container, "Input");
        }
        else if ((JButton) action == buttonEnter && psiInput.getText().isEmpty() )
            JOptionPane.showMessageDialog(this, "Please enter PSI");
        else if ((JButton) action == buttonEnter && !psiInput.getText().isEmpty() ){
            currentBeer.setCurrentTracking(Integer.parseInt(psiInput.getText()));
            currentBeer.adjustAvgVolRate();
            currentBeer.adjustReadyDate();
            currentBeer.saveCurrentBeerStateToFile();
            // Email ready notification
            if(currentBeer.getCurrentVolume() >= currentBeer.getDesiredVolume() && currentBeer.readyCheck() == false)
            {
                try {  
                    String subject = "Your Beer is Ready";
                    String content = "Hello there, your beer \"" + currentBeer.getName() + "\" is ready!!!";
                    sentmail(subject, content);
                    currentBeer.readyLogged();
                } catch (Exception ex) {
                    Logger.getLogger(GUIResults.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Email warning notification
            else if(currentBeer.getCurrentVolume() > 4.1 && currentBeer.warningCheck() == false)
            {
                try{
                    String subject = "Beer Warning";
                    String content = "Your beer \"" + currentBeer.getName() + "\" is at CO2 level " + currentBeer.getCurrentVolume() + " and is in danger of bursting!";
                    sentmail(subject, content);
                    currentBeer.warningLogged();
                } catch (Exception ex){
                    Logger.getLogger(GUIResults.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Email plateaued notification
            else if(currentBeer.plateauedCheck() == false)
            {
                if(currentBeer.weekPlateaued() == true){
                    try{
                        String subject = "Beer Plateaued";
                        String content = "Your beer \"" + currentBeer.getName() + "\" has plateaued at CO2 level " + currentBeer.getCurrentVolume() + " and will likely not carbonate much more.";
                        sentmail(subject, content);
                        currentBeer.plateauedLogged();
                    } catch (Exception ex){
                        Logger.getLogger(GUIResults.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            updatePage();
        }
    }

    public void updatePage(){
        labelCurrentPSI.setText("Current PSI: "+ currentBeer.getCurrentPSI());
        labelCurrentVol.setText("Current CO2 Volume: " + currentBeer.getCurrentVolume());
        labelVolPerDay.setText("Average CO2 Volume Rate (from past 4 days): " + currentBeer.getAvgVolRateString());
        if(currentBeer.readyCheck() == false)
            labelReadyDate.setText("Estimated Ready Date: " + currentBeer.getReadyDateString() );
        else
            labelReadyDate.setText("Estimated Ready Date: Now ready!");
        drawGraph();
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
            "CO2 Volumes in Beer",
            "Date", "Volumes CO2",
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
        ArrayList<Beer.TrackingObject> beer = currentBeer.getTrackingArrayList();
        for(int i = 0; i < results; i++){
            dataset.addValue(beer.get(i).getVolume(), "Volumes CO2", beer.get(i).getDateString());
        }
        return dataset;
    }




    public void sentmail(String subject, String content) throws MessagingException, Exception
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
        MimeMessage message = createMimeMessage(session, "carbcap490@gmail.com", currentBeer.getEmail(), subject, content);
        Transport transport = session.getTransport();
        transport.connect("carbcap490@gmail.com", "Comp4900");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }  
    public MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String subject, String content) throws Exception 
    {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "CarbCap", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "", "UTF-8"));
        message.setSubject(subject, "UTF-8");
        message.setContent(content, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
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