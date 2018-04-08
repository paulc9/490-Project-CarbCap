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

	Box box;
	JPanel mainPanel, panel1, titlePanel, panel2, panel3, insideScrollPane, container;
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
		titlePanel = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		this.setLayout(new BorderLayout());
		mainPanel.setLayout(new GridBagLayout());
		panel1.setLayout(new GridLayout(1, 1));
		titlePanel.setLayout(new GridLayout(1, 1));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel3.setLayout(new GridBagLayout());
		panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		//titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel3.setAlignmentX(Component.CENTER_ALIGNMENT);

		//mainPanel.setBorder(CarbCap.padding);
		panel1.setBorder(CarbCap.raised);
		titlePanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		panel2.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		panel3.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));

		box = Box.createHorizontalBox();
		box.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));

		makePanel1();
		makeTitlePanel();
		makePanel2();
		makePanel3();

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;
		c.weightx = 0.05;
		mainPanel.add(panel1, c);
		c.gridx++;
		c.weightx = 0.95;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(titlePanel, c);
		c.gridx = 0;
		c.gridy++;
		//c.insets = new Insets(10, 0, 0, 0);
		c.weighty = 0.8;
		c.weightx = 0.05;
		//mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel3, c);
		c.weightx = 0.95;
		c.gridx++;
		//mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel2, c);

		trackedBeers = null;

		this.add(mainPanel);
	}

	public void makePanel1(){
		ImageIcon img = new ImageIcon("images/carbcap5.png");
		img.setImage(img.getImage().getScaledInstance(-1, (int)(CarbCap.height * 0.2), Image.SCALE_SMOOTH));
		JLabel imgLabel = new JLabel(img);
		panel1.add(imgLabel);
	}

	public void makeTitlePanel(){
		JLabel title = new JLabel("List of Tracked Beers");
		title.setFont(title.getFont().deriveFont(Font.BOLD | Font.ITALIC, 50f));
		title.setHorizontalAlignment(JLabel.CENTER);
		titlePanel.add(title);
	}

	public void makePanel2(){
		scrollPane = new JScrollPane();
		insideScrollPane = new JPanel();
		scrollPane.setPreferredSize(new Dimension(this.getWidth(), (int)(CarbCap.height * 0.6)));
		insideScrollPane.setLayout(new BoxLayout(insideScrollPane, BoxLayout.PAGE_AXIS));
		insideScrollPane.setBackground(Color.gray.brighter());
		scrollPane.setViewportView(insideScrollPane);
		scrollPane.getViewport().setBackground(insideScrollPane.getBackground());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panel2.add(scrollPane);
	}

	public void makePanel3(){
		newBeerButton = new JButton("Create new beer", new ImageIcon("images/newBeer_3.png"));
		helpButton = new JButton("Open help guide", new ImageIcon("images/guide.png"));
		optionsButton = new JButton("Options", new ImageIcon("images/settings.png"));

		newBeerButton.setToolTipText("Create a new beer to keep track of");
		helpButton.setToolTipText("Open the CarbCap help guide PDF");
		optionsButton.setToolTipText("Adjust notification settings, change preset beers list");
		newBeerButton.addActionListener(this);
		optionsButton.addActionListener(this);
		helpButton.addActionListener(this);
		newBeerButton.setIconTextGap(6);
		helpButton.setIconTextGap(6);
		optionsButton.setIconTextGap(7);
		newBeerButton.setHorizontalAlignment(SwingConstants.LEFT);
		helpButton.setHorizontalAlignment(SwingConstants.LEFT);
		optionsButton.setHorizontalAlignment(SwingConstants.LEFT);

		//newBeerButton.setPreferredSize(CarbCap.buttonSize);
		//optionsButton.setPreferredSize(CarbCap.buttonSize);

		//box3.add(Box.createRigidArea(CarbCap.edgeSpace));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.ipady = 0;
		panel3.add(newBeerButton, c);
		//c.gridy++;
		//panel3.add(Box.createVerticalGlue(), c);
		c.gridy++;
		panel3.add(helpButton, c);
		//c.gridy++;
		//panel3.add(Box.createVerticalGlue(), c);
		c.gridy++;
		panel3.add(optionsButton, c);
		//box3.add(Box.createRigidArea(CarbCap.edgeSpace));
	}

	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if ((JButton) action == newBeerButton){
			input.clearFields();
			input.showPresetComboBox();
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
		noBeersText.setFont(CarbCap.labelFont);
		noBeersText.setAlignmentX(Component.CENTER_ALIGNMENT);

		insideScrollPane.add(noBeersText);
		insideScrollPane.revalidate();
		insideScrollPane.repaint();
	}

	public void addBeerPanel(Beer beer, int index){
		JPanel panel = new JPanel();
		JPanel titleBox = new JPanel();
		JPanel middleBox = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setMinimumSize(new Dimension(scrollPane.getViewport().getSize().width, 200));
		panel.setBorder(new CompoundBorder(CarbCap.padding, BorderFactory.createLineBorder(Color.black.brighter(), 3)));
		panel.setBackground(Color.gray.brighter());

		JLabel title = new JLabel("<html><b>" + beer.getName() + "</b></html>");
		title.setFont(CarbCap.titleFont);
		titleBox.setBackground(CarbCap.panelTitle);
		titleBox.setOpaque(true);
		titleBox.setLayout(new GridBagLayout());
		/*Dimension d = new Dimension(titleBox.getPreferredSize().width, title.getPreferredSize().height);
		titleBox.setPreferredSize(d);
		d = new Dimension(titleBox.getMaximumSize().width, title.getPreferredSize().height);
		titleBox.setMaximumSize(d);*/

		GridBagConstraints c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		titleBox.add(title, c);
		panel.add(titleBox);

		middleBox.setBackground(Color.gray.darker().darker());
		middleBox.setBorder(CarbCap.padding);
		panel.add(middleBox);

		middleBox.setLayout(new GridBagLayout());
		GridBagConstraints mc = new GridBagConstraints();

		mc.fill = GridBagConstraints.HORIZONTAL;
		mc.gridx = 0;
		mc.gridy = 0;
		mc.weightx = 0.15;
        JLabel showImg = Util.showBeerImage(beer, 100, -1);
        middleBox.add(showImg, mc);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        detailsPanel.setBackground(Color.gray.darker().darker());

        JLabel typeLabel = new JLabel("Type: ");
        JLabel currentLabel = new JLabel("Current CO2 Level: ");
        JLabel desiredLabel = new JLabel("Desired CO2 Level: ");
        JLabel estimatedLabel = new JLabel("Estimated ready date: ");
        JLabel typeVal = new JLabel(beer.getType());
        JLabel currentVal = new JLabel(CarbCap.df.format(beer.getCurrentVolume()));
        JLabel desiredVal = new JLabel(CarbCap.df.format(beer.getDesiredVolume()));
        JLabel estimatedVal = new JLabel(beer.getReadyDateString());

        typeLabel.setFont(CarbCap.font);
        currentLabel.setFont(CarbCap.font);
		desiredLabel.setFont(CarbCap.font);
		estimatedLabel.setFont(CarbCap.font);
		typeVal.setFont(CarbCap.font);
		currentVal.setFont(CarbCap.font);
		desiredVal.setFont(CarbCap.font);
		estimatedVal.setFont(CarbCap.font);

		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 0;
		c.gridy = 0;
		detailsPanel.add(typeLabel, c);
		c.gridy++;
		detailsPanel.add(currentLabel, c);
		c.gridy++;
		detailsPanel.add(desiredLabel, c);
		c.gridy++;
		detailsPanel.add(estimatedLabel, c);

		c.gridy = 0;
		c.gridx++;
		c.anchor = GridBagConstraints.LINE_START;
		detailsPanel.add(typeVal, c);
		c.gridy++;
		detailsPanel.add(currentVal, c);
		c.gridy++;
		detailsPanel.add(desiredVal, c);
		c.gridy++;
		detailsPanel.add(estimatedVal, c);

		mc.gridx++;
		mc.weightx = 0.7;
		middleBox.add(detailsPanel, mc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(Color.gray.darker().darker());

        JButton moreInfo = new JButton("More info");
        JButton delete = new JButton("Delete");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        buttonPanel.add(moreInfo, c);
        c.gridy++;
        c.weighty = 0.2;
        buttonPanel.add(Box.createVerticalGlue(), c);
        c.gridy++;
        c.weighty = 1;
        buttonPanel.add(delete, c);

        mc.gridx++;
        mc.weightx = 0.15;
        middleBox.add(buttonPanel, mc);

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
		progress.setValue((int)(beer.getCurrentVolume() * 10000));
		progress.setStringPainted(true);

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