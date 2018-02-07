import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.text.*;
import java.util.Date;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.*;
import org.jdatepicker.impl.*;
import org.jdatepicker.util.*;
import java.util.Properties;
import javax.swing.JFormattedTextField.AbstractFormatter;
import java.text.*;
import java.util.Calendar;
import java.lang.ClassLoader;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.StringBuilder;
import java.lang.String;
import java.io.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Created by Bhawley on 2/6/2018.
 */
public class SplashPage extends JPanel{

    JPanel mainPanel, panel1, container;
    JLabel letsBrewLable, carbCapLable, img;
    final ImageIcon LargeBeer;
    Box box1, box2, box3;
    InputPage input;
    GUIResults results;
    CardLayout pages;





    public SplashPage() {
        mainPanel = new JPanel();
        carbCapLable = new JLabel("CarbCap", SwingConstants.CENTER);
        letsBrewLable = new JLabel("Let's Get Brewin'", SwingConstants.CENTER);
        img = new JLabel();
        LargeBeer = new ImageIcon("images/Large Beer.png");
        box1 = box2 = box3 = Box.createVerticalBox();

        this.setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        mainPanel.setBorder(CarbCap.padding);
        mainPanel.setBorder(new CompoundBorder(CarbCap.border, CarbCap.padding));

        //mainPanel.add(Box.createVerticalGlue());

        carbCapLable.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        carbCapLable.setFont(CarbCap.titleFont);
        letsBrewLable.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        letsBrewLable.setFont(CarbCap.labelFont);
        img.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        img.setIcon(LargeBeer);
        box1.add(img);
        box2.add(carbCapLable);
        box3.add(letsBrewLable);


        //This centers the panel on all sizes of frame
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridbag.setConstraints(mainPanel, constraints);
        mainPanel.setLayout(gridbag);



        mainPanel.add(box1);
        mainPanel.add(box2);
        mainPanel.add(box3);

        this.add(mainPanel);
    }

    public void linkPages(InputPage in, GUIResults result, CardLayout change, JPanel main){
    	input = in;
    	results = result;
    	pages = change;
    	container = main;
    }

    public void changeToInput(){
    	ActionListener taskPerformer = new ActionListener(){
      		public void actionPerformed(ActionEvent evt) {
        		pages.show(container, "Input");
      		}
    	};
    	Timer timer = new Timer(3000, taskPerformer);
    	timer.setRepeats(false);
    	timer.start();
    }

    public void changeToResults(){
    	ActionListener taskPerformer = new ActionListener(){
      		public void actionPerformed(ActionEvent evt) {
        		pages.show(container, "Results");
      		}
    	};
    	Timer timer = new Timer(3000, taskPerformer);
    	timer.setRepeats(false);
    	timer.start();
    }


    public static void main (String[] args){

        new SplashPage();
    }

}