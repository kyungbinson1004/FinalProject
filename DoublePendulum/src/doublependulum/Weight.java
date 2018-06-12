/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doublependulum;

/**
 *
 * @author Kyungbin
 */
public class Weight {
    double length, angle, velocity, acceleration,x2,y2;
    
    public Weight(double lengthVal, double Angle){
        length = lengthVal;
        angle = Angle;
        velocity = 0;
        acceleration = 0;
        x2= 0; 
        y2= 0;
    }
    
}
