import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory; 
import javax.swing.border.*;
import javax.swing.UIManager;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OptionsPage extends JPanel implements ActionListener{

	JPanel notifyPanel, emailPanel, twitterPanel, presetPanel, creditsPanel, imagePanel;
	JTabbedPane notifyTabPane;
	JLabel showImg;
	JTextField emailIn, twitterIn, presetVolume, typeIn, volumeIn, imageIn;
	Box presetBox, imageBox, listBox, presetButtonsBox;
	JComboBox presetList;
	JButton add, edit, delete, test;
	JCheckBox emailNotify, twitterDirectNotify, twitterStatusNotify;
	BeerArray presetBeers;
	Beer beer;
	DefaultComboBoxModel model;
	final String TWITTER_ICON = "images/twitter-icon.png";
	final String EMAIL_ICON = "images/email-icon.png";

	public OptionsPage(){
		notifyPanel = new JPanel();
		presetPanel = new JPanel();
		creditsPanel = new JPanel();

		RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
		rl.setFill(true);
		rl.setGap(10);
		this.setLayout(rl);

		notifyPanel.setLayout(rl);

		RelativeLayout rlPreset = new RelativeLayout(RelativeLayout.X_AXIS);
		rlPreset.setFill(true);
		rlPreset.setGap(10);
		presetPanel.setLayout(rlPreset);
		creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));

		this.setBorder(CarbCap.padding);

		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Notification Settings");
		notifyPanel.setBorder(new CompoundBorder(title, CarbCap.padding));

		TitledBorder title2 = BorderFactory.createTitledBorder("Preset Beers List");
		presetPanel.setBorder(new CompoundBorder(title2, CarbCap.padding));

		creditsPanel.setBorder(CarbCap.padding);

		makeNotifyPanel();
		makePresetPanel();
		makeCreditsPanel();

		this.add(notifyPanel, new Float(50));
		//this.add(Box.createVerticalGlue());
		this.add(presetPanel, new Float(50));
		//this.add(Box.createVerticalGlue());
		//this.add(creditsPanel);
	}

	public void makeNotifyPanel(){

		notifyTabPane = new JTabbedPane();
		ImageIcon twitterIcon = new ImageIcon(TWITTER_ICON);
		Image image = twitterIcon.getImage().getScaledInstance(36, -1, Image.SCALE_SMOOTH);
		twitterIcon = new ImageIcon(image);
		ImageIcon emailIcon = new ImageIcon(EMAIL_ICON);
		image = emailIcon.getImage().getScaledInstance(40, -1, Image.SCALE_SMOOTH);
		emailIcon = new ImageIcon(image);

		emailPanel = createEmailPanel();
		notifyTabPane.addTab("Email", emailIcon, emailPanel);
		notifyTabPane.setMnemonicAt(0, KeyEvent.VK_1);

		twitterPanel = createTwitterPanel();
		notifyTabPane.addTab("Twitter", twitterIcon, twitterPanel);
		notifyTabPane.setMnemonicAt(1, KeyEvent.VK_2);

		notifyPanel.add(notifyTabPane);
	}

	public JPanel createEmailPanel(){
		JPanel panel = new JPanel();
		RelativeLayout layout = new RelativeLayout(RelativeLayout.Y_AXIS);
		layout.setFill(true);
		panel.setLayout(layout);
		panel.setBorder(CarbCap.padding);

		JLabel emailLabel = new JLabel("Email address");

		emailIn = new JTextField(20);
		emailIn.setText(CarbCap.properties.getProperty("email"));

		emailNotify = new JCheckBox("Send email");
		emailNotify.setToolTipText("Sends a message to your email when a new beer notification occurs");
		String notifyCheck = "";

		notifyCheck = CarbCap.properties.getProperty("emailNotify");
		if(notifyCheck != null && notifyCheck.equals("true"))
			emailNotify.setSelected(true);
		else
			emailNotify.setSelected(false);

		JPanel inner1 = new JPanel();
		RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
		
		inner1.setLayout(rl);
		inner1.add(Box.createHorizontalStrut(5), new Float(10));
		inner1.add(emailLabel, new Float(35));
		inner1.add(emailIn, new Float(45));
		inner1.add(Box.createHorizontalStrut(5), new Float(10));
		panel.add(inner1, new Float(10));

		JPanel inner2 = new JPanel();
		inner2.setLayout(rl);
		inner2.add(Box.createHorizontalStrut(5), new Float(45));
		inner2.add(emailNotify, new Float(55));
		panel.add(inner2, new Float(10));

		panel.add(Box.createHorizontalStrut(5), new Float(10));

		return panel;
	}

	public JPanel createTwitterPanel(){
		JPanel panel = new JPanel();
		RelativeLayout layout = new RelativeLayout(RelativeLayout.Y_AXIS);
		layout.setFill(true);
		panel.setLayout(layout);
		panel.setBorder(CarbCap.padding);

		JLabel twitterLabel = new JLabel("Twitter username");
		JLabel symbol = new JLabel("@");

		twitterIn = new JTextField(20);
		twitterIn.setText(CarbCap.properties.getProperty("twitterUsername"));

		twitterDirectNotify = new JCheckBox("Direct messaging");
		twitterStatusNotify = new JCheckBox("Status notification");
		twitterDirectNotify.setToolTipText("Sends a direct message to your Twitter account when a new beer notification occurs");
		twitterStatusNotify.setToolTipText("Tweets a message from the CarbCap Messaging Twitter account mentioning your Twitter account when a new beer notification occurs");
		String notifyCheck = "";

		notifyCheck = CarbCap.properties.getProperty("twitterDirectNotify");
		if(notifyCheck != null && notifyCheck.equals("true"))
			twitterDirectNotify.setSelected(true);
		else
			twitterDirectNotify.setSelected(false);

		notifyCheck = CarbCap.properties.getProperty("twitterStatusNotify");
		if(notifyCheck != null && notifyCheck.equals("true"))
			twitterStatusNotify.setSelected(true);
		else
			twitterStatusNotify.setSelected(false);

		JPanel inner1 = new JPanel();
		RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
		inner1.setLayout(rl);
		inner1.add(Box.createHorizontalStrut(5), new Float(10));
		inner1.add(twitterLabel, new Float(31));
		inner1.add(symbol, new Float(4));
		inner1.add(twitterIn, new Float(45));
		inner1.add(Box.createHorizontalStrut(5), new Float(10));
		panel.add(inner1, new Float(10));

		JPanel inner2 = new JPanel();
		inner2.setLayout(rl);
		inner2.add(Box.createHorizontalStrut(5), new Float(45));
		inner2.add(twitterDirectNotify, new Float(55));
		panel.add(inner2, new Float(10));

		JPanel inner3 = new JPanel();
		inner3.setLayout(rl);
		inner3.add(Box.createHorizontalStrut(5), new Float(45));
		inner3.add(twitterStatusNotify, new Float(55));
		panel.add(inner3, new Float(10));

		return panel;
	}

	public void makePresetPanel(){
		//presetBox = Box.createHorizontalBox();
		//imageBox = Box.createVerticalBox();
		imagePanel = new JPanel();
		listBox = Box.createVerticalBox();
		presetButtonsBox = Box.createVerticalBox();

		presetBeers = BeerArray.loadPresetBeers();
		model = new DefaultComboBoxModel();
		for(Beer listBeer: presetBeers.beerArray)
			model.addElement(listBeer);
		presetList = new JComboBox(model);
		presetList.setRenderer(new BeerComboBoxRenderer());
		presetList.setMaximumSize(presetList.getPreferredSize());
		listBox.add(presetList);

		presetVolume = new JTextField(15);
		presetVolume.setEditable(false);
		presetVolume.setMaximumSize(presetList.getPreferredSize());
		presetVolume.setBackground(CarbCap.background);
		presetVolume.setForeground(CarbCap.text);

		beer = (Beer) presetList.getSelectedItem();
		showImg = Util.showBeerImage(beer, 100, -1);
		//(new Util.LoadImage(beer, 100, -1, imagePanel)).execute();
		imagePanel.add(showImg);

		if (beer != null)
			presetVolume.setText("Volume: " + beer.getDesiredVolume());
		else
			presetVolume.setText("No preset beers");
		listBox.add(presetVolume);

		presetList.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				imagePanel.removeAll();

				beer = (Beer) presetList.getSelectedItem();
				(new Util.LoadImage(beer, 100, -1, imagePanel)).execute();
				if (beer != null)
			    	presetVolume.setText("Volume: " + beer.getDesiredVolume());
			    else
			    	presetVolume.setText("No preset beers");

			    //imageBox.add(showImg);
			    listBox.revalidate();
				listBox.repaint();
			}
		});

		add = new JButton("Add new preset");
		edit = new JButton("Edit selected preset");
		delete = new JButton("Delete selected preset");
		test = new JButton("test");
		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		test.addActionListener(this);

		presetButtonsBox.add(add);
		presetButtonsBox.add(Box.createRigidArea(new Dimension(0, 10)));
		presetButtonsBox.add(edit);
		presetButtonsBox.add(Box.createRigidArea(new Dimension(0, 10)));
		presetButtonsBox.add(delete);
		//presetButtonsBox.add(test);

		presetPanel.add(imagePanel, new Float(30));
		//presetBox.add(Box.createHorizontalGlue());
		presetPanel.add(listBox, new Float(20));
		//presetBox.add(Box.createRigidArea(CarbCap.space));
		//presetBox.add(Box.createHorizontalGlue());
		presetPanel.add(presetButtonsBox, new Float(50));

		//presetPanel.add(presetBox);
	}

	public void makeCreditsPanel(){
		creditsPanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JLabel creditsTitle = new JLabel("CarbCap Program");
		JLabel credits1 = new JLabel("Created by Brendon Hawley, Tingrui Ming, and Paul Chacon with the help of: ");
		JLabel credits2 = new JLabel("(list of third-party components like Java libraries used)");
		JLabel credits3 = new JLabel(" ");
		JLabel credits4 = new JLabel("Special thanks to: (list people/groups here)");
		JLabel credits5 = new JLabel("Contact us at: (contact info here)");

		creditsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		credits1.setAlignmentX(Component.CENTER_ALIGNMENT);
		credits2.setAlignmentX(Component.CENTER_ALIGNMENT);
		credits3.setAlignmentX(Component.CENTER_ALIGNMENT);
		credits4.setAlignmentX(Component.CENTER_ALIGNMENT);
		credits5.setAlignmentX(Component.CENTER_ALIGNMENT);

		creditsPanel.add(creditsTitle);
		creditsPanel.add(credits1);
		creditsPanel.add(credits2);
		creditsPanel.add(credits3);
		creditsPanel.add(credits4);
		creditsPanel.add(credits5);
	}

	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		final ImageIcon beerIcon = new ImageIcon("images/Beer Icon.png");

		if((JButton) action == add){
			int result = JOptionPane.showConfirmDialog(null, makeDialogPanel(false, null), "Add new preset", JOptionPane.OK_CANCEL_OPTION, 2, beerIcon);
			if(result == JOptionPane.OK_OPTION){
				try{
					Double check = Double.parseDouble(volumeIn.getText());
					Beer addedBeer;

					if (typeIn.getText().isEmpty() == true){
						addedBeer = new Beer(check);
					}
					else{
						addedBeer = new Beer(typeIn.getText(), check);
					}

					addedBeer.setImageCopy(false);
					if (imageIn.getText().isEmpty() == true){
						addedBeer.setBeerImage("images/beer_10.jpg");
					}
					else{
						addedBeer.setBeerImage(imageIn.getText());
					}

					if(Util.checkImageDirectory(addedBeer) == false){
						addedBeer.setImageCopy(true);
			        }

					presetBeers.beerArray.add(addedBeer);
					model.addElement(addedBeer);
					//makeComboBox();
				} catch (NumberFormatException error){
					JOptionPane.showMessageDialog(this, "Input error. Please enter a number with or without decimals for CO2 volume.");
				}
			}
		}

		else if((JButton) action == edit){
			int result = JOptionPane.showConfirmDialog(null, makeDialogPanel(true, (Beer) presetList.getSelectedItem()), "Edit preset", JOptionPane.OK_CANCEL_OPTION, 2, beerIcon);
			if(result == JOptionPane.OK_OPTION){
				int index = presetList.getSelectedIndex();
				try{
					Double check = Double.parseDouble(volumeIn.getText());
					Beer editedBeer;

					if (typeIn.getText().isEmpty() == true){
						editedBeer = new Beer(check);
					}
					else{
						editedBeer = new Beer(typeIn.getText(), check);
					}

					editedBeer.setImageCopy(false);
					if (imageIn.getText().isEmpty() == true){
						editedBeer.setBeerImage("images/beer_10.jpg");
					}
					else{
						editedBeer.setBeerImage(imageIn.getText());
					}

					if(Util.checkImageDirectory(editedBeer) == false){
						editedBeer.setImageCopy(true);
			        }

					presetBeers.beerArray.set(index, editedBeer);
					model.removeElementAt(index);
					model.insertElementAt(editedBeer, index);
					//makeComboBox();
				} catch (NumberFormatException error){
					JOptionPane.showMessageDialog(this, "Input error. Please enter a number with or without decimals for CO2 volume.");
				}
			}
		}

		else if ((JButton) action == delete){
			int index = presetList.getSelectedIndex();
			final ImageIcon BeerIcon = new ImageIcon("images/Beer Icon.png");
        	int n = JOptionPane.showConfirmDialog(
                this,
                "<html><font size=\"4\">Are you sure you want to delete preset beer \"" + presetBeers.beerArray.get(index).getType() +
                	"\" with desired CO2 level " + presetBeers.beerArray.get(index).getDesiredVolume() + "?</font></html>",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION, 2,
                BeerIcon);
        	if (n == 0){
				presetBeers.beerArray.remove(index);
				model.removeElementAt(index);
			}
		}

