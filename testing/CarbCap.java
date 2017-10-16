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
//Hey!

public class CarbCap extends JFrame implements ActionListener{

	JLabel panel1_Text, beerLabel, bottleDate, panel2_Text, beerListLabel, panel3_Text, panel4_Text, beerTypeLabel, psiLabel;
	static JTextField beerLabelIn, psiIn, beerTypeIn;
	JComboBox beerList;
	JButton button1, button2;
	JPanel mainPanel, panel1, panel2, panel3, panel4;
	Box box1, box2, box4;
	Dimension space, boxSpace, edgeSpace, buttonSize;
	Font titleFont, labelFont;
	Border border, raised, padding;
	org.jdatepicker.impl.UtilDateModel model;
	Properties p;
	JDatePanelImpl datePanel;
	static JDatePickerImpl bottleDateIn;

	public CarbCap(){
		setGUI();
	}

	@SuppressWarnings("unchecked")
	public void setGUI(){
		//URL[] url={new URL("../lib/jdatepicker-1.3.4.jar")};
		//URLClassLoader loader = new URLClassLoader(url);
		styling();
		frameLayout();
		makePanel1();
		makePanel2();
		makePanel3();
		makePanel4();

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// creating colors, borders, fonts, etc.
	public void styling(){
		UIManager.put("Label.foreground", new Color(228, 125, 0));

		border = BorderFactory.createLineBorder(Color.black);
		raised = BorderFactory.createRaisedBevelBorder();
		padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		titleFont = new Font("Helvetica", Font.PLAIN, 26);
		labelFont = new Font("Helvetica", Font.PLAIN, 22);
	}

	public void frameLayout(){
		this.setTitle("Input Beer Information");

		Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int width = (dim.width / 2) + (dim.width / 20);
        int height = (dim.height / 2) + (dim.height / 10);
        this.setSize(width, height);

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);

		//this.setResizable(false);

		mainPanel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();

		box1 = Box.createHorizontalBox();
		box2 = Box.createHorizontalBox();
		box4 = Box.createHorizontalBox();
		space = new Dimension(15, 0);
		boxSpace = new Dimension(0, 30);
		edgeSpace = new Dimension(40, 0);
		buttonSize = new Dimension(100, 40);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));

		mainPanel.setBorder(padding);
		add(mainPanel);

		panel1.setBorder(new CompoundBorder(border, padding));
		panel2.setBorder(new CompoundBorder(raised, padding));
		panel4.setBorder(new CompoundBorder(raised, padding));
		panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(panel1);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 125)));
		mainPanel.add(panel2);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel3);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(panel4);
	}

	public void makePanel1(){
		panel1_Text = new JLabel("Please input beer information", SwingConstants.CENTER);
		beerLabel = new JLabel("Beer Label", SwingConstants.CENTER);
		bottleDate = new JLabel("Bottle Date", SwingConstants.CENTER);
		beerLabelIn = new JTextField(15);
		model = new UtilDateModel();
		p = new Properties();
        datePanel = new JDatePanelImpl(model, p);
        bottleDateIn = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		panel1_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bottleDateIn.setBounds(220, 350, 120, 30);
        panel1_Text.setFont(titleFont);
		beerLabel.setFont(labelFont);
		bottleDate.setFont(labelFont);
		bottleDateIn.setMaximumSize(bottleDateIn.getPreferredSize());
		beerLabelIn.setMaximumSize(beerLabelIn.getPreferredSize());

		panel1.add(panel1_Text);
		box1.add(Box.createRigidArea(edgeSpace));
		box1.add(beerLabel);
		box1.add(Box.createRigidArea(space));
		box1.add(beerLabelIn);
		box1.add(Box.createHorizontalGlue());
		box1.add(bottleDate);
		box1.add(Box.createRigidArea(space));
		box1.add(bottleDateIn);
		box1.add(Box.createRigidArea(edgeSpace));
		panel1.add(Box.createRigidArea(boxSpace));
		panel1.add(box1);
	}

	public void makePanel2(){
		panel2_Text = new JLabel("Select a beer type from the drop-down menu below for an existing beer", SwingConstants.CENTER);
		beerListLabel = new JLabel("Beer type", SwingConstants.CENTER);
		String[] beerStrings = {"Beer 1", "Beer 2", "Beer 3", "Beer 4"};
		beerList = new JComboBox(beerStrings);
		button1 = new JButton("Ok");

		panel2_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		beerListLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel2_Text.setFont(titleFont);
		beerListLabel.setFont(labelFont);
		beerList.setMaximumSize(beerList.getPreferredSize());
		button1.setPreferredSize(buttonSize);
		button1.addActionListener(this);

		panel2.add(panel2_Text);
		box2.add(Box.createRigidArea(edgeSpace));
		box2.add(beerListLabel);
		box2.add(Box.createRigidArea(space));
		box2.add(beerList);
		box2.add(Box.createHorizontalGlue());
		box2.add(button1);
		box2.add(Box.createRigidArea(edgeSpace));
		panel2.add(Box.createRigidArea(boxSpace));
		panel2.add(box2);
	}

	public void makePanel3(){
		panel3_Text = new JLabel("<html><b><i>OR</i></b></html>");
		panel3_Text.setFont(labelFont);
		panel3.add(panel3_Text);
	}

	public void makePanel4(){
		panel4_Text = new JLabel("Type in the following info for a custom beer", SwingConstants.CENTER);
		psiLabel = new JLabel("Desired Final PSI Level");
		psiIn = new JTextField(7);
		beerTypeLabel = new JLabel("Beer Type");
		beerTypeIn = new JTextField(15);
		button2 = new JButton("Ok");

		panel4_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel4_Text.setFont(titleFont);
		beerTypeLabel.setFont(labelFont);
		psiLabel.setFont(labelFont);
		beerTypeIn.setMaximumSize(beerTypeIn.getPreferredSize());
		psiIn.setMaximumSize(psiIn.getPreferredSize());
		button2.setPreferredSize(buttonSize);
		button2.addActionListener(this);

		panel4.add(panel4_Text);
		box4.add(Box.createRigidArea(edgeSpace));
		box4.add(beerTypeLabel);
		box4.add(Box.createRigidArea(space));
		box4.add(beerTypeIn);
		box4.add(Box.createHorizontalGlue());
		box4.add(psiLabel);
		box4.add(Box.createRigidArea(space));
		box4.add(psiIn);
		box4.add(Box.createHorizontalGlue());
		box4.add(button2);
		box4.add(Box.createRigidArea(edgeSpace));
		panel4.add(Box.createRigidArea(boxSpace));
		panel4.add(box4);
	}

	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if (!errorCheck(action)){
			if ((JButton) action == button1){
				beerTypeIn.setText((String) beerList.getSelectedItem());
				psiIn.setText(beerTypeIn.getText() + " PSI");
			}
			else{
				if (beerTypeIn.getText().isEmpty())
					beerTypeIn.setText("Custom");
			}
			this.setVisible(false);
			Newpage next = new Newpage();
			next.setVisible(true);
		}
	}

	public Boolean errorCheck(Object action){
		Boolean beerLabelEmpty, bottleDateEmpty, beerTypeEmpty, psiEmpty, error;
		beerLabelEmpty = bottleDateEmpty = beerTypeEmpty = psiEmpty = error = false;
		StringBuilder message = new StringBuilder("Please input the following missing information to select this option:\n");
		if (beerLabelIn.getText().isEmpty() == true){
			beerLabelEmpty = true;
			error = true;
		}
		if (bottleDateIn.getJFormattedTextField().getText().isEmpty() == true){
			bottleDateEmpty = true;
			error = true;
		}
		if (beerTypeIn.getText().isEmpty() == true)			// No error, since empty beer type means "Custom" will be used instead
			beerTypeEmpty = true;
		if (psiIn.getText().isEmpty() == true)
			psiEmpty = true;
		if (beerLabelEmpty)
			message.append("- Beer label\n");
		if (bottleDateEmpty)
			message.append("- Bottle date\n");
		if ((JButton) action == button1){
			if (error)
				JOptionPane.showMessageDialog(this, message);
		}
		else if ((JButton) action == button2){
			if (psiEmpty){
				message.append("- Desired Final PSI");
				error = true;
			}
			if (error)
				JOptionPane.showMessageDialog(this, message);
		}
		if (error)
			return true;
		else
			return false;
	}

	// needed for calendar date selection
	public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
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

	public static void main(String[] args){
		new CarbCap();
	}
}