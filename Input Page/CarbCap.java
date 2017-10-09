import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.BorderFactory; 
import javax.swing.border.*;
import javax.swing.UIManager;
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

public class CarbCap extends JFrame{

	@SuppressWarnings("unchecked")
	public void setGUI(){
		this.setTitle("CarbCap");
		UIManager.put("Label.foreground", new Color(228, 125, 0));

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

		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		Dimension space = new Dimension(15, 0);
		Dimension boxSpace = new Dimension(0, 30);
		Dimension edgeSpace = new Dimension(40, 0);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));

		Border border = BorderFactory.createLineBorder(Color.black);
		Border raised = BorderFactory.createRaisedBevelBorder();
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
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

		Font titleFont = new Font("Helvetica", Font.PLAIN, 26);
		Font labelFont = new Font("Helvetica", Font.PLAIN, 22);

		panel1_Text = new JLabel("Please input beer information", SwingConstants.CENTER);
		beerLabel = new JLabel("Beer Label", SwingConstants.CENTER);
		bottleDate = new JLabel("Bottle Date", SwingConstants.CENTER);
		beerIn = new JTextField(15);
		model = new UtilDateModel();
		Properties p = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl bottleDateIn = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        bottleDateIn.setBounds(220, 350, 120, 30);
		bottleDateIn.setMaximumSize(bottleDateIn.getPreferredSize());
		beerIn.setMaximumSize(beerIn.getPreferredSize());
		panel1_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel1_Text.setFont(titleFont);
		beerLabel.setFont(labelFont);
		bottleDate.setFont(labelFont);
		panel1.add(panel1_Text);
		box1.add(Box.createRigidArea(edgeSpace));
		box1.add(beerLabel);
		box1.add(Box.createRigidArea(space));
		box1.add(beerIn);
		box1.add(Box.createHorizontalGlue());
		box1.add(bottleDate);
		box1.add(Box.createRigidArea(space));
		box1.add(bottleDateIn);
		box1.add(Box.createRigidArea(edgeSpace));
		panel1.add(Box.createRigidArea(boxSpace));
		panel1.add(box1);

		panel2_Text = new JLabel("Select a beer type from the drop-down menu below", SwingConstants.CENTER);
		beerListLabel = new JLabel("Beer type", SwingConstants.CENTER);
		panel2_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		beerListLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel2_Text.setFont(titleFont);
		beerListLabel.setFont(labelFont);
		String[] beerStrings = {"Beer 1", "Beer 2", "Beer 3", "Beer 4"};
		beerList = new JComboBox(beerStrings);
		beerList.setMaximumSize(beerList.getPreferredSize());
		button1 = new JButton("Ok");
		//button1.setMaximumSize(button1.getPreferredSize());
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

		panel3_Text = new JLabel("<html><b><i>OR</i></b></html>");
		panel3_Text.setFont(labelFont);
		panel3.add(panel3_Text);

		panel4_Text = new JLabel("Type in the desired PSI level for a custom beer", SwingConstants.CENTER);
		psiLabel = new JLabel("PSI Level");
		psiIn = new JTextField(7);
		psiIn.setMaximumSize(psiIn.getPreferredSize());
		psiLabel.setFont(labelFont);
		panel4_Text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel4_Text.setFont(titleFont);
		button2 = new JButton("Ok");
		//button2.setMaximumSize(button2.getPreferredSize());
		panel4.add(panel4_Text);
		box4.add(Box.createRigidArea(edgeSpace));
		box4.add(psiLabel);
		box4.add(Box.createRigidArea(space));
		box4.add(psiIn);
		box4.add(Box.createHorizontalGlue());
		box4.add(button2);
		box4.add(Box.createRigidArea(edgeSpace));
		panel4.add(Box.createRigidArea(boxSpace));
		panel4.add(box4);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
/*
	public void mainLayout(){
		this.setTitle("Test Window");
		mainPanel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));
		Border border = BorderFactory.createLineBorder(Color.black);
		Border raised = BorderFactory.createRaisedBevelBorder();
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		mainPanel.setBorder(padding);
	}
*/
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
		CarbCap frame = new CarbCap();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setGUI();
            }
        });
	}

	JLabel panel1_Text, beerLabel, bottleDate, panel2_Text, beerListLabel, panel3_Text, panel4_Text, psiLabel;
	JTextField beerIn, psiIn;
	JComboBox beerList;
	JButton button1, button2;
	JPanel mainPanel, panel1, panel2, panel3, panel4;
	org.jdatepicker.impl.UtilDateModel model;
}