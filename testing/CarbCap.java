//package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.CardLayout;
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
import java.io.*;
//Hey!

public class CarbCap extends JFrame implements Serializable{

	CardLayout pages;
	static Font titleFont, labelFont, font;
	static Border border, raised, lowered, padding;
	static Dimension space, boxSpace, edgeSpace, buttonSize;
	static int width, height;								// width and height of JFrame window
	static String PROPERTIES_PATH = "options.properties";	// path for options page values such as notification setings
	static Properties properties;
	static double DANGER_LEVEL = 4.1;						// CO2 Danger level for bottle bursting
	static Color text = new Color(242, 191, 37);
	static Color background = new Color(75, 87, 97);
	JPanel container;
	InputPage input;
	Newpage confirm;
	GUIResults results;
	TrackingPage tracking;
	SplashPage splash;
	static DecimalFormat df;								// number of decimal places shown


	public CarbCap(){
		setGUI();
	}

	@SuppressWarnings("unchecked")
	public void setGUI(){
		//URL[] url={new URL("../lib/jdatepicker-1.3.4.jar")};
		//URLClassLoader loader = new URLClassLoader(url);
		styling();
		loadProperties();
		frameLayout();

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// creating colors, borders, fonts, box sizes, etc.
	public void styling(){
		setUIDefaults();

		border = BorderFactory.createLineBorder(Color.black);
		raised = BorderFactory.createRaisedBevelBorder();
		lowered = BorderFactory.createLoweredBevelBorder();
		padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		titleFont = new Font("Helvetica", Font.PLAIN, 26);
		labelFont = new Font("Helvetica", Font.PLAIN, 22);
		font = new Font("Helvetica", Font.PLAIN, 17);

		getContentPane().setBackground(Color.gray);

		space = new Dimension(15, 0);
		boxSpace = new Dimension(0, 30);
		edgeSpace = new Dimension(40, 0);
		buttonSize = new Dimension(100, 40);

		df = new DecimalFormat("#.0000");
	}

	// setting up default colors for component backgrounds, text, etc.
	public void setUIDefaults(){
		UIManager.put("Label.foreground", text);
		UIManager.put("CheckBox.foreground", text);
		UIManager.put("OptionPane.foreground", text);
		UIManager.put("OptionPane.messageForeground", text);
		UIManager.put("TitledBorder.titleColor", text);
		UIManager.put("TabbedPane.foreground", text);
		UIManager.put("ProgressBar.foreground", new Color(216, 167, 19));
		UIManager.put("ProgressBar.selectionForeground", Color.black);

		UIManager.put("Panel.background", background);
		UIManager.put("CheckBox.background", background);
		UIManager.put("OptionPane.background", background);
		UIManager.put("TabbedPane.background", new Color(117, 136, 150));
		UIManager.put("ProgressBar.selectionBackground", background);

		UIManager.put("TabbedPane.contentAreaColor", background.darker());
		UIManager.put("TabbedPane.light", background);
		UIManager.put("TabbedPane.selected", background);
		UIManager.put("TabbedPane.borderHightlightColor", new Color(152, 177, 197));
		UIManager.put("TabbedPane.darkShadow", background.darker().darker());
		UIManager.put("TabbedPane.selectHighlight", background.darker());
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(1, 1, 3, 3));
	}

	// load user settings
	public void loadProperties(){
		properties = new Properties();
		try{
			properties.load(new FileInputStream(PROPERTIES_PATH));
		} catch (IOException e){
			
		}
	}

	public void frameLayout(){
		this.setTitle("CarbCap");

		Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        width = (dim.width / 2) + (dim.width / 20);
        height = (dim.height / 2) + (dim.height / 10);
        this.setSize(width, height);

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);

		pages = new CardLayout();
		container = new JPanel();
		container.setLayout(pages);

		input = new InputPage();
		confirm = new Newpage();
		results = new GUIResults();
		tracking = new TrackingPage();
		splash = new SplashPage();

		input.linkPages(tracking, confirm, pages, container);
		confirm.linkPages(input, results, pages, container);
		results.linkPages(tracking, pages, container);
		tracking.linkPages(input, results, pages, container);
		splash.linkPages(tracking, pages, container);

		container.add(input, "Input");
		container.add(confirm, "Confirm");
		container.add(results, "Results");
		container.add(tracking, "Tracking");
		container.add(splash, "Splash");

		File presetFile = new File("savedPresetBeers.ser");
		if (!presetFile.exists()){
			BeerArray presetBeers = new BeerArray();
			presetBeers.savePresetBeers();
		}
		File tmpFile = new File("savedBeers.ser");
		if(tmpFile.exists()){
			tracking.loadTrackedBeers();
		}
		tracking.displayTrackedBeers();

		/* Uncomment this to show app with UI properties *//*
		UIManagerDefaults c = new UIManagerDefaults();
		c.createAndShowGUI();
		/**/

		/* Show splash page *//*
		pages.show(container, "Splash");
		splash.changePage();
		/* */

		/* Skip splash page */
		pages.show(container, "Tracking");
		/* */

		add(container);
		//this.setResizable(false);

	}

	public static void main(String[] args){
		CarbCap frame = new CarbCap();
	}
}

//JDatePicker Calendar library
//Github link: https://github.com/JDatePicker/JDatePicker

//JFreeChart Graph library
//Website: http://www.jfree.org/jfreechart/

/*
	Copyright things to check:
	Notification images used on options page (Twitter, Email, etc.)
	Images used for preset beers
*/