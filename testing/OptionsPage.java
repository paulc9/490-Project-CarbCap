import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory; 
import javax.swing.border.*;
import javax.swing.UIManager;
import java.util.*;

public class OptionsPage extends JPanel{

	JPanel notifyPanel, presetPanel, creditsPanel;
	JTextField emailIn;
	Box presetBox, presetButtonsBox;
	JComboBox presetList;
	JButton add, edit, delete;

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

		notifyPanel.add(emailLabel);
		notifyPanel.add(emailIn);
	}

	public void makePresetPanel(){
		presetBox = Box.createHorizontalBox();

		Vector model = new Vector();
		BeerArray presetBeers = loadPresetBeers();

		for(Beer beer: presetBeers.beerArray)
			model.addElement(beer);
		presetList = new JComboBox(model);

		presetList.setRenderer(new BeerComboBoxRenderer());
		presetList.setMaximumSize(presetList.getPreferredSize());
		presetBox.add(presetList);

		presetBox.add(Box.createRigidArea(CarbCap.space));
		presetBox.add(Box.createHorizontalGlue());

		add = new JButton("Add new preset");
		edit = new JButton("Edit selected preset");
		delete = new JButton("Delete selected preset");

		presetButtonsBox = Box.createVerticalBox();
		presetButtonsBox.add(add);
		presetButtonsBox.add(Box.createRigidArea(new Dimension(0, 5)));
		presetButtonsBox.add(edit);
		presetButtonsBox.add(Box.createRigidArea(new Dimension(0, 5)));
		presetButtonsBox.add(delete);
		presetBox.add(presetButtonsBox);

		presetPanel.add(presetBox);
	}

	public void makeCreditsPanel(){
		creditsPanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JLabel creditsTitle = new JLabel("CarbCap Program");
		JLabel credits1 = new JLabel("Created by Brendon Hawley, Tingrui Ming, and Paul Chacon with the help of: ");
		JLabel credits2 = new JLabel("(list of third-party components like Java libraries used)");
		JLabel credits3 = new JLabel(" ");
		JLabel credits4 = new JLabel("Special thanks to: (list of senpais that noticed us)");
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

	public BeerArray loadPresetBeers(){
		BeerArray presetBeers = null;

		try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("savedPresetBeers.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            presetBeers = (BeerArray)in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        return presetBeers;
	}
}