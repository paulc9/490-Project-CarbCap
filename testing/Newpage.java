/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package start;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author Administrator
 */
public class Newpage extends JPanel implements ActionListener, Serializable{
    JPanel mainPanel, p1, p2, p3, container;
    Box theBox;
    private ImageIcon img;
    private JLabel showImg;
    private final static int width=400;
    private final static int height=320;
    JLabel ask, volume, name, type, date;
    JButton back, next;
    InputPage input;
    GUIResults results;
    CardLayout pages;
    Beer currentBeer;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       new Newpage(); // TODO code application logic here
    }

    public Newpage()
    {
        mainPanel=new JPanel();
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        theBox=Box.createVerticalBox();

        mainPanel.setLayout(new BorderLayout());
        p2.setLayout(new FlowLayout(FlowLayout.CENTER,200,10));

        ask=new JLabel("Is this the beer you want?");
        ask.setFont(CarbCap.titleFont);
        p1.add(ask);
        mainPanel.add(p1,BorderLayout.NORTH);

        back=new JButton("No, go back to last page");
        back.addActionListener(this);
        p2.add(back);

        next=new JButton("Yes, next page");
        next.addActionListener(this);
        p2.add(next);

        volume=new JLabel();
        name=new JLabel();
        type=new JLabel();
        date=new JLabel();
    }

    public void setPage(Beer beer){
        theBox.removeAll();
        p3.removeAll();

        currentBeer = beer;

        showImg = Util.showBeerImage(currentBeer, Newpage.width, Newpage.height);
        theBox.add(showImg);

        volume.setText("Desired CO2 volume: " + currentBeer.getDesiredVolume());
        //volume.setColumns(10);
        volume.setBorder(null);
        volume.setFont(CarbCap.labelFont);
        theBox.add(volume);
        theBox.add(Box.createVerticalStrut(5));
     
        name.setText("The beer name: " + currentBeer.getName() );
        //name.setColumns(10);
        name.setBorder(null);
        name.setFont(CarbCap.labelFont);
        theBox.add(name);
        theBox.add(Box.createVerticalStrut(5));

        type.setText("The beer type: " + currentBeer.getType());
        type.setFont(CarbCap.labelFont);
        theBox.add(type);
        theBox.add(Box.createVerticalStrut(5));
     
        date.setText("The bottle date: " + currentBeer.getBottleDateString());
        date.setFont(CarbCap.labelFont);
        theBox.add(date);
     
        p3.add(theBox);    
        mainPanel.add(p3,BorderLayout.CENTER);
        mainPanel.add(p2,BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
        this.add(mainPanel);
    }       

    public void linkPages(InputPage previous, GUIResults next, CardLayout change, JPanel main){
        input = previous;
        results = next;
        pages = change;
        container = main;
    } 

    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == back)
            pages.show(container, "Input");
        else if((JButton) action == next){
            if(Util.checkImageDirectory(currentBeer) == false){
                currentBeer.setBeerImage(Util.copyToImageDir(currentBeer));
            }
            results.setPage(currentBeer);
            results.saveNewBeer();
            pages.show(container, "Results");
        }
    }
}