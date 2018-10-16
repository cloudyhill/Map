/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polar.world.map;

import java.util.Arrays;


/**
 *
 * @author 00818880
 */
public class GlobePoint {
    
    private double x;
    private double y;
    private double z;
    private double lat;
    private double lon;
    
    public static double greatCircleDistant(GlobePoint p1, GlobePoint p2, boolean metric){
        double delta=Math.acos(Math.sin(Math.toRadians(p1.lat))*Math.sin(Math.toRadians(p2.lat))+
                               Math.cos(Math.toRadians(p1.lat))*Math.cos(Math.toRadians(p2.lat))*Math.cos(Math.toRadians(p1.lon-p2.lon)));
        return delta*(metric?Parameter.R_METRIC:Parameter.R_IMPERIAL);
    }
    public static double getPolarAngle(GlobePoint o, GlobePoint p1, GlobePoint p2){
        //angle between op1 and op2
//        System.out.println("======");
        double[] n1=MathTool.crossProduct3(o.getXYZ(), p1.getXYZ());
        double[] n2=MathTool.crossProduct3(o.getXYZ(), p2.getXYZ());
//        System.out.println(Arrays.toString(n1));
//        System.out.println(Arrays.toString(n2));
        double cosTheta=MathTool.dotProduct(n1, n2)/(MathTool.norm(n1)*MathTool.norm(n2));
//        System.out.println(MathTool.dotProduct(n1, n2));
//        System.out.println(MathTool.norm(n1)*MathTool.norm(n2));
//        System.out.println(cosTheta);
//        System.out.println("======");
        return Math.toDegrees(Math.acos(cosTheta));
    }
    
    public static GlobePoint getAuxPoint(GlobePoint orig, GlobePoint dest){
        double[] xyz=MathTool.crossProduct3(orig.getXYZ(), dest.getXYZ());
//        System.out.println(Arrays.toString(orig.getXYZ()));
//        System.out.println(Arrays.toString(dest.getXYZ()));
//        System.out.println(Arrays.toString(xyz));
        double factor=Parameter.R_METRIC/MathTool.norm(xyz);
        return new GlobePoint(xyz[0]*factor,xyz[1]*factor,xyz[2]*factor);
    }
            
            
    public GlobePoint(double lat, double lon){
        this.lat=lat;
        this.lon=lon;
        setXYZ(lat,lon);
    }
    
    public GlobePoint(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
        setLatLon(x,y,z);
    }
    
    private void setXYZ(double lat, double lon){
        this.x=Math.cos(Math.toRadians(lat))*Math.cos(Math.toRadians(lon))*Parameter.R_METRIC;
        this.y=Math.cos(Math.toRadians(lat))*Math.sin(Math.toRadians(lon))*Parameter.R_METRIC;
        this.z=Math.sin(Math.toRadians(lat))*Parameter.R_METRIC;
    }
    
    public double[] getXYZ(){
        return new double[]{x,y,z};
    }
    
    public void setLatLon(double x, double y, double z){
        double theta=Math.asin(z/Parameter.R_METRIC); //radian
        double phi;
        if(theta==Math.PI/2||theta==-Math.PI/2){
            phi=0;
        }else{
            double cosPhi=x/(Parameter.R_METRIC*Math.cos(theta));
            double sinPhi=x/(Parameter.R_METRIC*Math.cos(theta));
            phi=Math.acos(cosPhi);
            if(sinPhi<0){
                phi=-phi;
            }            
        }
        this.lat=Math.toDegrees(theta);
        this.lon=Math.toDegrees(phi);
    }
    
    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append(String.format("The latitude and longitude are %s and %s.\n", lat, lon));
        str.append(String.format("The coordinates in XYZ is (%s, %s, %s).s", x, y, z));
        return str.toString();
    }
}
