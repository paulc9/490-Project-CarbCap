//package start;

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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.time.LocalDateTime;

public class InputPage extends JPanel implements ActionListener{

	JLabel startTitle, beerNameLabel, beerNameError, beerIdLabel, beerIdError, bottleDateLabel, bottleDateError, inputTypeLabel, inputTypeError, 
		   presetTitle, beerTypePresetLabel, volumePresetLabel,
		   customTitle, beerTypeCustomLabel, volumeCustomLabel, volumeCustomError, imageLabel;
	JTextField beerNameIn, beerIdIn, volumePresetIn, volumeCustomIn, beerTypeIn, imagePathIn;
	JComboBox beerList;
	JButton savePresetButton, backButton, okButton, imageButton;
	JRadioButton presetButton, customButton;
	ButtonGroup group;
	JPanel mainContainer, imagePanel, startPanel, presetCustomContainer, presetPanel, presetImage, customPanel, customImage, buttonPanel, container;
	Box box1, box1_2, box2, box4, box4_2;
	org.jdatepicker.impl.UtilDateModel model;
	Properties p;
	JDatePanelImpl datePanel;
	JDatePickerImpl bottleDateIn;
	TrackingPage tracking;
	GUIResults results;
	CardLayout pages;	// reference to switch pages
	Beer currentBeer;
	int beerIndex;
	BeerArray presetBeers;
	DefaultComboBoxModel CBmodel;
	CardLayout panelSwitch;		// reference to change preset/custom beer panel
	Dimension errorSize;

	public InputPage(){
		mainContainer = new JPanel();
		imagePanel = new JPanel();
		startPanel = new JPanel();
		presetCustomContainer = new JPanel();
		buttonPanel = new JPanel();

		box1 = Box.createHorizontalBox();
		box1_2 = Box.createHorizontalBox();
		box2 = Box.createHorizontalBox();
		box4 = Box.createHorizontalBox();
		box4_2 = Box.createHorizontalBox();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		mainContainer.setLayout(new GridBagLayout());
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
		startPanel.setLayout(new GridBagLayout());
		buttonPanel.setLayout(new GridLayout(1, 3, CarbCap.width/12, 0));

		mainContainer.setBorder(CarbCap.padding);
		imagePanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		startPanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		presetCustomContainer.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		buttonPanel.setBorder(new CompoundBorder(CarbCap.raised, BorderFactory.createEmptyBorder(10, CarbCap.width/6, 10, CarbCap.width/6)));
		//customPanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		//startPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		presetCustomContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

		imagePanel.setBackground(CarbCap.altBackground);
		startPanel.setBackground(CarbCap.altBackground);
		buttonPanel.setBackground(CarbCap.altBackground);
		presetCustomContainer.setBackground(CarbCap.altBackground);

		GridBagConstraints c = new GridBagConstraints();

		makePresetCustomContainer();
		makeImagePanel();
		makeStartPanel();
		makeButtonPanel();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		mainContainer.add(imagePanel, c);

		c.gridx++;
		c.weightx = 0.9;
		//c.insets = new Insets(0, 10, 0, 0);
		mainContainer.add(startPanel, c);

		c.weighty = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		//c.insets = new Insets(20, 0, 0, 0);
		c.gridy++;
		mainContainer.add(presetCustomContainer, c);

		c.gridy++;
		c.weighty = 0;
		c.insets = new Insets(10, 0, 0, 0);
		mainContainer.add(new JLabel("Fields marked with * are required", SwingConstants.CENTER), c);

		c.gridy++;
		mainContainer.add(buttonPanel, c);

		this.add(mainContainer);
	}

	public void makeImagePanel(){
		ImageIcon img = new ImageIcon("images/newBeer_3.png");
		img.setImage(img.getImage().getScaledInstance(-1, (int)(CarbCap.height * 0.15), Image.SCALE_SMOOTH));
		JLabel imgLabel = new JLabel(img);

		JLabel text = new JLabel("New Beer");

		imgLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		text.setFont(new Font("Helvetica", Font.BOLD, 20));
		//text.setBackground(CarbCap.panelTitle);
		//text.setOpaque(true);

		imagePanel.add(text);
		imagePanel.add(imgLabel);
	}

