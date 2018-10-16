/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polar.world.map;

/**
 *
 * @author 00818880
 */
public class PolarPoint {
    
    private double theta;
    private double rho;    
    
    public PolarPoint(){ //create the origin of the polar coordinates
        theta=0;
        rho=0;
    }
    
    public PolarPoint(double theta, double rho){
        this.theta=theta;
        this.rho=rho;
    }
    
}
