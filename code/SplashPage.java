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
    JLabel subtitleLabel, titleLabel, img;
    final ImageIcon largeBeer;
    Box box1, box2, box3, box4;
    TrackingPage tracking;
    CardLayout pages;
    JProgressBar progress;
    JButton next;




    public SplashPage() {
        mainPanel = new JPanel();
        titleLabel = new JLabel("CarbCap", SwingConstants.CENTER);
        subtitleLabel = new JLabel("Let's Get Brewin'", SwingConstants.CENTER);
        img = new JLabel();
        largeBeer = new ImageIcon("images/splash.gif");
        box1 = box2 = box3 = Box.createVerticalBox();
        box4 = Box.createHorizontalBox();

        this.setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        mainPanel.setBorder(CarbCap.padding);
        mainPanel.setBorder(new CompoundBorder(CarbCap.border, CarbCap.padding));

        //mainPanel.add(Box.createVerticalGlue());

        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setFont(CarbCap.titleFont);
        subtitleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        subtitleLabel.setFont(CarbCap.labelFont);
        img.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        largeBeer.setImage(largeBeer.getImage().getScaledInstance(-1, CarbCap.height * 4/5, Image.SCALE_DEFAULT));
        img.setIcon(largeBeer);
        box1.add(img);
        box2.add(titleLabel);
        box3.add(subtitleLabel);

        progress = new JProgressBar(0, 100);
        box4.add(progress);
        progress.setIndeterminate(true);
        progress.setString("Loading...");
        progress.setStringPainted(true);

        next = new JButton("Loading...");
        box4.add(next);
        next.setEnabled(false);
        next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
			    pages.show(container, "Tracking");
			}
		});

        //This centers the panel on all sizes of frame
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridbag.setConstraints(mainPanel, constraints);
        mainPanel.setLayout(gridbag);



        mainPanel.add(box1);
        mainPanel.add(box2);
        mainPanel.add(box3);
        box3.add(box4);

        this.add(mainPanel); 
    }

    public void linkPages(TrackingPage track, CardLayout change, JPanel main){
        tracking = track;
        pages = change;
        container = main;
    }

    public void changePage(){
        ActionListener taskPerformer = new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                pages.show(container, "Tracking");
            }
        };
        Timer timer = new Timer(6000, taskPerformer);
        timer.setRepeats(false);
        timer.start();
    }

    public void finished(){
    	progress.setIndeterminate(false);
    	progress.setValue(100);
    	progress.setString("Loading done!");
    	next.setText("Click to continue");
    	next.setEnabled(true);
    }

    public static void main (String[] args){

        new SplashPage();
    }

}