	public void makeStartPanel(){
		startTitle = new JLabel("<html><b>Please input beer information</b></html>");
		beerNameLabel = new JLabel("Beer Name*", SwingConstants.CENTER);
		beerIdLabel = new JLabel("Beer ID*", SwingConstants.CENTER);
		bottleDateLabel = new JLabel("Bottle Date*", SwingConstants.CENTER);
		inputTypeLabel = new JLabel("Select input type*", SwingConstants.CENTER);

		beerNameError = new JLabel(" ", SwingConstants.CENTER);
		bottleDateError = new JLabel(" ", SwingConstants.CENTER);
		inputTypeError = new JLabel(" ", SwingConstants.CENTER);
		beerIdError = new JLabel(" ", SwingConstants.CENTER);

		beerNameIn = new JTextField(15);
		beerIdIn = new JTextField(15);
		model = new UtilDateModel();
		p = new Properties();
        datePanel = new JDatePanelImpl(model, p);
        bottleDateIn = new JDatePickerImpl(datePanel, new DateLabelFormatter());

   		JPanel titleBox = new JPanel();
   		titleBox.setBackground(CarbCap.panelTitle);
   		titleBox.setLayout(new GridBagLayout());
   		titleBox.add(startTitle);

		//startTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        startTitle.setFont(CarbCap.titleFont);
		beerNameLabel.setFont(CarbCap.font);
		beerIdLabel.setFont(CarbCap.font);
		bottleDateLabel.setFont(CarbCap.font);
		inputTypeLabel.setFont(CarbCap.font);
		beerNameError.setFont(CarbCap.errorFont);
		bottleDateError.setFont(CarbCap.errorFont);
		inputTypeError.setFont(CarbCap.errorFont);
		beerIdError.setFont(CarbCap.errorFont);

		beerNameError.setForeground(CarbCap.errorColor);
		bottleDateError.setForeground(CarbCap.errorColor);
		inputTypeError.setForeground(CarbCap.errorColor);
		beerIdError.setForeground(CarbCap.errorColor);

		Dimension size = Util.limitComponentDimensions(inputTypeError, "Input type selection required", 0);
	    Util.limitComponent(beerNameError, size);
		Util.limitComponent(bottleDateError, size);
	    Util.limitComponent(inputTypeError, size);
	    Util.limitComponent(beerIdError, size);

		presetButton = new JRadioButton("Preset");
		customButton = new JRadioButton("Custom");
		presetButton.setFont(CarbCap.font);
		customButton.setFont(CarbCap.font);
		presetButton.setBackground(CarbCap.altBackground);
		customButton.setBackground(CarbCap.altBackground);
		group = new ButtonGroup();
		group.add(presetButton);
		group.add(customButton);
		presetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				panelSwitch.show(presetCustomContainer, "Preset");
			}
		});
		customButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				panelSwitch.show(presetCustomContainer, "Custom");
			}
		});

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 4;
		c.weightx = 1;
		c.weighty = 0.1;
		//c.anchor = GridBagConstraints.PAGE_START;
		startPanel.add(titleBox, c);

		c.gridy++;
		c.gridwidth = 1;
		//c.insets = new Insets(0, 0, 0, 10);
		c.weightx = 0.3;
		c.weighty = 0.3;
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.anchor = GridBagConstraints.CENTER;
		startPanel.add(beerNameLabel, c);

		c.gridy++;
		startPanel.add(beerIdLabel, c);

		c.gridy++;
		startPanel.add(bottleDateLabel, c);

		c.gridy++;
		c.weightx = 0.25;
		startPanel.add(inputTypeLabel, c);

		c.gridx++;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 0.2;
		startPanel.add(beerNameIn, c);

		c.gridy++;
		startPanel.add(beerIdIn, c);

		c.gridy++;
		startPanel.add(bottleDateIn, c);

		c.gridy++;
		c.gridwidth = 1;
		c.weightx = 0.1;
		startPanel.add(presetButton, c);

		c.gridx++;
		startPanel.add(customButton, c);

		c.gridy = 1;
		c.gridx++;
		c.weightx = 0.1;
		startPanel.add(beerNameError, c);

		c.gridy++;
		startPanel.add(beerIdError, c);

		c.gridy++;
		startPanel.add(bottleDateError, c);

		c.gridy++;
		startPanel.add(inputTypeError, c);
	}

	public void makePresetCustomContainer(){
		panelSwitch = new CardLayout();
		presetCustomContainer.setLayout(panelSwitch);
		JPanel noSelectionPanel = new JPanel();
		noSelectionPanel.add(new JLabel("No input type selected"));
		noSelectionPanel.setBackground(CarbCap.altBackground);

		makeCustomPanel();
		makePresetPanel();

		presetCustomContainer.add(noSelectionPanel, "None");
		presetCustomContainer.add(presetPanel, "Preset");
		presetCustomContainer.add(customPanel, "Custom");
	}

	public void makePresetPanel(){
		presetPanel = new JPanel();
		presetPanel.setLayout(new GridBagLayout());
		presetPanel.setBackground(CarbCap.altBackground);

		presetTitle = new JLabel("Select a beer type from the drop-down menu below for a preset beer", SwingConstants.CENTER);
		beerTypePresetLabel = new JLabel("Beer type*", SwingConstants.CENTER);
		beerList = new JComboBox();
		volumePresetLabel = new JLabel("Final CO2 volume", SwingConstants.CENTER);
		volumePresetIn = new JTextField(20);

		JLabel empty = new JLabel(" ");
		Util.limitComponent(empty, errorSize);

		volumePresetIn.setEditable(false);

		JPanel titleBox = new JPanel();
   		titleBox.setBackground(CarbCap.panelTitle);
   		titleBox.setLayout(new GridBagLayout());
   		titleBox.add(presetTitle);

   		JPanel infoBox = new JPanel();
   		infoBox.setLayout(new GridBagLayout());
   		infoBox.setBackground(CarbCap.altBackground);

   		presetImage = new JPanel();
   		presetImage.setLayout(new BorderLayout());
   		presetImage.setBorder(CarbCap.raised);

		presetTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		//beerTypePresetLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		presetTitle.setFont(CarbCap.titleFont);
		beerTypePresetLabel.setFont(CarbCap.labelFont);
		volumePresetLabel.setFont(CarbCap.labelFont);

		//beerList.setMaximumSize(beerList.getPreferredSize());

		beerList.addActionListener (new ActionListener(){
			public void actionPerformed(ActionEvent e){
				presetImage.removeAll();

				Beer beer = (Beer) beerList.getSelectedItem();
				JLabel showImg = Util.showBeerImage(beer, -1, presetImage.getHeight() * 3 / 4);
				if (beer != null)
			    	volumePresetIn.setText("Volume: " + beer.getDesiredVolume());
			    else
			    	volumePresetIn.setText("No preset beers");

			    presetImage.add(showImg);

			    presetImage.revalidate();
			    presetImage.repaint();
			    beerList.revalidate();
			    beerList.repaint();
			}
		});

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		presetPanel.add(titleBox, c);

		GridBagConstraints inner = new GridBagConstraints();
		inner.gridx = 0;
		inner.gridy = 0;
		inner.weightx = 0;
		inner.weighty = 0.33;
		inner.insets = new Insets(0, 0, 0, 10);
		infoBox.add(beerTypePresetLabel, inner);

		inner.gridy++;
		infoBox.add(volumePresetLabel, inner);

		inner.gridy++;
		infoBox.add(new JLabel(" "), inner);

		inner.gridy = 0;
		inner.gridx++;
		inner.fill = GridBagConstraints.HORIZONTAL;
		inner.weightx = 0.1;
		infoBox.add(beerList, inner);

		inner.gridy++;
		infoBox.add(volumePresetIn, inner);

		inner.gridy = 0;
		inner.gridx++;
		inner.weightx = 0.2;
		inner.insets = new Insets(0, 0, 0, 23);
		infoBox.add(empty, inner);

		c.gridy++;
		c.insets = new Insets(10, 0, 0, 0);
		c.gridwidth = 1;
		c.weighty = 1;
		c.weightx = 0;
		presetPanel.add(infoBox, c);

		c.gridx++;
		c.weightx = 1;
		presetPanel.add(presetImage, c);
	}

	public void makeCustomPanel(){
		customPanel = new JPanel();
		customPanel.setLayout(new GridBagLayout());
		customPanel.setBackground(CarbCap.altBackground);

		customTitle = new JLabel("Type in the following info for a custom beer", SwingConstants.CENTER);
		volumeCustomLabel = new JLabel("Final CO2 volume*", SwingConstants.CENTER);
		volumeCustomIn = new JTextField(20);
		beerTypeCustomLabel = new JLabel("Beer type", SwingConstants.CENTER);
		beerTypeIn = new JTextField(20);
		imageLabel = new JLabel("Beer image", SwingConstants.CENTER);
		imagePathIn = new JTextField(20);
		imageButton = new JButton("Choose image");

		volumeCustomError = new JLabel(" ", SwingConstants.CENTER);

		JPanel titleBox = new JPanel();
		titleBox.setBackground(CarbCap.panelTitle);
   		titleBox.setLayout(new GridBagLayout());
   		titleBox.add(customTitle);

   		JPanel infoBox = new JPanel();
   		infoBox.setLayout(new GridBagLayout());
   		infoBox.setBackground(CarbCap.altBackground);

   		customImage = new JPanel();
   		customImage.setLayout(new BorderLayout());
   		customImage.setBorder(CarbCap.raised);

		customTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		customTitle.setFont(CarbCap.titleFont);
		beerTypeCustomLabel.setFont(CarbCap.labelFont);
		volumeCustomLabel.setFont(CarbCap.labelFont);
		imageLabel.setFont(CarbCap.labelFont);
		volumeCustomError.setFont(CarbCap.errorFont);
		volumeCustomError.setForeground(CarbCap.errorColor);
/*		beerTypeIn.setMaximumSize(beerTypeIn.getPreferredSize());
		volumeCustomIn.setMaximumSize(volumeCustomIn.getPreferredSize());
		imagePathIn.setMaximumSize(imagePathIn.getPreferredSize());
		button2.setPreferredSize(CarbCap.buttonSize);
		imageButton.setPreferredSize(CarbCap.buttonSize);*/
		imageButton.addActionListener(this);

		Dimension size = Util.limitComponentDimensions(volumeCustomLabel, "Final CO2 Volume*", 0);
		Util.limitComponent(beerTypeCustomLabel, size);
		Util.limitComponent(volumeCustomLabel, size);
		Util.limitComponent(imageLabel, size);

		errorSize = Util.limitComponentDimensions(volumeCustomError, "Must type in decimal/", 1);
		Util.limitComponent(volumeCustomError, errorSize);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		customPanel.add(titleBox, c);

		GridBagConstraints inner = new GridBagConstraints();
		inner.gridx = 0;
		inner.gridy = 0;
		inner.weightx = 0;
		inner.weighty = 0.33;
		inner.insets = new Insets(0, 0, 0, 10);
		infoBox.add(beerTypeCustomLabel, inner);

		inner.gridy++;
		infoBox.add(volumeCustomLabel, inner);

		inner.gridy++;
		infoBox.add(imageLabel, inner);

		inner.gridy = 0;
		inner.gridx++;
		inner.weightx = 0.9;
		infoBox.add(beerTypeIn, inner);

		inner.gridy++;
		infoBox.add(volumeCustomIn, inner);

		inner.gridy++;
		infoBox.add(imagePathIn, inner);

		inner.gridy = 0;
		inner.gridx++;
		inner.weightx = 0.1;
		infoBox.add(new JLabel(" "), inner);

		inner.gridy++;
		infoBox.add(volumeCustomError, inner);

		inner.gridy++;
		infoBox.add(imageButton, inner);

		c.gridy++;
		c.insets = new Insets(10, 0, 0, 0);
		c.gridwidth = 1;
		c.weighty = 1;
		c.weightx = 0;
		customPanel.add(infoBox, c);

		c.gridx++;
		c.weightx = 1;
		customPanel.add(customImage, c);
	}


	public void makeButtonPanel(){
		GridBagConstraints c = new GridBagConstraints();

		backButton = new JButton("Cancel");
		//backButton.setPreferredSize(CarbCap.buttonSize);
		backButton.addActionListener(this);

		c.gridx = 0;
		c.weightx = 0.3;
		c.weighty = 0;
		buttonPanel.add(backButton);

		okButton = new JButton("Confirm");
		//okButton.setPreferredSize(CarbCap.buttonSize);
		okButton.addActionListener(this);

		c.gridx++;
		c.ipadx = 30;
		buttonPanel.add(okButton);
	}

	public void linkPages(TrackingPage back, GUIResults next, CardLayout change, JPanel main){
		tracking = back;
		results = next;
		pages = change;
		container = main;
	}

	public void showPresetComboBox(){
		presetBeers = BeerArray.loadPresetBeers();
		CBmodel = new DefaultComboBoxModel();
		for(Beer listBeer: presetBeers.beerArray)
			CBmodel.addElement(listBeer);
		beerList.setModel(CBmodel);
		beerList.setRenderer(new BeerComboBoxRenderer());

		Beer beer = (Beer) beerList.getSelectedItem();
		JLabel showImg = Util.showBeerImage(beer, -1, presetImage.getHeight() * 3 / 4);
		if (beer != null)
	    	volumePresetIn.setText("Volume: " + beer.getDesiredVolume());
	    else
	    	volumePresetIn.setText("No preset beers");

	    presetImage.add(showImg);

	    presetImage.revalidate();
	    presetImage.repaint();
	    beerList.revalidate();
	    beerList.repaint();
	}

	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if((JButton) action == backButton){
			pages.show(container, "Tracking");
		}
		else if ((JButton) action == imageButton){
			final JFileChooser fc = new JFileChooser("images/");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
			fc.setFileFilter(filter);
			fc.setAccessory(new FileChooserThumbnail(fc));

			int ret = fc.showOpenDialog(this);

			if (ret == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();

				imagePathIn.setText(file.getPath());
				customImage.removeAll();
				customImage.add(Util.showImage(file.getPath(), -1, customImage.getHeight() * 3 / 4));

				customImage.revalidate();
				customImage.repaint();
			}
		}
		/*
		else if ((JButton) action == savePresetButton){
			try{
				Double check = Double.parseDouble(volumeCustomIn.getText());
				if (beerTypeIn.getText().isEmpty() == true){
					presetBeers.beerArray.add(new Beer(check));
					JOptionPane.showMessageDialog(this, "Custom beer \"Custom\" with CO2 volume " + check + " saved as preset");
				}
				else{
					presetBeers.beerArray.add(new Beer(beerTypeIn.getText(), check));
					JOptionPane.showMessageDialog(this, "Custom beer \"" + beerTypeIn.getText() + "\" with CO2 volume " + check + " saved as preset");
				}
				presetBeers.savePresetBeers();
			} catch (NumberFormatException error){
				JOptionPane.showMessageDialog(this, "Input error. Please enter a number with or without decimals for CO2 volume.");
			}
		}
		*/
		else if (!errorCheck()){
			currentBeer = new Beer(beerNameIn.getText(), bottleDateIn.getJFormattedTextField().getText(), Integer.parseInt(beerIdIn.getText()));
			if (presetButton.isSelected() == true){
				Beer selectedBeer = (Beer)beerList.getSelectedItem();
				currentBeer.setType(selectedBeer.getType());
				currentBeer.setDesiredVolume(selectedBeer.getDesiredVolume());
				currentBeer.setBeerImage(selectedBeer.getBeerImage());
			}
			else{
				if (beerTypeIn.getText().isEmpty())
					currentBeer.setType("Custom");
				else
					currentBeer.setType(beerTypeIn.getText());
				currentBeer.setDesiredVolume(Double.parseDouble(volumeCustomIn.getText()));
				if (imagePathIn.getText().isEmpty())
					currentBeer.setBeerImage("images/beer_10.jpg");
				else
					currentBeer.setBeerImage(imagePathIn.getText());
			}
			currentBeer.setReadyDate(21);
			/*
			confirm.setPage(currentBeer);
			pages.show(container, "Confirm");
			*/
			ImageIcon img;
			File check = new File(currentBeer.getBeerImage());
            if(!(check.exists()))
                img = new ImageIcon("images/no_image.png");
            else
                img = new ImageIcon(currentBeer.getBeerImage());
            img.setImage(img.getImage().getScaledInstance(-1, 200, Image.SCALE_SMOOTH));
			int n = JOptionPane.showConfirmDialog(
				InputPage.this,
				"<html>Is this the beer you want to track?<br>" +
					"- Beer name: " + currentBeer.getName() + "<br>" +
					"- Beer ID: " + currentBeer.getBeerId() + "<br>" +
					"- Beer type: " + currentBeer.getType() + "<br>" +
					"- Final CO2 volume: " + currentBeer.getDesiredVolume() + "<br>" +
					"- Bottle date: " + currentBeer.getBottleDateString() + "</html>",
				"Beer Confirmation",
				JOptionPane.YES_NO_OPTION, 2,
				img);
			if (n == 0){
				if(Util.checkImageDirectory(currentBeer) == false){
                	currentBeer.setBeerImage(Util.copyToImageDir(currentBeer));
            	}
            	int id = currentBeer.getBeerId();
            	if(id >= 0 && id < 3){
            		LocalDateTime now = LocalDateTime.now();
            		SensorData sensor = new SensorData();

            		try{
						sensor.renewSensorData();
						currentBeer = Util.update(currentBeer, sensor, id, now);
					} catch (Exception ex){
						System.out.println(ex.getMessage());
						System.out.println("Error with sensor data, sensor update process halted. Continuing without updating beer with sensor.");
					}
            	}

            	currentBeer = Util.notifyCheck(currentBeer);
            	results.setPage(currentBeer);
				results.saveNewBeer();
				pages.show(container, "Results");
			}

		}
	}

	public Boolean errorCheck(){
		Boolean error = false;

		if (beerNameIn.getText().isEmpty() == true){
			beerNameError.setText("Beer name missing");
			error = true;
		}
		else
			beerNameError.setText(" ");

		try{
			int i = Integer.parseInt(beerIdIn.getText());
			beerIdError.setText(" ");
		} catch (NumberFormatException e){
			beerIdError.setText("Need an integer");
			error = true;
		}

		if (bottleDateIn.getJFormattedTextField().getText().isEmpty() == true){
			bottleDateError.setText("Invalid start date");
			error = true;
		}
		else
			bottleDateError.setText(" ");

		if (presetButton.isSelected() == false && customButton.isSelected() == false){
			inputTypeError.setText("Input type selection required");
			error = true;
		}
		else
			inputTypeError.setText(" ");

		if(customButton.isSelected() == true){
			if (volumeCustomIn.getText().isEmpty() == true){
				volumeCustomError.setText("<html>Must type in decimal/<br>non-decimal number</html>");
				error = true;
			}
			else{
				try{
					Double check = Double.parseDouble(volumeCustomIn.getText());
					volumeCustomError.setText(" ");
				} catch (NumberFormatException e){
					volumeCustomError.setText("<html>Invalid decimal/<br>non-decimal number</html>");
					error = true;
				}
			}
		}

		return error;
	}

	public void clearFields(){
		beerNameIn.setText("");
		beerIdIn.setText("");
		Calendar today = Calendar.getInstance();
		DateLabelFormatter df = new DateLabelFormatter();
		try {
			bottleDateIn.getJFormattedTextField().setText(df.valueToString(today));
		} catch (ParseException e){
			bottleDateIn.getJFormattedTextField().setText("");
		}
		group.clearSelection();
		beerNameError.setText(" ");
		beerIdError.setText(" ");
		bottleDateError.setText(" ");
		inputTypeError.setText(" ");

		volumePresetIn.setText("");
		presetImage.removeAll();

		beerTypeIn.setText("");
		volumeCustomIn.setText("");
		imagePathIn.setText("");
		volumeCustomError.setText(" ");
		customImage.removeAll();

		panelSwitch.show(presetCustomContainer, "None");

	}

	// needed for calendar date selection
	public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "MM-dd-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}