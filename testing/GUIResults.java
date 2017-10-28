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



public class GUIResults extends JPanel implements ActionListener{

    JPanel thePanel, thePanel2, thePanel3, thePanel4, container;
    JLabel labelName, labelCurrentPSI, labelDesiredPSI, labelReadyDate, labelGraph, graphImgLabel, labelManualPSI, labelBeerType, labelBottleDate;
    JButton buttonDelBeer, buttonEnter;
    ImageIcon graphImg;
    JTextField psiInput;
    Font font;
    InputPage input;
    CardLayout pages;
    Box theBox;


    public static void main(String[] args) {
        new GUIResults();
    }

    public GUIResults() {
        thePanel = new JPanel();
        thePanel2 = new JPanel();
        thePanel3 = new JPanel();
        thePanel4 = new JPanel();

        theBox = Box.createHorizontalBox();


        thePanel.setLayout(new GridLayout(0, 1));
        thePanel2.setLayout(new GridLayout(0, 1));

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

        URL url = this.getClass().getClassLoader().getResource("images/graph.jpg");
        graphImg = new ImageIcon(url);
        Image image = graphImg.getImage(); // transform it
        Image newimg = image.getScaledInstance(320, 165,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        graphImg = new ImageIcon(newimg);  // transform it back*/
        graphImgLabel = new JLabel(graphImg);

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

        thePanel4.add(graphImgLabel);

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

    public void setPage(){
        labelName.setText("Name: " + InputPage.currentBeer.getName());
        labelCurrentPSI.setText("Current PSI: +PSI");
        labelDesiredPSI.setText("Desired PSI: " + InputPage.currentBeer.getDesiredPSI());
        labelReadyDate.setText("Estimated Ready Date: +DATE" );
        labelGraph.setText("Graph: ");
        labelBeerType.setText("Beer Type: " + InputPage.currentBeer.getType());
        labelBottleDate.setText("Bottled on: " + InputPage.currentBeer.getBottleDate());
    }

    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == buttonDelBeer)
            pages.show(container, "Input");
    }

    public void linkPages(InputPage next, CardLayout change, JPanel main){
        input = next;
        pages = change;
        container = main;
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