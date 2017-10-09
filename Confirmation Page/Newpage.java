/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package newpage;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Administrator
 */
public class Newpage extends JFrame{
    private ImageIcon img;
    private JLabel showImg;
    private final static int width=450;
    private final static int height=320;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       new Newpage(); // TODO code application logic here
    }
    public Newpage()
    {
     this.setSize(480,620);
     this.setLocationRelativeTo(null);
     this.setTitle("The second page");
     this.setResizable(false);
     JPanel mainPanel=new JPanel();
     
     img=new ImageIcon("./src/Image/beer_10.jpg");
     img.setImage(img.getImage().getScaledInstance(Newpage.width, Newpage.height, Image.SCALE_DEFAULT));
     showImg=new JLabel(img);
     mainPanel.add(showImg);
     
     JLabel ask=new JLabel("Is this the beer you want?");
     mainPanel.add(ask);
     
     JButton back=new JButton("No,go back to last page");
     mainPanel.add(back);
     
     JButton next=new JButton("Yes,next pgae");
     mainPanel.add(next);
     
     JTextField PSI=new JTextField("PSL: XXX");
     PSI.setColumns(10);
     PSI.setBackground(new java.awt.Color(240, 240, 240));
     PSI.setBorder(null);
     mainPanel.add(PSI);
     
     JTextField name=new JTextField("The beer name:XXX");
     name.setColumns(10);
     name.setBorder(null);
     name.setBackground(new java.awt.Color(240, 240, 240));
     mainPanel.add(name);
     
     this.add(mainPanel);
     this.setVisible(true);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }        
    
}