// For debugging purposes, make sure preset beer array is same as one in combo box model
		else if ((JButton) action == test){
			printTest();
		}
	}

/* 
	Making panel for add preset (if edit == false)
	and for edit preset (if edit == true)
*/
	public JPanel makeDialogPanel(Boolean edit, Beer editBeer){
		JPanel ret = new JPanel();
		ret.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton imageChoose = new JButton("Choose image");

		typeIn = new JTextField(15);
		volumeIn = new JTextField(15);
		imageIn = new JTextField(15);

		JLabel type = new JLabel("Beer type name  ");
		JLabel vol = new JLabel("Desired CO2 volume  ");
		JLabel image = new JLabel("Beer image  ");

		type.setFont(CarbCap.dialogBodyFont);
		vol.setFont(CarbCap.dialogBodyFont);
		image.setFont(CarbCap.dialogBodyFont);

		imageChoose.setPreferredSize(new Dimension(120, imageIn.getPreferredSize().height));

		if(edit == true){
			typeIn.setText(editBeer.getType());
			volumeIn.setText("" + editBeer.getDesiredVolume());
			imageIn.setText(editBeer.getBeerImage());
		}

		imageChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
			    final JFileChooser fc = new JFileChooser("images/");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
				fc.setFileFilter(filter);
				fc.setAccessory(new FileChooserThumbnail(fc));

				int ret = fc.showOpenDialog(OptionsPage.this);

				if (ret == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();

					imageIn.setText(file.getPath());
				}
			}
		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 10);
		c.gridx = 0;
		c.gridy = 0;

		ret.add(type, c);
		c.gridx++;
		ret.add(typeIn, c);

		c.gridx = 0;
		c.gridy++;
		ret.add(vol, c);
		c.gridx++;
		ret.add(volumeIn, c);

		c.gridx = 0;
		c.gridy++;
		ret.add(image, c);
		c.gridx++;
		ret.add(imageIn, c);
		c.gridx = 4;
		ret.add(imageChoose, c);

		return ret;
	}

	public void saveSettings(){
		for(int i = 0; i < presetBeers.beerArray.size(); i++){
			Beer update = presetBeers.beerArray.get(i);
			if(update.getImageCopy() == true){
				update.setBeerImage(Util.copyToImageDir(update));
				presetBeers.beerArray.set(i, update);
			}
		}
		presetBeers.savePresetBeers();
		saveProperties();
	}

	public void saveProperties(){
		CarbCap.properties.setProperty("email", emailIn.getText());
		CarbCap.properties.setProperty("twitterUsername", twitterIn.getText());


		if(emailNotify.isSelected())
			CarbCap.properties.setProperty("emailNotify", "true");
		else
			CarbCap.properties.setProperty("emailNotify", "false");


		if(twitterDirectNotify.isSelected())
			CarbCap.properties.setProperty("twitterDirectNotify", "true");
		else
			CarbCap.properties.setProperty("twitterDirectNotify", "false");


		if(twitterStatusNotify.isSelected())
			CarbCap.properties.setProperty("twitterStatusNotify", "true");
		else
			CarbCap.properties.setProperty("twitterStatusNotify", "false");


		try{
			CarbCap.properties.store(new FileOutputStream(CarbCap.PROPERTIES_PATH), null);
		} catch (IOException e){
			System.out.println("Error storing properties file - halted");
		}
	}

	public void printTest(){
		for(int i = 0; i < presetBeers.beerArray.size(); i++){
			Beer b = presetBeers.beerArray.get(i);
			System.out.println("Name = " + b.getType());
			b = (Beer) model.getElementAt(i);
			System.out.println("Name = " + b.getType());
		}
		System.out.println();
	}
}