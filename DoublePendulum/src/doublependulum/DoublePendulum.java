/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doublependulum;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.JFrame;

/**
 *
 * @author Kyungbin
 */
public class DoublePendulum extends JFrame {
    int xStart = 350;
    int yStart = 150;
    double l1 = 150; 
    double l2 = 150;
    double m1 = 30;
    double m2 = 30;
    static double angle1= Math.PI/2; 
    static double angle2 = Math.PI/2; 
    double angle1Vel = 0;
    double angle2Vel = 0;
    double g = 1;
    static Point px2;
    static Point py2; 
    Weight weight1 = new Weight(l1,angle1);
    Weight weight2 = new Weight(l2,angle2);
    ArrayList<Integer> array = new ArrayList<>();
    static ArrayList<Double> weight1Acc = new ArrayList<>();
    static ArrayList<Double> weight2Acc = new ArrayList<>();
    static int time; 
//    double angle1Acc;
//    double angle2Acc;
//    
    public static void sleep( int duration ) {
        try {
              Thread.sleep( duration );
            }
        catch (Exception e) {
            }
    }
    public void paint (Graphics g){ 
       Image image = createImage();
       g.drawImage(image, 0, 0, this);
       g.setColor(Color.yellow);
      
      
        
    }
    
    public void updateAngle(int numG){
        
        
        
        double num1 = -g * (2* m1 + m2)*Math.sin(angle1);
        double num2 = - m2*g*Math.sin(angle1-2*angle2);
        double num3 = -2*Math.sin(angle1-angle2)*m2;
        double num4 = angle2Vel*angle2Vel*l2 + angle1Vel * angle1Vel* l1*Math.cos(angle1-angle2);
        
        double den = l1* (2*m1 + m2- m2*Math.cos(2*angle1 - 2*angle2));

        weight1.acceleration = (num1+num2+num3*num4) / den;
        
        double num12 = 2* Math.sin(angle1 - angle2);
        double num22 = (angle1Vel*angle1Vel*l1*(m1+m2));
        double num32 = g* (m1+ m2) * Math.cos(angle1);
        double num42 = angle2Vel * angle2Vel * l2 * m2* Math.cos(angle1-angle2);
        double den2 = l2* (2*m1 + m2- m2*Math.cos(2*angle1 - 2*angle2));
        
        weight2.acceleration = (num12 * (num22+num32+num42)) / den2 ;
        
        angle1Vel = angle1Vel + weight1.acceleration;
        angle2Vel = angle2Vel + weight2.acceleration;
        angle1 = angle1 + angle1Vel; 
        angle2= angle2 + angle2Vel;
        weight1Acc.add(angle1);
        weight2Acc.add(angle2);
        System.out.println("hi");
        
        if (numG >1 ){
            
            updateAngle(numG-1);
        }
        
        
       
    }
    
   
    private Image createImage(){
        
        
        BufferedImage bufferedImage = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);

        
        
        double x1 = xStart + l1* Math.sin(weight1Acc.get(time));
        double y1 =  yStart + l1* Math.cos(weight1Acc.get(time));

        //took out x1,
//        x1 = x1+ l2 * Math.sin(weight1Acc.get(time));
//        y1 = y1+ l2 * Math.cos(weight1Acc.get(time));
        
        weight1.x2 = x1 + l2 * Math.sin(weight2Acc.get(time));
        weight1.y2 = y1+ l2 * Math.cos(weight2Acc.get(time));
        int padding = 15;
        
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setColor(Color.pink);

        g.drawLine(xStart, yStart, (int)x1, (int)y1);

        g.drawOval((int)x1-padding-10, (int)y1-padding, (int)m1, (int)m1);
        g.fillOval((int)x1-padding-10, (int)y1-padding, (int)m1, (int)m1);
        
        
        g.drawLine((int)x1, (int)y1,(int) weight1.x2, (int)weight1.y2);
        g.drawOval((int)weight1.x2-padding, (int)weight1.y2- padding, (int)m2, (int)m2);
        g.fillOval((int)weight1.x2-padding, (int)weight1.y2-padding, (int)m2, (int)m2);
        
        
        array.add((int)weight1.x2);
        array.add((int)weight1.y2);
        
        g.setColor(Color.pink);
         for(int i = 0; i < array.size()-1; i=i+2){
           g.drawOval(array.get(i),array.get(i+1), 3,3);
           g.fillOval(array.get(i), array.get(i+1), 3, 3);
           
       } 
        //System.out.println(array);
        return bufferedImage;
        
        
        
    }
    
    public void tracePath(Graphics g){
        
        
        
        
    }
    
        
    public static void main(String[] args) {
        
        DoublePendulum dp = new DoublePendulum();
        new OpeningScreen().setVisible(true);
        new DoublePendulum().setVisible(false);
        
        dp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dp.setTitle("Double Pendulum");
        dp.setBackground(Color.blue);
        dp.setSize(700, 700);

        dp.setVisible(true);
        int numFrames = 1000;
        dp.updateAngle(numFrames);
        System.out.println(weight1Acc);
        System.out.println(weight2Acc);
        //while (true){
            for (int j = 0; j < numFrames; j++){
                time = j;
                dp.repaint();

            sleep(19);
           // }
        }
        
        
    }
    
     
}
