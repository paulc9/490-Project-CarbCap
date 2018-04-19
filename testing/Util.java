import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory; 
import javax.swing.border.*;
import javax.swing.UIManager;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import java.nio.file.*;

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import twitter4j.auth.AccessToken;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Util{

//  Keys needed for Twitter notifications
    static String consumerKey = "gbSdUOCLNWr9FCQ14c2vlEF2t";
    static String consumerSecret = "QBgH6TgCRLL0S5LupIwlEvbE3cqNZVMtWmH9GGxoq3kOvxJ74f";  
    static String twitterToken = "978010248307814400-bJ2szl9L7CIZkarCybjhvB2rHwQpB6w";
    static String twitterSecret = "V0jIU93gwpQmMX3X18PDeak4HfP7xz8awTBIuIws69VcB";

/*
    Converts a Celsuis temperature to Fahrenheit.
*/
    public static Double cToF(double cTemp){
        double fTemp = (cTemp * 1.8) + 32;
        return fTemp;
    }

/*
	Returns a JLabel with the image of the beer given to it.
    If the beer isn't null, the beer's image path will be used
    in the showImage function, else a string saying the beer 
    is null will be returned.
*/
	public static JLabel showBeerImage(Beer beer, int width, int height, int scaleType){
		JLabel showImg = new JLabel();

		if (beer != null){
			showImg = showImage(beer.getBeerImage(), width, height, scaleType);
		}
	    else{
	    	showImg = new JLabel("Beer null");
	    }

	    return showImg;
	}

    public static JLabel showBeerImage(Beer beer, int width, int height){
        return showBeerImage(beer, width, height, Image.SCALE_SMOOTH);
    }

/*
    Returns a JLabel with the image of the path string given to it.
    If the string is empty, a label with "no image set" will
    be returned; else, the label will have a "No image found"
    picture (if the path doesn't exist) or the picture specified by
    the path.
*/
    public static JLabel showImage(String path, int width, int height, int scaleType){
        JLabel showImg = new JLabel();
        if (path.isEmpty() || path == null){
            showImg = new JLabel("No image set");
        }
        else{
            ImageIcon img;
            File check = new File(path);

            if(!(check.exists()))
                img = new ImageIcon("images/no_image.png");
            else
                img = new ImageIcon(path);
            img.setImage(img.getImage().getScaledInstance(width, height, scaleType));
            showImg = new JLabel(img);
        }

        return showImg;
    }

    public static JLabel showImage(String path, int width, int height){
        return showImage(path, width, height, Image.SCALE_SMOOTH);
    }

/*
    Returns a dimension object used to limit the size of JLabels in 
    GridBayLayouts. Usually used with limitLabel function after.
    String must be the longest string to be used in the column. If there
    are line breaks in the JLabel, then type the number of breaks in numNewlines;
    else, type 0. If there are line breaks, then the string must only be the longest
    line in the JLabel.
*/
    public static Dimension limitComponentDimensions(JComponent component, String string, int numNewlines){
        FontMetrics fm = component.getFontMetrics(component.getFont());
        int w = fm.stringWidth(string);
        int h = fm.getHeight();
        if(numNewlines > 0){
            int newH = h * (numNewlines + 1);
            h = newH;
        }
        return new Dimension(w, h);
    }

/*
    Limits the size of the JLabel provided using the given dimension
    object. 
*/
    public static void limitComponent(JComponent component, Dimension size){
        component.setMinimumSize(size);
        component.setPreferredSize(size);
    }

    /*
        Functions for checking if image selected exists in images directory
        and copying image to images directory if the above is false.
    */
    public static Boolean checkImageDirectory(Beer beer){
        Path imagesDir = Paths.get("images");
        File f = new File(beer.getBeerImage());

        if (!f.exists())
            return true;

        Path currentBeerImgDir = Paths.get(f.getParent());

        if (imagesDir.toAbsolutePath().equals(currentBeerImgDir.toAbsolutePath()))
            return true;
        return false;
    }

    public static String copyToImageDir(Beer beer){
        Path source = Paths.get(beer.getBeerImage());
        Path destination = Paths.get("images/" + source.toFile().getName());
        String image = beer.getBeerImage();

        // Splits file name into name and extension so that (i) can be added for duplicates and extension can be added after.
        String[] split = source.toFile().getName().split("\\.(?=[^\\.]+$)");

        int i = 1;
        while(destination.toFile().exists()){
            destination = Paths.get("images/" + split[0] + " (" + i + ")." + split[1]);
            i++;
        }

        try{
            Files.copy(source, destination);
            System.out.println("Image " + image + " copied to images directory");
            return destination.toString();
        } catch (IOException e){
            System.out.println("Error copying image " + image + " to images directory.");
            return beer.getBeerImage();
        }
    }


	/*
		Functions for sending email.
	*/
	public static void sendMail(String subject, String content, String image, String email) throws MessagingException, Exception
    {
        Properties props = new Properties();                    
        props.setProperty("mail.transport.protocol", "smtp");   
        props.setProperty("mail.smtp.host", "smtp.gmail.com");   
        props.setProperty("mail.smtp.auth", "true"); 
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        Session session = Session.getInstance(props);
        session.setDebug(true);   
        MimeMessage message = createMimeMessage(session, "carbcap490@gmail.com", email, subject, content, image);
        Transport transport = session.getTransport();
        transport.connect("carbcap490@gmail.com", "Comp4900");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }  

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String subject, String content, String image) throws Exception 
    {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "CarbCap", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "", "UTF-8"));
        message.setSubject(subject, "UTF-8");
        MimeBodyPart imageName = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(image));
        imageName.setDataHandler(dh);
        imageName.setContentID("beer image");
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");
        MimeMultipart mailBody= new MimeMultipart();
        mailBody.addBodyPart(text);
        mailBody.addBodyPart(imageName);
        mailBody.setSubType("related"); 
        message.setContent(mailBody);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }


    /*
        Functions for sending messages direct messages/posting status via Twitter.
    */

    public static void sendTwitterDirectMessage(String directMessage) throws TwitterException
    {
        String twitterName = CarbCap.properties.getProperty("twitterUsername");
        ConfigurationBuilder  builder=new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration=builder.build();
        TwitterFactory factory=new TwitterFactory(configuration);
        Twitter twitter=factory.getInstance();
        //System.out.println("key:" + twitter.getConfiguration().getOAuthConsumerKey());
        //System.out.println("secret: " + twitter.getConfiguration().getOAuthConsumerSecret());
        //twitter.setOAuthConsumer(consumerKey,consumerSecret);
        AccessToken accessToken=new AccessToken(twitterToken,twitterSecret);
        twitter.setOAuthAccessToken(accessToken);
        DirectMessage Message=twitter.sendDirectMessage(twitterName,directMessage);
    }

    public static void postStatus(String statusMessage) throws TwitterException
    {
        ConfigurationBuilder builder=new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration=builder.build();
        TwitterFactory factory=new TwitterFactory(configuration);
        Twitter twitter=factory.getInstance();
        AccessToken accessToken=new AccessToken(twitterToken,twitterSecret);
        twitter.setOAuthAccessToken(accessToken);
        twitter.updateStatus(statusMessage);  
    }        

    /*
        Functions for saving/loading tracked beers
    */
    public static void saveTrackedBeers(ArrayList<Beer> trackedBeers){
        try{
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("savedBeers.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(trackedBeers);

            out.close();
            file.close();

            System.out.println("savedBeers.ser has been serialized");
        }
        catch(IOException ex)
        {
            System.out.println("Error while saving savedBeers.ser");
        }
    }

    public static ArrayList<Beer> loadTrackedBeers(){
        ArrayList<Beer> trackedBeers = null;
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("savedBeers.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            trackedBeers = (ArrayList<Beer>)in.readObject();

            in.close();
            file.close();

            System.out.println("savedBeers.ser has been deserialized");
            return trackedBeers;
        }

        catch(IOException ex)
        {
            System.out.println("Error while loading savedBeers.ser");
            return new ArrayList<Beer>();
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
            return new ArrayList<Beer>();
        }
    }
}