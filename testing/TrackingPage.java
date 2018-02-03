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

	JPanel mainPanel, panel1, panel2, panel3, insideScrollPane;
	JLabel panel1_Text;
	JButton newBeerButton, optionsButton, helpButton;
	Box box3;
	JScrollPane scrollPane;

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
		panel1.setBorder(new CompoundBorder(CarbCap.border, CarbCap.padding));
		panel2.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		panel2.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));

		mainPanel.add(panel1);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel2);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel3);

		makePanel1();
		makePanel2();
		makePanel3();

		this.add(mainPanel);
	}

	public void makePanel1(){
		panel1_Text = new JLabel("List of tracked beers");
		panel1_Text.setFont(CarbCap.labelFont);
		panel1.add(panel1_Text);
	}

	public void makePanel2(){
		/*scrollPane = new JScrollPane();
		insideScrollPane = new JPanel();
		insideScrollPane.setLayout(new BorderLayout());
		*/
	}

	public void makePanel3(){
		newBeerButton = new JButton("Create new beer");
		optionsButton = new JButton("Options");
		newBeerButton.addActionListener(this);
		optionsButton.addActionListener(this);

		newBeerButton.setPreferredSize(CarbCap.buttonSize);
		optionsButton.setPreferredSize(CarbCap.buttonSize);

		box3.add(Box.createRigidArea(CarbCap.edgeSpace));
		box3.add(newBeerButton);
		box3.add(Box.createHorizontalGlue());
		box3.add(optionsButton);
		box3.add(Box.createRigidArea(CarbCap.edgeSpace));
		panel3.add(box3);
	}

	public void actionPerformed(ActionEvent e){

	}
}