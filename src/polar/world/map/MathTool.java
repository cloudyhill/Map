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
public class MathTool {
    
    public static double dotProduct(double[] v1, double[] v2){
        assert v1.length==v2.length;
        double ret=0;
        for(int i=0;i<v1.length;i++){
            ret+=v1[i]*v2[i];
        }
        return ret;
    }
    
    public static double[] crossProduct3(double[] u, double[] v){
        assert u.length==v.length&&u.length==3;
        double[] ret=new double[3];
        ret[0]=u[1]*v[2]-u[2]*v[1];
        ret[1]=u[2]*v[0]-u[0]*v[2];
        ret[2]=u[0]*v[1]-u[1]*v[0];
        return ret;
    }
    
    public static double norm(double[] u){
        double ret=0;
        for(double i :u){
            ret+=i*i;
        }
        return Math.sqrt(ret);
    }
    
    
}
