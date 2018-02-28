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

	JLabel panel1_Text, beerLabel, bottleDate, emailLabel, panel2_Text, beerListLabel, panel3_Text, panel4_Text, beerTypeLabel, volumeLabel, imageLabel;
	JTextField beerLabelIn, volumeIn, beerTypeIn, emailIn, imagePathIn;
	JComboBox beerList;
	JButton button1, button2, savePresetButton, backButton, imageButton;
	JPanel mainPanel, panel1, panel2, panel3, panel4, container;
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

	public InputPage(){
		mainPanel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();

		box1 = Box.createHorizontalBox();
		box1_2 = Box.createHorizontalBox();
		box2 = Box.createHorizontalBox();
		box4 = Box.createHorizontalBox();
		box4_2 = Box.createHorizontalBox();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));


		mainPanel.setBorder(CarbCap.padding);
		panel1.setBorder(new CompoundBorder(CarbCap.border, CarbCap.padding));
		panel2.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		panel4.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainPanel.add(panel1);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 125)));
		mainPanel.add(panel2);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel3);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel4);

		backButton = new JButton("Back to tracking page");
		backButton.setPreferredSize(CarbCap.buttonSize);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(this);
		mainPanel.add(backButton);

		makePanel1();
		makePanel2();
		makePanel3();
		makePanel4();

		this.add(mainPanel);
	}

	public void makePanel1(){
		panel1_Text = new JLabel("Please input beer information", SwingConstants.CENTER);
		beerLabel = new JLabel("Beer Name", SwingConstants.CENTER);
		bottleDate = new JLabel("Bottle Date", SwingConstants.CENTER);
		emailLabel = new JLabel("E-mail for notification", SwingConstants.CENTER);
		beerLabelIn = new JTextField(15);
		model = new UtilDateModel();
		p = new Properties();
        datePanel = new JDatePanelImpl(model, p);
        bottleDateIn = new JDatePickerImpl(datePanel, new DateLabelFormatter());
       	//emailIn = new JTextField(15);

		panel1_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bottleDateIn.setBounds(220, 350, 120, 30);
        panel1_Text.setFont(CarbCap.titleFont);
		beerLabel.setFont(CarbCap.labelFont);
		bottleDate.setFont(CarbCap.labelFont);
		emailLabel.setFont(CarbCap.labelFont);
		bottleDateIn.setMaximumSize(bottleDateIn.getPreferredSize());
		beerLabelIn.setMaximumSize(beerLabelIn.getPreferredSize());
		//emailIn.setMaximumSize(emailIn.getPreferredSize());

		panel1.add(panel1_Text);
		box1.add(Box.createRigidArea(CarbCap.edgeSpace));
		box1.add(beerLabel);
		box1.add(Box.createRigidArea(CarbCap.space));
		box1.add(beerLabelIn);
		box1.add(Box.createHorizontalGlue());
		box1.add(bottleDate);
		box1.add(Box.createRigidArea(CarbCap.space));
		box1.add(bottleDateIn);
		box1.add(Box.createRigidArea(CarbCap.edgeSpace));
		panel1.add(Box.createRigidArea(CarbCap.boxSpace));
		panel1.add(box1);
/*
		box1_2.add(emailLabel);
		box1_2.add(Box.createRigidArea(CarbCap.space));
		box1_2.add(emailIn);
		panel1.add(box1_2);
*/
	}

	public void makePanel2(){
		int count = 0;
		panel2_Text = new JLabel("Select a beer type from the drop-down menu below for an existing/preset beer", SwingConstants.CENTER);
		beerListLabel = new JLabel("Beer type", SwingConstants.CENTER);

		File presetFile = new File("savedPresetBeers.ser");
		if (presetFile.exists())
			presetBeers = BeerArray.loadPresetBeers();
		else{
			presetBeers = new BeerArray();
			presetBeers.savePresetBeers();
		}
		beerList = new JComboBox();
		for(Beer beer: presetBeers.beerArray)
			beerList.addItem(beer.getType());
		//String[] beerStrings = {"Beer 1", "Beer 2", "Beer 3", "Beer 4"};
		button1 = new JButton("Ok");

		panel2_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		beerListLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel2_Text.setFont(CarbCap.titleFont);
		beerListLabel.setFont(CarbCap.labelFont);
		beerList.setMaximumSize(beerList.getPreferredSize());
		button1.setPreferredSize(CarbCap.buttonSize);
		button1.addActionListener(this);

		panel2.add(panel2_Text);
		box2.add(Box.createRigidArea(CarbCap.edgeSpace));
		box2.add(beerListLabel);
		box2.add(Box.createRigidArea(CarbCap.space));
		box2.add(beerList);
		box2.add(Box.createHorizontalGlue());
		box2.add(button1);
		box2.add(Box.createRigidArea(CarbCap.edgeSpace));
		panel2.add(Box.createRigidArea(CarbCap.boxSpace));
		panel2.add(box2);
	}

	public void makePanel3(){
		panel3_Text = new JLabel("<html><b><i>OR</i></b></html>");
		panel3_Text.setFont(CarbCap.labelFont);
		panel3.add(panel3_Text);
	}

	public void makePanel4(){
		panel4_Text = new JLabel("Type in the following info for a custom beer", SwingConstants.CENTER);
		volumeLabel = new JLabel("Desired Final CO2 volume level");
		volumeIn = new JTextField(7);
		beerTypeLabel = new JLabel("Beer Type (optional)");
		beerTypeIn = new JTextField(15);
		savePresetButton = new JButton("Save info as preset");
		button2 = new JButton("Ok");
		imageLabel = new JLabel("Custom beer image");
		imagePathIn = new JTextField(15);
		imageButton = new JButton("Choose image");


		panel4_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel4_Text.setFont(CarbCap.titleFont);
		beerTypeLabel.setFont(CarbCap.labelFont);
		volumeLabel.setFont(CarbCap.labelFont);
		imageLabel.setFont(CarbCap.labelFont);
		beerTypeIn.setMaximumSize(beerTypeIn.getPreferredSize());
		volumeIn.setMaximumSize(volumeIn.getPreferredSize());
		imagePathIn.setMaximumSize(imagePathIn.getPreferredSize());
		button2.setPreferredSize(CarbCap.buttonSize);
		imageButton.setPreferredSize(CarbCap.buttonSize);
		savePresetButton.addActionListener(this);
		button2.addActionListener(this);
		imageButton.addActionListener(this);

		panel4.add(panel4_Text);

		box4.add(Box.createRigidArea(CarbCap.edgeSpace));
		box4.add(beerTypeLabel);
		box4.add(Box.createRigidArea(CarbCap.space));
		box4.add(beerTypeIn);
		box4.add(Box.createHorizontalGlue());
		box4.add(volumeLabel);
		box4.add(Box.createRigidArea(CarbCap.space));
		box4.add(volumeIn);
		box4.add(Box.createHorizontalGlue());
		box4.add(savePresetButton);
		box4.add(Box.createHorizontalGlue());
		box4.add(button2);
		box4.add(Box.createRigidArea(CarbCap.edgeSpace));

		box4_2.add(Box.createRigidArea(CarbCap.edgeSpace));
		box4_2.add(imageLabel);
		box4_2.add(Box.createRigidArea(CarbCap.space));
		box4_2.add(imagePathIn);
		box4_2.add(Box.createRigidArea(CarbCap.space));
		box4_2.add(imageButton);

		panel4.add(Box.createRigidArea(CarbCap.boxSpace));
		panel4.add(box4);
		panel4.add(box4_2);
	}

	public void linkPages(TrackingPage back, Newpage next, CardLayout change, JPanel main){
		tracking = back;
		confirm = next;
		pages = change;
		container = main;
	}

	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if((JButton) action == backButton){
			pages.show(container, "Tracking");
		}
		else if ((JButton) action == imageButton){
			final JFileChooser fc = new JFileChooser(new File(System.getProperty("user.home")));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
			fc.setFileFilter(filter);

			int ret = fc.showOpenDialog(this);

			if (ret == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();

				imagePathIn.setText(file.getPath());
			}
		}
		else if ((JButton) action == savePresetButton){
			try{
				Double check = Double.parseDouble(volumeIn.getText());
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
		else if (!errorCheck(action)){
			currentBeer = new Beer(beerLabelIn.getText(), bottleDateIn.getJFormattedTextField().getText()/*, emailIn.getText()*/);
			if ((JButton) action == button1){
				currentBeer.setType((String) beerList.getSelectedItem());
				beerIndex = beerList.getSelectedIndex();
				currentBeer.setDesiredPSI(presetBeers.beerArray.get(beerIndex).getDesiredPSI());
				currentBeer.setDesiredVolume(presetBeers.beerArray.get(beerIndex).getDesiredVolume());
				currentBeer.setBeerImage(presetBeers.beerArray.get(beerIndex).getBeerImage());
			}
			else{
				if (beerTypeIn.getText().isEmpty())
					currentBeer.setType("Custom");
				else
					currentBeer.setType(beerTypeIn.getText());
				currentBeer.setDesiredVolume(Double.parseDouble(volumeIn.getText()));
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

	public Boolean errorCheck(Object action){
		Boolean beerLabelEmpty, bottleDateEmpty, emailEmpty, beerTypeEmpty, volumeEmpty, error;
		beerLabelEmpty = bottleDateEmpty =/* emailEmpty =*/ beerTypeEmpty = volumeEmpty = error = false;
		StringBuilder message = new StringBuilder("Please input the following missing information to select this option:\n");
		if (beerLabelIn.getText().isEmpty() == true){
			beerLabelEmpty = true;
			error = true;
		}
		if (bottleDateIn.getJFormattedTextField().getText().isEmpty() == true){
			bottleDateEmpty = true;
			error = true;
		}
/*
		if (emailIn.getText().isEmpty() == true){
			emailEmpty = true;
			error = true;
		}
*/
		if (beerTypeIn.getText().isEmpty() == true)			// No error, since empty beer type means "Custom" will be used instead
			beerTypeEmpty = true;
		if (volumeIn.getText().isEmpty() == true)
			volumeEmpty = true;
		if (beerLabelEmpty)
			message.append("- Beer label\n");
		if (bottleDateEmpty)
			message.append("- Bottle date\n");
/*
		if (emailEmpty)
			message.append("- E-mail for notification\n");
*/
		if ((JButton) action == button1){
			if (error){
				JOptionPane.showMessageDialog(this, message);
				return true;
			}
		}
		else if ((JButton) action == button2){
			if (volumeEmpty){
				message.append("- Desired Final CO2 volume");
				error = true;
			}
			else{
				try{
					Double check = Double.parseDouble(volumeIn.getText());
				} catch (NumberFormatException e){
					JOptionPane.showMessageDialog(this, "Input error. Please enter a number with or without decimals for CO2 volume.");
					return true;
				}
			}
			if (error){
				JOptionPane.showMessageDialog(this, message);
				return true;
			}
		}
		return false;
	}

	public void clearFields(){
		beerLabelIn.setText("");
		volumeIn.setText("");
		beerTypeIn.setText("");
		Calendar today = Calendar.getInstance();
		DateLabelFormatter df = new DateLabelFormatter();
		try {
			bottleDateIn.getJFormattedTextField().setText(df.valueToString(today));
		} catch (ParseException e){
			bottleDateIn.getJFormattedTextField().setText("");
		}
		imagePathIn.setText("");
		//emailIn.setText("");
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