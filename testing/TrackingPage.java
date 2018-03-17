import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Font;
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
import java.util.*;
import java.io.*;


public class TrackingPage extends JPanel implements ActionListener{

	JPanel mainPanel, panel1, panel2, panel3, insideScrollPane, container;
	JLabel panel1_Text, noBeersText;
	JButton newBeerButton, optionsButton, helpButton;
	Box box3;
	JScrollPane scrollPane;
	int width, height;
	GUIResults results;
	InputPage input;
	CardLayout pages;
	ArrayList<Beer> trackedBeers;


	public static void main(String[] args){
		new TrackingPage();
	}

	public TrackingPage(){
		mainPanel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		box3 = Box.createHorizontalBox();

		this.setLayout(new BorderLayout());
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
		panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel3.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainPanel.setBorder(CarbCap.padding);
		panel1.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		panel2.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		panel3.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));

		mainPanel.add(panel1);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel2);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel3);

		makePanel1();
		makePanel2();
		makePanel3();

		trackedBeers = null;

		this.add(mainPanel);
	}

	public void makePanel1(){
		panel1_Text = new JLabel("List of tracked beers");
		panel1_Text.setFont(CarbCap.titleFont);
		panel1.add(panel1_Text);
	}

	public void makePanel2(){
		scrollPane = new JScrollPane();
		insideScrollPane = new JPanel();
		scrollPane.setPreferredSize(new Dimension(this.getWidth(), (int)(CarbCap.height - CarbCap.height * 0.5)));
		insideScrollPane.setLayout(new BoxLayout(insideScrollPane, BoxLayout.PAGE_AXIS));
		scrollPane.setViewportView(insideScrollPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panel2.add(scrollPane);
	}

	public void makePanel3(){
		newBeerButton = new JButton("Create new beer");
		helpButton = new JButton("Open help guide");
		optionsButton = new JButton("Options");

		newBeerButton.setToolTipText("Create a new beer to keep track of");
		helpButton.setToolTipText("Open the CarbCap help guide PDF");
		optionsButton.setToolTipText("Adjust notification settings, change preset beers list");
		newBeerButton.addActionListener(this);
		optionsButton.addActionListener(this);
		helpButton.addActionListener(this);

		//newBeerButton.setPreferredSize(CarbCap.buttonSize);
		//optionsButton.setPreferredSize(CarbCap.buttonSize);

		box3.add(Box.createRigidArea(CarbCap.edgeSpace));
		box3.add(newBeerButton);
		box3.add(Box.createHorizontalGlue());
		box3.add(helpButton);
		box3.add(Box.createHorizontalGlue());
		box3.add(optionsButton);
		box3.add(Box.createRigidArea(CarbCap.edgeSpace));
		panel3.add(box3);
	}

	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if ((JButton) action == newBeerButton){
			input.clearFields();
			pages.show(container, "Input");
		}
		else if ((JButton) action == helpButton){
			try{
				File help = new File("CarbCap user guide.pdf");
				Desktop.getDesktop().open(help);
			} catch (Exception error){
				JOptionPane.showMessageDialog(this, "Error opening help guide.");
			}
		}
		else if ((JButton) action == optionsButton){
			OptionsPage optionsPage = new OptionsPage();
			JOptionPane options = new JOptionPane(optionsPage,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);
			JDialog dialog = options.createDialog("Settings");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			if (options.getValue() != null){
				int value = ((Integer)options.getValue()).intValue();
				if(value == JOptionPane.OK_OPTION){
					optionsPage.saveSettings();
				}
			}
		}
	}

	public void setBeerArray(ArrayList<Beer> beers){
		trackedBeers = beers;
	}

	public void displayTrackedBeers(){
		insideScrollPane.removeAll();
        if(trackedBeers == null || trackedBeers.size() == 0)
        	displayNoBeersMessage();
        else{
        	int index = 0;
        	for(Beer beer: trackedBeers){
				addBeerPanel(beer, index);
				index++;
        	}
        }
	}

	public void displayNoBeersMessage(){
		noBeersText = new JLabel("No tracked beers found");
		insideScrollPane.add(noBeersText);
		insideScrollPane.revalidate();
		insideScrollPane.repaint();
	}

	public void addBeerPanel(Beer beer, int index){
		JPanel panel = new JPanel();
		Box infoBox = Box.createHorizontalBox();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)/*new FlowLayout()*/);
		panel.setMinimumSize(new Dimension(scrollPane.getViewport().getSize().width, 200));
		//panel.setPreferredSize(new Dimension(scrollPane.getViewport().getSize().width, 200));
		panel.setBorder(new CompoundBorder(CarbCap.border, CarbCap.padding));

		//panel.add(new JLabel("added label"));
		
		panel.add(infoBox);

        JLabel showImg = Util.showBeerImage(beer, 100, -1);
        infoBox.add(showImg);

        infoBox.add(Box.createHorizontalGlue());

        Box info1 = Box.createVerticalBox();
        JLabel beerName = new JLabel("Name: " + beer.getName());
        JLabel beerType = new JLabel("Type: " + beer.getType());
        info1.add(beerName);
        info1.add(beerType);
        infoBox.add(info1);

        beerName.setFont(CarbCap.labelFont);
        beerType.setFont(CarbCap.labelFont);

        infoBox.add(Box.createHorizontalGlue());

        Box info2 = Box.createVerticalBox();
        JLabel currentVolume = new JLabel("Current CO2: " + CarbCap.df.format(beer.getCurrentVolume()));
        JLabel readyDate = new JLabel("Estimated ready date: " + beer.getReadyDateString());
        info2.add(currentVolume);
        info2.add(readyDate);
        infoBox.add(info2);

        currentVolume.setFont(CarbCap.labelFont);
        readyDate.setFont(CarbCap.labelFont);

        infoBox.add(Box.createHorizontalGlue());

        Box buttonBox = Box.createVerticalBox();
        JButton moreInfo = new JButton("More info");
        JButton delete = new JButton("Delete");
        buttonBox.add(moreInfo);
        buttonBox.add(delete);
        infoBox.add(buttonBox);

        // More info button action
        moreInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
			    results.setPage(beer, trackedBeers, index);
			    results.updatePage();
			    pages.show(container, "Results");
			}
		});

		// Delete button action
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				final ImageIcon BeerIcon = new ImageIcon("images/Beer Icon.png");
            	int n = JOptionPane.showConfirmDialog(
                    TrackingPage.this,
                    "Are you sure you want to delete your " + beer.getType() + " beer \"" + beer.getName() + "\"?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION, 2,
                    BeerIcon);
            	if (n == 0){
					trackedBeers.remove(index);
				    saveTrackedBeers();
				    displayTrackedBeers();
				}
			}
		});

		JProgressBar progress = new JProgressBar(0, (int)(beer.getDesiredVolume() * 10000));
		progress.setValue((int)beer.getCurrentVolume() * 10000);
		progress.setStringPainted(true);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(progress);
		
		insideScrollPane.add(panel);
		insideScrollPane.revalidate();
		insideScrollPane.repaint();
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

	public void linkPages(InputPage in, GUIResults res, CardLayout change, JPanel main){
		input = in;
		results = res;
		pages = change;
		container = main;
	}
}