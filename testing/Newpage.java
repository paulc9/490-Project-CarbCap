/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package nwepage;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author Administrator
 */
public class Newpage extends JFrame implements ActionListener{
    private ImageIcon img;
    private JLabel showImg;
    private final static int width=400;
    private final static int height=320;
    JButton back, next;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       new Newpage(); // TODO code application logic here
    }
    public Newpage()
    {
     Toolkit tk=Toolkit.getDefaultToolkit();
     Dimension dim=tk.getScreenSize();
     int width = (dim.width / 2) + (dim.width / 20);
     int height = (dim.height / 2) + (dim.height / 10);
     this.setSize(width, height);
     this.setLocationRelativeTo(null);
     this.setTitle("Confirmation");
     this.setResizable(false);
     JPanel mainPanel=new JPanel();
     JPanel p1=new JPanel();
     JPanel p2=new JPanel();
     JPanel p3=new JPanel();
     Box theBox=Box.createVerticalBox();
     
     img=new ImageIcon("beer_10.jpg");
     img.setImage(img.getImage().getScaledInstance(Newpage.width, Newpage.height, Image.SCALE_DEFAULT));
     showImg=new JLabel(img);
     theBox.add(showImg);
     mainPanel.setLayout(new BorderLayout());
    
     
     JLabel ask=new JLabel("Is this the beer you want?");
     ask.setFont(new java.awt.Font("黑体", 0, 22));
     ask.setForeground(new java.awt.Color(228, 125, 0));
     p1.add(ask);
     mainPanel.add(p1,BorderLayout.NORTH);
     
     back=new JButton("No,go back to last page");
     back.setFont(new java.awt.Font("黑体", 0, 22));
     back.setForeground(new java.awt.Color(228, 125, 0));
     back.addActionListener(this);
     p2.add(back);
     
     
     next=new JButton("Yes,next page");
     next.setFont(new java.awt.Font("黑体", 0, 22));
     next.setForeground(new java.awt.Color(228, 125, 0));
     next.addActionListener(this);
     p2.add(next);
     
     JLabel psi=new JLabel("Desired PSI: " + CarbCap.psiIn.getText());
     //psi.setColumns(10);
     psi.setFont(new java.awt.Font("黑体", 0, 17));
     psi.setForeground(new java.awt.Color(228, 125, 0));
     psi.setBackground(new java.awt.Color(240, 240, 240));
     psi.setBorder(null);
     theBox.add(psi);
     theBox.add(Box.createVerticalStrut(5));
     
     JLabel name=new JLabel("The beer name: " + CarbCap.beerLabelIn.getText());
     name.setFont(new java.awt.Font("黑体", 0, 17));
     name.setForeground(new java.awt.Color(228, 125, 0));
     //name.setColumns(10);
     name.setBorder(null);
     name.setBackground(new java.awt.Color(240, 240, 240));
     theBox.add(name);
     theBox.add(Box.createVerticalStrut(5));
     
     JLabel type=new JLabel();
     type.setText("The beer type: " + CarbCap.beerTypeIn.getText());
     type.setFont(new java.awt.Font("黑体", 0, 17));
     type.setForeground(new java.awt.Color(228, 125, 0));
     theBox.add(type);
     theBox.add(Box.createVerticalStrut(5));
     
     JLabel date=new JLabel();
     date.setText("The bottle date: " + CarbCap.bottleDateIn.getJFormattedTextField().getText());
     date.setFont(new java.awt.Font("黑体", 0, 17));
     date.setForeground(new java.awt.Color(228, 125, 0));
     theBox.add(date);
     
     p3.add(theBox);    
     mainPanel.add(p3,BorderLayout.CENTER);
     p2.setLayout(new FlowLayout(FlowLayout.CENTER,200,10));
     mainPanel.add(p2,BorderLayout.SOUTH);
     this.add(mainPanel);
     this.setVisible(true);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }        
    
    public void actionPerformed(ActionEvent e){
        Object action = e.getSource();
        if ((JButton) action == back){
            this.setVisible(false);
            CarbCap previous = new CarbCap();
            previous.setVisible(true);
        }
        else if((JButton) action == next){
            this.setVisible(false);
            GUIResults next = new GUIResults();
            next.setVisible(true);
        }
    }
}