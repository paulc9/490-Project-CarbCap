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

public class Util{

/*
	Returns a JLabel with the image of the beer given to it.
	If the file specified by the beer's image string doesn't exist,
	a "No Image Available" picture will appear instead. Otherwise,
	the label will have text saying no image was set (if the string is empty)
	or the beer is null (if the beer objec doesn't exist).
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

	public static void sentmail(String subject, String content, String image, String email) throws MessagingException, Exception
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
}