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
	JPanel mainPanel, logoPanel, titlePanel, trackingPanel, buttonPanel, insideScrollPane, container;
	JLabel noBeersText;
	JButton newBeerButton, optionsButton, helpButton, exitButton;
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
		logoPanel = new JPanel();
		titlePanel = new JPanel();
		trackingPanel = new JPanel();
		buttonPanel = new JPanel();

		this.setLayout(new BorderLayout());
		mainPanel.setLayout(new GridBagLayout());
		logoPanel.setLayout(new GridLayout(1, 1));
		titlePanel.setLayout(new GridBagLayout());
		trackingPanel.setLayout(new BoxLayout(trackingPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setLayout(new GridBagLayout());
		logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		//titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		trackingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		//mainPanel.setBorder(CarbCap.padding);
		logoPanel.setBorder(CarbCap.raised);
		titlePanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		trackingPanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));
		buttonPanel.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));

		box = Box.createHorizontalBox();
		box.setBorder(new CompoundBorder(CarbCap.raised, CarbCap.padding));

		makeLogoPanel();
		makeTitlePanel();
		makeTrackingPanel();
		makeButtonPanel();

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;
		c.weightx = 0.05;
		mainPanel.add(logoPanel, c);
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
		mainPanel.add(buttonPanel, c);
		c.weightx = 0.95;
		c.gridx++;
		//mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(trackingPanel, c);

		trackedBeers = null;

		this.add(mainPanel);
	}

	public void makeLogoPanel(){
		ImageIcon img = new ImageIcon("images/carbcap5.png");
		img.setImage(img.getImage().getScaledInstance(-1, (int)(CarbCap.height * 0.2), Image.SCALE_SMOOTH));
		JLabel imgLabel = new JLabel(img);
		logoPanel.add(imgLabel);
	}

	public void makeTitlePanel(){
		JPanel inner = new JPanel();
		inner.setLayout(new BorderLayout());
		inner.setBackground(CarbCap.background.brighter());
		Border border = BorderFactory.createCompoundBorder(CarbCap.raised, CarbCap.lowered);
		inner.setBorder(new CompoundBorder(border, CarbCap.padding));

		JLabel title = new JLabel("List of Tracked Beers");
		title.setFont(title.getFont().deriveFont(Font.BOLD | Font.ITALIC, 50f));
		title.setHorizontalAlignment(JLabel.CENTER);
		inner.add(title);
		titlePanel.add(inner);
	}

	public void makeTrackingPanel(){
		scrollPane = new JScrollPane();
		insideScrollPane = new JPanel();
		scrollPane.setPreferredSize(new Dimension(this.getWidth(), (int)(CarbCap.height * 0.6)));
		insideScrollPane.setLayout(new GridBagLayout());
		insideScrollPane.setBackground(Color.gray.brighter());
		scrollPane.setViewportView(insideScrollPane);
		scrollPane.getViewport().setBackground(insideScrollPane.getBackground());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		trackingPanel.add(scrollPane);
	}

	public void makeButtonPanel(){
		ImageIcon img = new ImageIcon("images/newBeer_3.png");
		img.setImage(img.getImage().getScaledInstance(-1, 71, Image.SCALE_SMOOTH));
		newBeerButton = new JButton("Create new beer", img);
		helpButton = new JButton("Open help guide", new ImageIcon("images/guide.png"));
		optionsButton = new JButton("Options", new ImageIcon("images/settings.png"));
		exitButton = new JButton("Exit program", new ImageIcon("images/open-exit-door.png"));

		newBeerButton.setToolTipText("Create a new beer to keep track of");
		helpButton.setToolTipText("Open the CarbCap help guide PDF");
		optionsButton.setToolTipText("Adjust notification settings, change preset beers list");
		exitButton.setToolTipText("Exit the program");
		newBeerButton.addActionListener(this);
		optionsButton.addActionListener(this);
		helpButton.addActionListener(this);
		exitButton.addActionListener(this);
		newBeerButton.setIconTextGap(6);
		helpButton.setIconTextGap(6);
		optionsButton.setIconTextGap(7);
		newBeerButton.setHorizontalAlignment(SwingConstants.LEFT);
		helpButton.setHorizontalAlignment(SwingConstants.LEFT);
		optionsButton.setHorizontalAlignment(SwingConstants.LEFT);
		exitButton.setHorizontalAlignment(SwingConstants.LEFT);

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
		buttonPanel.add(newBeerButton, c);
		//c.gridy++;
		//buttonPanel.add(Box.createVerticalGlue(), c);
		c.gridy++;
		buttonPanel.add(helpButton, c);
		//c.gridy++;
		//buttonPanel.add(Box.createVerticalGlue(), c);
		c.gridy++;
		buttonPanel.add(optionsButton, c);
		//box3.add(Box.createRigidArea(CarbCap.edgeSpace));
		c.gridy++;
		buttonPanel.add(exitButton, c);
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
				File help = new File("CarbCap User Guide.pdf");
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
		else if ((JButton) action == exitButton){
			System.exit(0);
		}
	}

	public void setBeerArray(ArrayList<Beer> beers){
		trackedBeers = beers;
	}

	public void displayTrackedBeers(){
		insideScrollPane.removeAll();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;

        if(trackedBeers == null || trackedBeers.size() == 0)
        	displayNoBeersMessage();
        else{
        	int index = 0;
        	for(Beer beer: trackedBeers){
        		System.out.println(beer.getBeerId());
				addBeerPanel(beer, index, c);
				index++;
				c.gridy++;
        	}
        }
	}

	public void displayNoBeersMessage(){
		JPanel panel = new JPanel();
		panel.setBackground(CarbCap.altBackground);
		panel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.black.brighter(), 3), CarbCap.padding));

		noBeersText = new JLabel("No tracked beers found");
		noBeersText.setFont(CarbCap.labelFont);
		noBeersText.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(noBeersText);
		insideScrollPane.add(panel);
		insideScrollPane.revalidate();
		insideScrollPane.repaint();
	}

	public void addBeerPanel(Beer beer, int index, GridBagConstraints gbc){
		JPanel panel = new JPanel();
		JPanel titleBox = new JPanel();
		JPanel middleBox = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setMinimumSize(new Dimension(scrollPane.getViewport().getSize().width, 200));
		panel.setMaximumSize(new Dimension(1, 210));
		panel.setPreferredSize(new Dimension(1, 210));
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

		middleBox.setBackground(CarbCap.altBackground);
		middleBox.setBorder(CarbCap.padding);
		panel.add(middleBox);

		RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
		middleBox.setLayout(rl);

		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(new BorderLayout());
		imgPanel.setBackground(CarbCap.altBackground);
		JLabel showImg = Util.showBeerImage(beer, -1, 100, Image.SCALE_DEFAULT);
        imgPanel.add(showImg);

        middleBox.add(imgPanel, new Float(1.5));

        JPanel detailsContainer = new JPanel();
        detailsContainer.setLayout(new GridLayout(0, 2));
        detailsContainer.setBackground(CarbCap.altBackground);

        JPanel detailsLabelPanel = new JPanel();
        detailsLabelPanel.setLayout(new GridBagLayout());
        detailsLabelPanel.setBackground(CarbCap.altBackground);

        JPanel detailsValPanel = new JPanel();
        detailsValPanel.setLayout(new GridBagLayout());
        detailsValPanel.setBackground(CarbCap.altBackground);

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

		typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		currentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		desiredLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		estimatedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		typeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		currentLabel.setHorizontalAlignment(SwingConstants.LEFT);
		desiredLabel.setHorizontalAlignment(SwingConstants.LEFT);
		estimatedLabel.setHorizontalAlignment(SwingConstants.LEFT);

		c.anchor = GridBagConstraints.EAST;
		c.weightx = 0.3;
		c.gridx = 0;
		c.gridy = 0;
		detailsLabelPanel.add(typeLabel, c);
		c.gridy++;
		detailsLabelPanel.add(currentLabel, c);
		c.gridy++;
		detailsLabelPanel.add(desiredLabel, c);
		c.gridy++;
		detailsLabelPanel.add(estimatedLabel, c);

		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.WEST;
		detailsValPanel.add(typeVal, c);
		c.gridy++;
		detailsValPanel.add(currentVal, c);
		c.gridy++;
		detailsValPanel.add(desiredVal, c);
		c.gridy++;
		detailsValPanel.add(estimatedVal, c);

		detailsContainer.add(detailsLabelPanel);
		detailsContainer.add(detailsValPanel);

		middleBox.add(detailsContainer, new Float(4));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(CarbCap.altBackground);

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

        middleBox.add(buttonPanel, new Float(1.5));

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
				    Util.saveTrackedBeers(trackedBeers);
				    displayTrackedBeers();
				}
			}
		});

		JProgressBar progress = new JProgressBar(0, (int)(beer.getDesiredVolume() * 10000));
		progress.setValue((int)(beer.getCurrentVolume() * 10000));
		progress.setStringPainted(true);

		panel.add(progress);
		
		insideScrollPane.add(panel, gbc);
		insideScrollPane.revalidate();
		insideScrollPane.repaint();
	}

	public void loadTrackedBeers(){
        trackedBeers = Util.loadTrackedBeers();
    }

	public void linkPages(InputPage in, GUIResults res, CardLayout change, JPanel main){
		input = in;
		results = res;
		pages = change;
		container = main;
	}
}