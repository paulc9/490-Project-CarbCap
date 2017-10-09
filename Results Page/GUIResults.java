/**
 * Created by Bhawley on 9/27/2017.
 */
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



public class GUIResults extends JFrame {

    JLabel labelName, labelCurrentPSI, labelReadyDate, labelGraph, graphImgLabel;
    JButton buttonDelBeer;
    ImageIcon graphImg;


    public static void main(String[] args) {
        new GUIResults();
    }

    public GUIResults() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int width = (dim.width / 2);
        int height = (dim.height / 2);

        this.setSize(width, height); //sets to 1/4 screen size

        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Beer Details");

        JPanel thePanel = new JPanel();
        JPanel thePanel2 = new JPanel();
        JPanel thePanel3 = new JPanel();
        JPanel thePanel4 = new JPanel();

        thePanel.setLayout(new GridLayout(0, 1));
        thePanel2.setLayout(new GridLayout(0, 1));
        //thePanel3.setLayout(new GridLayout(0, 1));



      /*  GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.fill = GridBagConstraints.NONE;
       gridConstraints.gridheight = 1;
       gridConstraints.gridwidth = 1;
       */

        Font font = new Font("Helvetica", Font.PLAIN, 18);


        labelName = new JLabel("Name: +BEERNAME", SwingConstants.CENTER);
        labelCurrentPSI = new JLabel("Current PSI: +PSI", SwingConstants.CENTER);
        labelReadyDate = new JLabel("Estimated Ready Date: +DATE", SwingConstants.CENTER);
        labelGraph = new JLabel("Graph: ", SwingConstants.CENTER);

        graphImg = new ImageIcon(getClass().getResource("graph.jpg"));
        Image image = graphImg.getImage(); // transform it
        Image newimg = image.getScaledInstance(320, 165,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        graphImg = new ImageIcon(newimg);  // transform it back*/
        graphImgLabel = new JLabel(graphImg);


        /*ImageIcon imageIcon = new ImageIcon(getClass().getResource("graph.jpg")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back*/


        buttonDelBeer = new JButton("Delete Beer");
        buttonDelBeer.setToolTipText("Delete Current Beer Data");

        labelName.setFont(font);
        labelCurrentPSI.setFont(font);
        labelReadyDate.setFont(font);
        labelGraph.setFont(font);
        buttonDelBeer.setFont(font);
         //Box theBox = Box.createVerticalBox();



        addComp(thePanel2, labelName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelCurrentPSI, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelReadyDate, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(thePanel2, labelGraph, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        //addComp(thePanel2, graphImg, 0, 4, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        //addComp(thePanel, buttonDelBeer,0,4,1,1,GridBagConstraints.SOUTH, GridBagConstraints.NONE);
        //thePanel3.add(buttonDelBeer);

        //theBox.add(Box.createHorizontalGlue());
       // theBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        thePanel4.add(graphImgLabel);
        thePanel3.add(buttonDelBeer);



       /* thePanel.add(labelName,);
        thePanel.add(labelCurrentPSI, gridConstraints);
        thePanel.add(labelReadyDate, gridConstraints);
        thePanel.add(labelGraph, gridConstraints);
        thePanel.add(buttonDelBeer, gridConstraints);*/

        thePanel.add(thePanel2);
        thePanel.add(thePanel4);
        thePanel.add(thePanel3);
       // thePanel2.add(theBox);

        this.add(thePanel);
        this.setVisible(true);
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