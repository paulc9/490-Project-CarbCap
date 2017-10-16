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
import java.awt.event.*;



public class GUIResults extends JFrame implements ActionListener{

    JLabel labelName, labelCurrentPSI, labelDesiredPSI, labelReadyDate, labelGraph, graphImgLabel, labelManualPSI, labelBeerType, labelBottleDate;
    JButton buttonDelBeer, buttonEnter;
    ImageIcon graphImg;
    JTextField psiInput;


    public static void main(String[] args) {
        new GUIResults();
    }

    public GUIResults() {

        UIManager.put("Label.foreground", new Color(228, 125, 0));

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int width = (dim.width / 2) + (dim.width / 20);
        int height = (dim.height / 2) + (dim.height / 10);

        this.setSize(width, height);

        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Beer Details");

        JPanel thePanel = new JPanel();
        JPanel thePanel2 = new JPanel();
        JPanel thePanel3 = new JPanel();
        JPanel thePanel4 = new JPanel();

        Box theBox = Box.createHorizontalBox();


        thePanel.setLayout(new GridLayout(0, 1));
        thePanel2.setLayout(new GridLayout(0, 1));

        Font font = new Font("Helvetica", Font.PLAIN, 22);


        labelName = new JLabel("Name: " + CarbCap.beerLabelIn.getText(), SwingConstants.CENTER);
        labelCurrentPSI = new JLabel("Current PSI: +PSI", SwingConstants.CENTER);
        labelDesiredPSI = new JLabel("Desired PSI: " + CarbCap.psiIn.getText(), SwingConstants.CENTER);
        labelReadyDate = new JLabel("Estimated Ready Date: +DATE", SwingConstants.CENTER);
        labelGraph = new JLabel("Graph: ", SwingConstants.CENTER);
        labelManualPSI = new JLabel("Manual PSI Input:", SwingConstants.CENTER);
        labelBeerType = new JLabel("Beer Type: " + CarbCap.beerTypeIn.getText(), SwingConstants.CENTER);
        labelBottleDate = new JLabel("Bottled on: " + CarbCap.bottleDateIn.getJFormattedTextField().getText(), SwingConstants.CENTER);

        psiInput = new JTextField(10);
        psiInput.setMaximumSize( psiInput.getPreferredSize() );

        graphImg = new ImageIcon(getClass().getResource("graph.jpg"));
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
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == buttonDelBeer){
            this.setVisible(false);
            CarbCap previous = new CarbCap();
            previous.setVisible(true);
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