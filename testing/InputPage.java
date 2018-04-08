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

public class InputPage extends JPanel implements ActionListener{

	JLabel startTitle, beerNameLabel, beerNameError, bottleDateLabel, bottleDateError, inputTypeLabel, inputTypeError, 
		   presetTitle, beerTypePresetLabel, volumePresetLabel,
		   customTitle, beerTypeCustomLabel, volumeCustomLabel, volumeCustomError, imageLabel;
	JTextField beerNameIn, volumePresetIn, volumeCustomIn, beerTypeIn, imagePathIn;
	JComboBox beerList;
	JButton button1, button2, savePresetButton, backButton, okButton, imageButton;
	JRadioButton presetButton, customButton;
	ButtonGroup group;
	JPanel mainPanel, imagePanel, startPanel, presetCustomContainer, presetPanel, presetImage, customPanel, customImage, buttonPanel, container;
	Box box1, box1_2, box2, box4, box4_2;
	org.jdatepicker.impl.UtilDateModel model;
	Properties p;
	JDatePanelImpl datePanel;
	JDatePickerImpl bottleDateIn;
	TrackingPage tracking;
	Newpage confirm;
	CardLayout pages;
	Beer currentBeer;
	int beerIndex;
	BeerArray presetBeers;
	DefaultComboBoxModel CBmodel;
	CardLayout panelSwitch;

	public InputPage(){
		mainPanel = new JPanel();
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
		mainPanel.setLayout(new GridBagLayout());
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
		startPanel.setLayout(new GridBagLayout());
		buttonPanel.setLayout(new GridLayout(1, 3, CarbCap.width/12, 0));

		mainPanel.setBorder(CarbCap.padding);
		imagePanel.setBorder(CarbCap.raised);
		startPanel.setBorder(CarbCap.border);
		presetCustomContainer.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		buttonPanel.setBorder(new CompoundBorder(CarbCap.raised, BorderFactory.createEmptyBorder(10, CarbCap.width/6, 10, CarbCap.width/6)));
		//customPanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		//startPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		presetCustomContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

		GridBagConstraints c = new GridBagConstraints();

		makePresetCustomContainer();
		makeImagePanel();
		makeStartPanel();
		makeButtonPanel();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.BOTH;
		mainPanel.add(imagePanel, c);
		c.gridx++;
		c.weightx = 0.9;
		mainPanel.add(startPanel, c);
		c.weighty = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.insets = new Insets(20, 0, 0, 0);
		c.gridy++;
		mainPanel.add(presetCustomContainer, c);
		c.gridy++;
		c.weighty = 0;
		mainPanel.add(new JLabel("Fields marked with * are required"), c);
		c.gridy++;
		mainPanel.add(buttonPanel, c);

		this.add(mainPanel);
	}

	public void makeImagePanel(){
		ImageIcon img = new ImageIcon("images/newBeer_3.png");
		img.setImage(img.getImage().getScaledInstance(-1, (int)(CarbCap.height * 0.2), Image.SCALE_SMOOTH));
		JLabel imgLabel = new JLabel(img);

		imagePanel.add(new JLabel("Input page"));
		imagePanel.add(imgLabel);
	}

	public void makeStartPanel(){
		startTitle = new JLabel("<html><b>Please input beer information</b></html>");
		beerNameLabel = new JLabel("Beer Name*", SwingConstants.CENTER);
		bottleDateLabel = new JLabel("Bottle Date*", SwingConstants.CENTER);
		inputTypeLabel = new JLabel("Select input type*", SwingConstants.CENTER);

		beerNameError = new JLabel(" ", SwingConstants.CENTER);
		bottleDateError = new JLabel(" ", SwingConstants.CENTER);
		inputTypeError = new JLabel(" ", SwingConstants.CENTER);

		beerNameIn = new JTextField(15);
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
		beerNameLabel.setFont(CarbCap.labelFont);
		bottleDateLabel.setFont(CarbCap.labelFont);
		inputTypeLabel.setFont(CarbCap.labelFont);
		beerNameError.setFont(CarbCap.errorFont);
		bottleDateError.setFont(CarbCap.errorFont);
		inputTypeError.setFont(CarbCap.errorFont);

		beerNameError.setForeground(CarbCap.errorColor);
		bottleDateError.setForeground(CarbCap.errorColor);
		inputTypeError.setForeground(CarbCap.errorColor);

		FontMetrics fm = inputTypeError.getFontMetrics(inputTypeError.getFont());
	    int w = fm.stringWidth("Input type selection required");	// Uses longest string in error message column as basis for error JLabel dimensions
	    int h = fm.getHeight();
	    Dimension size = new Dimension(w, h);

	    beerNameError.setMinimumSize(size);
		beerNameError.setPreferredSize(size);
		bottleDateError.setMinimumSize(size);
		bottleDateError.setPreferredSize(size);
	    inputTypeError.setMinimumSize(size);
	    inputTypeError.setPreferredSize(size);

		presetButton = new JRadioButton("Preset");
		customButton = new JRadioButton("Custom");
		presetButton.setFont(CarbCap.font);
		customButton.setFont(CarbCap.font);
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
		startPanel.add(bottleDateError, c);

		c.gridy++;
		startPanel.add(inputTypeError, c);
	}

	public void makePresetCustomContainer(){
		panelSwitch = new CardLayout();
		presetCustomContainer.setLayout(panelSwitch);
		JPanel noSelectionPanel = new JPanel();
		noSelectionPanel.add(new JLabel("No input type selected"));

		makePresetPanel();
		makeCustomPanel();

		presetCustomContainer.add(noSelectionPanel, "None");
		presetCustomContainer.add(presetPanel, "Preset");
		presetCustomContainer.add(customPanel, "Custom");
	}

