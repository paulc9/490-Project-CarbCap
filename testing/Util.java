import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory; 
import javax.swing.border.*;
import javax.swing.UIManager;
import java.util.*;

public class Util{

/*
	Returns a JLabel with the image of the beer given to it.
	If the file specified by the beer's image string doesn't exist,
	a "No Image Available" picture will appear instead.
*/
	public static JLabel showBeerImage(Beer beer, int width, int height){
		JLabel showImg = new JLabel();

		if (beer != null){
			if(beer.getBeerImage().isEmpty() || beer.getBeerImage() == null){
				showImg = new JLabel("No image set");
			}
			else{
				ImageIcon img;
				File check = new File(beer.getBeerImage());

				if(!(check.exists()))
					img = new ImageIcon("images/no_image.png");
				else
					img = new ImageIcon(beer.getBeerImage());
				img.setImage(img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
				showImg = new JLabel(img);
			}
		}
	    else{
	    	showImg = new JLabel("Beer null");
	    }

	    return showImg;
	}
}