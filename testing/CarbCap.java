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
//Hey!

public class CarbCap extends JFrame{

	CardLayout pages;
	static Font titleFont, labelFont;
	static Border border, raised, padding;
	static Dimension space, boxSpace, edgeSpace, buttonSize;
	JPanel container;
	InputPage input;
	Newpage confirm;
	GUIResults results;

	public CarbCap(){
		setGUI();
	}

	@SuppressWarnings("unchecked")
	public void setGUI(){
		//URL[] url={new URL("../lib/jdatepicker-1.3.4.jar")};
		//URLClassLoader loader = new URLClassLoader(url);
		styling();
		frameLayout();

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// creating colors, borders, fonts, box sizes, etc.
	public void styling(){
		UIManager.put("Label.foreground", new Color(228, 125, 0));

		border = BorderFactory.createLineBorder(Color.black);
		raised = BorderFactory.createRaisedBevelBorder();
		padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		titleFont = new Font("Helvetica", Font.PLAIN, 26);
		labelFont = new Font("Helvetica", Font.PLAIN, 22);

		space = new Dimension(15, 0);
		boxSpace = new Dimension(0, 30);
		edgeSpace = new Dimension(40, 0);
		buttonSize = new Dimension(100, 40);
	}

	public void frameLayout(){
		this.setTitle("CarbCap");

		Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int width = (dim.width / 2) + (dim.width / 20);
        int height = (dim.height / 2) + (dim.height / 10);
        this.setSize(width, height);

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);

		pages = new CardLayout();
		container = new JPanel();
		container.setLayout(pages);

		input = new InputPage();

		container.add(input, "Input");
		pages.show(container, "Input");

		add(container);
		//this.setResizable(false);
	}

	public static void main(String[] args){
		CarbCap frame = new CarbCap();
	}
}

//JDatePicker Calendar library
//Github link: https://github.com/JDatePicker/JDatePicker