	public void makePresetPanel(){
		presetPanel = new JPanel();
		presetPanel.setLayout(new GridBagLayout());

		presetTitle = new JLabel("Select a beer type from the drop-down menu below for an existing/preset beer", SwingConstants.CENTER);
		beerTypePresetLabel = new JLabel("Beer type*", SwingConstants.CENTER);
		beerList = new JComboBox();
		button1 = new JButton("Ok");
		volumePresetLabel = new JLabel("Final CO2 volume", SwingConstants.CENTER);
		volumePresetIn = new JTextField(7);

		volumePresetIn.setEditable(false);

		JPanel titleBox = new JPanel();
   		titleBox.setBackground(CarbCap.panelTitle);
   		titleBox.setLayout(new GridBagLayout());
   		titleBox.add(presetTitle);

   		presetImage = new JPanel();
   		presetImage.setBorder(CarbCap.raised);

		presetTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		beerTypePresetLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		presetTitle.setFont(CarbCap.titleFont);
		beerTypePresetLabel.setFont(CarbCap.labelFont);
		beerList.setMaximumSize(beerList.getPreferredSize());
		button1.setPreferredSize(CarbCap.buttonSize);
		button1.addActionListener(this);

		beerList.addActionListener (new ActionListener(){
			public void actionPerformed(ActionEvent e){
				presetImage.removeAll();

				Beer beer = (Beer) beerList.getSelectedItem();
				JLabel showImg = Util.showBeerImage(beer, 100, -1);
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
		c.weighty = 0.1;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.BOTH;
		presetPanel.add(titleBox, c);

		c.gridy++;
		c.gridwidth = 1;
		c.weightx = 0.1;
		c.weighty = 0.3;
		presetPanel.add(beerTypePresetLabel, c);

		c.gridy++;
		presetPanel.add(volumePresetLabel, c);

		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		c.weightx = 0.1;
		presetPanel.add(beerList, c);

		c.gridy++;
		presetPanel.add(volumePresetIn, c);

		c.gridx++;
		c.gridy = 1;
		c.weightx = 0.3;
		presetPanel.add(new JLabel(" "), c);

		c.gridx++;
		c.weightx = 0.6;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		presetPanel.add(presetImage, c);
	}

	public void makeCustomPanel(){
		customPanel = new JPanel();
		customPanel.setLayout(new GridBagLayout());

		customTitle = new JLabel("Type in the following info for a custom beer", SwingConstants.CENTER);
		volumeCustomLabel = new JLabel("Final CO2 volume*");
		volumeCustomIn = new JTextField(7);
		beerTypeCustomLabel = new JLabel("Beer type");
		beerTypeIn = new JTextField(15);
		savePresetButton = new JButton("Save info as preset");
		button2 = new JButton("Ok");
		imageLabel = new JLabel("Beer image");
		imagePathIn = new JTextField(15);
		imageButton = new JButton("Choose image");

		volumeCustomError = new JLabel(" ", SwingConstants.CENTER);

		JPanel titleBox = new JPanel();
		titleBox.setBackground(CarbCap.panelTitle);
   		titleBox.setLayout(new GridBagLayout());
   		titleBox.add(customTitle);

   		customImage = new JPanel();
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
		savePresetButton.addActionListener(this);
		button2.addActionListener(this);
		imageButton.addActionListener(this);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.BOTH;
		customPanel.add(titleBox, c);

		c.gridy++;
		c.gridwidth = 1;
		c.weightx = 0.1;
		c.weighty = 0.3;
		customPanel.add(beerTypeCustomLabel, c);

		c.gridy++;
		customPanel.add(volumeCustomLabel, c);

		c.gridy++;
		customPanel.add(imageLabel, c);

		c.gridx++;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		customPanel.add(beerTypeIn, c);

		c.gridy++;
		customPanel.add(volumeCustomIn, c);

		c.gridy++;
		customPanel.add(imagePathIn, c);

		c.gridx++;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		customPanel.add(new JLabel(" "), c);

		c.gridy++;
		customPanel.add(volumeCustomError, c);

		c.gridy++;
		customPanel.add(imageButton, c);

		c.gridx++;
		c.gridy = 1;
		c.weightx = 0.6;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
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

	public void linkPages(TrackingPage back, Newpage next, CardLayout change, JPanel main){
		tracking = back;
		confirm = next;
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
		JLabel showImg = Util.showBeerImage(beer, 100, -1);
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
				customImage.add(Util.showImage(file.getPath(), 100, -1));

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
			currentBeer = new Beer(beerNameIn.getText(), bottleDateIn.getJFormattedTextField().getText());
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
			confirm.setPage(currentBeer);
			pages.show(container, "Confirm");

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
				volumeCustomError.setText("Must type in decimal/non-decimal number");
				error = true;
			}
			else{
				try{
					Double check = Double.parseDouble(volumeCustomIn.getText());
					volumeCustomError.setText(" ");
				} catch (NumberFormatException e){
					volumeCustomError.setText("Invalid decimal/non-decimal number");
					error = true;
				}
			}
		}

		return error;
	}

	public void clearFields(){
		beerNameIn.setText("");
		Calendar today = Calendar.getInstance();
		DateLabelFormatter df = new DateLabelFormatter();
		try {
			bottleDateIn.getJFormattedTextField().setText(df.valueToString(today));
		} catch (ParseException e){
			bottleDateIn.getJFormattedTextField().setText("");
		}
		group.clearSelection();
		beerNameError.setText(" ");
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