/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package nwepage;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Administrator
 */
public class Newpage extends JFrame{
    private ImageIcon img;
    private JLabel showImg;
    private final static int width=400;
    private final static int height=320;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       new Newpage(); // TODO code application logic here
    }
    public Newpage()
    {
     Toolkit tk=Toolkit.getDefaultToolkit();
     Dimension Dim=tk.getScreenSize();
     this.setSize(Dim.width/2,Dim.height/2);
     this.setLocationRelativeTo(null);
     this.setTitle("The second page");
     this.setResizable(false);
     JPanel mainPanel=new JPanel();
     JPanel p1=new JPanel();
     JPanel p2=new JPanel();
     JPanel p3=new JPanel();
     Box theBox=Box.createVerticalBox();
     
     img=new ImageIcon("./src/Image/beer_10.jpg");
     img.setImage(img.getImage().getScaledInstance(Newpage.width, Newpage.height, Image.SCALE_DEFAULT));
     showImg=new JLabel(img);
     theBox.add(showImg);
     mainPanel.setLayout(new BorderLayout());
    
     
     JLabel ask=new JLabel("Is this the beer you want?");
     ask.setFont(new java.awt.Font("黑体", 0, 22));
     ask.setForeground(new java.awt.Color(228, 125, 0));
     p1.add(ask);
     mainPanel.add(p1,BorderLayout.NORTH);
     
     JButton back=new JButton("No,go back to last page");
     back.setFont(new java.awt.Font("黑体", 0, 22));
     back.setForeground(new java.awt.Color(228, 125, 0));
     p2.add(back);
     
     
     JButton next=new JButton("Yes,next pgae");
     next.setFont(new java.awt.Font("黑体", 0, 22));
     next.setForeground(new java.awt.Color(228, 125, 0));
     p2.add(next);
     
     JTextField PSI=new JTextField("PSL: XXX");
     PSI.setColumns(10);
     PSI.setFont(new java.awt.Font("黑体", 0, 17));
     PSI.setForeground(new java.awt.Color(228, 125, 0));
     PSI.setBackground(new java.awt.Color(240, 240, 240));
     PSI.setBorder(null);
     theBox.add(PSI);
     theBox.add(Box.createVerticalStrut(5));
     
     JTextField name=new JTextField("The beer name:XXX");
     name.setFont(new java.awt.Font("黑体", 0, 17));
     name.setForeground(new java.awt.Color(228, 125, 0));
     name.setColumns(10);
     name.setBorder(null);
     name.setBackground(new java.awt.Color(240, 240, 240));
     theBox.add(name);
     theBox.add(Box.createVerticalStrut(5));
     
     JLabel type=new JLabel();
     type.setText("the beer type:XXX");
     type.setFont(new java.awt.Font("黑体", 0, 17));
     type.setForeground(new java.awt.Color(228, 125, 0));
     theBox.add(type);
     theBox.add(Box.createVerticalStrut(5));
     
     JLabel Date=new JLabel();
     Date.setText("the bottle date:XXX");
     Date.setFont(new java.awt.Font("黑体", 0, 17));
     Date.setForeground(new java.awt.Color(228, 125, 0));
     theBox.add(Date);
     
     p3.add(theBox);    
     mainPanel.add(p3,BorderLayout.CENTER);
     p2.setLayout(new FlowLayout(FlowLayout.CENTER,200,10));
     mainPanel.add(p2,BorderLayout.SOUTH);
     this.add(mainPanel);
     this.setVisible(true);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }        
    
}