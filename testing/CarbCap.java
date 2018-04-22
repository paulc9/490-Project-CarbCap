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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.ArrayList;
//Hey!

public class CarbCap extends JFrame implements Serializable{

	CardLayout pages;
	static Font titleFont, labelFont, font, infoFont, errorFont;
	static Border border, raised, lowered, padding;
	static Dimension space, boxSpace, edgeSpace, buttonSize;
	static int width, height;								// width and height of JFrame window
	static String PROPERTIES_PATH = "options.properties";	// path for options page values such as notification setings
	static Properties properties;
	static double DANGER_LEVEL = 4.1;						// CO2 Danger level for bottle bursting
	static Color text = new Color(242, 191, 37);
	static Color background = new Color(75, 87, 97);
	static Color altBackground = Color.gray.darker().darker();
	static Color panelTitle = new Color(16, 156, 147);
	static Color errorColor = new Color(247, 108, 108);

	static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss a");
	static DecimalFormat df = new DecimalFormat("#.0000");	// number of decimal places shown

	JPanel container;
	InputPage input;
	GUIResults results;
	TrackingPage tracking;
	SplashPage splash;


	public CarbCap(){
		setGUI();
	}

	@SuppressWarnings("unchecked")
	public void setGUI(){
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

		titleFont = new Font("Helvetica", Font.BOLD, 26);
		labelFont = new Font("Helvetica", Font.PLAIN, 22);
		font = new Font("Helvetica", Font.PLAIN, 17);
		infoFont = new Font("Helvetica", Font.PLAIN, 16);
		errorFont = new Font("Helvetica", Font.BOLD, 18);

		getContentPane().setBackground(Color.gray);

		space = new Dimension(15, 0);
		boxSpace = new Dimension(0, 30);
		edgeSpace = new Dimension(40, 0);
		buttonSize = new Dimension(100, 40);
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
		UIManager.put("RadioButton.foreground", text);

		UIManager.put("Panel.background", background);
		UIManager.put("CheckBox.background", background);
		UIManager.put("OptionPane.background", background);
		UIManager.put("TabbedPane.background", new Color(117, 136, 150));
		UIManager.put("ProgressBar.selectionBackground", background);
		UIManager.put("RadioButton.background", background);

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
		} catch (IOException e1){

		}
	}

	public void updateBeers(){
		ArrayList<Beer> trackedBeers = Util.loadTrackedBeers();
		SensorData sensor = new SensorData();
		LocalDateTime now = LocalDateTime.now();
		Boolean updateLogged = false;
		Boolean sensorBeerFound = false;

		for(Beer beer: trackedBeers){
			int id = beer.getBeerId();
			if(id >= 0 && id < 3){
				sensorBeerFound = true;
				break;
			}
		}

		if(sensorBeerFound){
			try{
				sensor.renewSensorData();
			} catch (Exception e){
				System.out.println(e.getMessage());
				System.out.println("Error with sensor data, sensor update process halted.");
				return;
			}
		}

		for(int i = 0; i < 3; i++){
			int beerIndex = -1;
			for(Beer beer: trackedBeers){
				beerIndex++;
				if(beer.getBeerId() == i && Util.updateCheck(beer.getLastUpdate(), now)){
					Beer updatedBeer = Util.update(beer, sensor, i, now);

					trackedBeers.set(beerIndex, updatedBeer);
					updateLogged = true;
					break;
				}
			}
		}

		if(updateLogged == true)
			Util.saveTrackedBeers(trackedBeers);
	}


	public void frameLayout(){
		this.setTitle("CarbCap");

		ImageIcon img = new ImageIcon("images/Beer Icon.png");
		this.setIconImage(img.getImage());

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
		results = new GUIResults();
		tracking = new TrackingPage();
		splash = new SplashPage();

		input.linkPages(tracking, results, pages, container);
		results.linkPages(tracking, pages, container);
		tracking.linkPages(input, results, pages, container);
		splash.linkPages(tracking, pages, container);

		container.add(input, "Input");
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
			updateBeers();
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
		this.setResizable(false);

	}

	public static void main(String[] args){
		CarbCap frame = new CarbCap();
	}
}

// JDatePicker Calendar library
// Github link: https://github.com/JDatePicker/JDatePicker

// JFreeChart Graph library
// Website: http://www.jfree.org/jfreechart/

// door icon
// https://www.flaticon.com/free-icon/open-exit-door_59801

// Relative layout code written by Rob Camick
// https://tips4java.wordpress.com/2008/11/02/relative-layout/

// beer bottle pic for simulator
// https://cliparts.zone/clipart/1785546

/*
	Copyright things to check:
	Notification images used on options page (Twitter, Email, etc.)
	Images used for preset beers
*/