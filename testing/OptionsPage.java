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

	JPanel notifyPanel, presetPanel, creditsPanel;
	JLabel showImg;
	JTextField emailIn, presetVolume, typeIn, volumeIn, imageIn;
	Box presetBox, imageBox, listBox, presetButtonsBox;
	JComboBox presetList;
	JButton add, edit, delete, test;
	JCheckBox emailNotify;
	BeerArray presetBeers;
	Beer beer;
	DefaultComboBoxModel model;

	public OptionsPage(){
		notifyPanel = new JPanel();
		presetPanel = new JPanel();
		creditsPanel = new JPanel();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		notifyPanel.setLayout(new GridLayout(1, 2));
		presetPanel.setLayout(new GridBagLayout());
		creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));

		this.setBorder(CarbCap.padding);

		TitledBorder title = BorderFactory.createTitledBorder("Notification Settings");
		notifyPanel.setBorder(new CompoundBorder(title, CarbCap.padding));

		TitledBorder title2 = BorderFactory.createTitledBorder("Preset Beers List");
		presetPanel.setBorder(new CompoundBorder(title2, CarbCap.padding));

		creditsPanel.setBorder(CarbCap.padding);

		makeNotifyPanel();
		makePresetPanel();
		makeCreditsPanel();

		this.add(notifyPanel);
		this.add(Box.createVerticalGlue());
		this.add(presetPanel);
		this.add(Box.createVerticalGlue());
		this.add(creditsPanel);
	}

	public void makeNotifyPanel(){
		JLabel emailLabel = new JLabel("Email");
		emailIn = new JTextField(15);
		emailIn.setText(CarbCap.properties.getProperty("email"));

		emailNotify = new JCheckBox("Enable notification");
		String emailNotifyCheck = "";
		emailNotifyCheck = CarbCap.properties.getProperty("emailNotify");
		if(emailNotifyCheck != null && emailNotifyCheck.equals("true"))
			emailNotify.setSelected(true);
		else
			emailNotify.setSelected(false);

		notifyPanel.add(emailNotify);
		notifyPanel.add(emailLabel);
		notifyPanel.add(emailIn);
	}

	public void makePresetPanel(){
		presetBox = Box.createHorizontalBox();
		imageBox = Box.createVerticalBox();
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

		beer = (Beer) presetList.getSelectedItem();
		showImg = Util.showBeerImage(beer, 100, -1);
		imageBox.add(showImg);

		if (beer != null)
			presetVolume.setText("Volume: " + beer.getDesiredVolume());
		else
			presetVolume.setText("No preset beers");
		listBox.add(presetVolume);

		presetList.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				imageBox.removeAll();

				beer = (Beer) presetList.getSelectedItem();
				showImg = Util.showBeerImage(beer, 100, -1);
				if (beer != null)
			    	presetVolume.setText("Volume: " + beer.getDesiredVolume());
			    else
			    	presetVolume.setText("No preset beers");

			    imageBox.add(showImg);
			    imageBox.revalidate();
			    imageBox.repaint();
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
		presetButtonsBox.add(test);

		presetBox.add(imageBox);
		presetBox.add(Box.createHorizontalGlue());
		presetBox.add(listBox);
		//presetBox.add(Box.createRigidArea(CarbCap.space));
		presetBox.add(Box.createHorizontalGlue());
		presetBox.add(presetButtonsBox);

		presetPanel.add(presetBox);
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

					if (imageIn.getText().isEmpty() == true){
						addedBeer.setBeerImage("images/beer_10.jpg");
					}
					else{
						addedBeer.setBeerImage(imageIn.getText());
					}

					if(Util.checkImageDirectory(addedBeer) == false){
			            addedBeer.setBeerImage(Util.copyToImageDir(addedBeer));
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

					if (imageIn.getText().isEmpty() == true){
						editedBeer.setBeerImage("images/beer_10.jpg");
					}
					else{
						editedBeer.setBeerImage(imageIn.getText());
					}

					if(Util.checkImageDirectory(editedBeer) == false){
			            editedBeer.setBeerImage(Util.copyToImageDir(editedBeer));
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
			presetBeers.beerArray.remove(index);
			model.removeElementAt(index);
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
		ret.setLayout(new BoxLayout(ret, BoxLayout.PAGE_AXIS));

		JButton imageChoose = new JButton("Choose image");

		Box typeBox = Box.createHorizontalBox();
		Box volBox = Box.createHorizontalBox();
		Box imageBox = Box.createHorizontalBox();

		typeIn = new JTextField(10);
		volumeIn = new JTextField(10);
		imageIn = new JTextField(10);

		typeIn.setMaximumSize(typeIn.getPreferredSize());
		volumeIn.setMaximumSize(volumeIn.getPreferredSize());
		imageIn.setMaximumSize(imageIn.getPreferredSize());

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

		typeBox.add(new JLabel("Beer type name  "));
		typeBox.add(typeIn);

		volBox.add(new JLabel("Desired CO2 volume  "));
		volBox.add(volumeIn);

		imageBox.add(new JLabel("Beer image  "));
		imageBox.add(imageIn);
		imageBox.add(imageChoose);

		ret.add(typeBox);
		ret.add(volBox);
		ret.add(imageBox);

		return ret;
	}

	public void saveSettings(){
		presetBeers.savePresetBeers();
		saveProperties();
	}

	public void saveProperties(){
		CarbCap.properties.setProperty("email", emailIn.getText());
		if(emailNotify.isSelected())
			CarbCap.properties.setProperty("emailNotify", "true");
		else
			CarbCap.properties.setProperty("emailNotify", "false");
		try{
			CarbCap.properties.store(new FileOutputStream(CarbCap.PROPERTIES_PATH), null);
		} catch (IOException e){
			
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