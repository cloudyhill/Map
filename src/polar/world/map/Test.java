/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polar.world.map;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author 00818880
 */
public class Test {
    
    public static void main(String[] args){
        GlobePoint p1=new GlobePoint(90,0); //origin
        GlobePoint p2=new GlobePoint(0,0);  //destination
        GlobePoint p3=new GlobePoint(0,-140);  //any other point on earth
        GlobePoint auxP=GlobePoint.getAuxPoint(p1, p2); //auxiliar point
        double d=GlobePoint.greatCircleDistant(p1, p2, true); //great circle distance between p1 and p2
        double d1=GlobePoint.greatCircleDistant(p1, p3, true); //great circle distance between p1 and p3
        double rho1=GlobePoint.getPolarAngle(p1, p2, p3);
        double rho2=GlobePoint.getPolarAngle(p1, auxP, p3);
        double rho;  //angel between p1p2 and p1p3 in the polar coordinate
//        if(rho1<=90){
//            if(rho2<=90){
//                rho=rho1;
//            }else{
//                rho=360-rho1;
//            }
//        }else{
//            if(rho2<=90){
//                rho=rho1;
//            }else{
//                rho=360-rho1;
//            }
//        }
        if(rho2<=90){
            rho=rho1;
        }else{
            rho=360-rho1;
        }
        //TODO iterate all points on the eartch as p3
        System.out.println(GlobePoint.getAuxPoint(p1, p2));
        System.out.println(rho);
        PolarPoint o=new PolarPoint();
        PolarPoint p2Polar=new PolarPoint(0,d);
        PolarPoint p3Polar=new PolarPoint(rho, d1);
        System.out.println("Distance Test: "+GlobePoint.greatCircleDistant(new GlobePoint(0,0), new GlobePoint(0,0.01), true));
//        System.out.println(Arrays.toString(p1.getXYZ()));
//        System.out.println(Arrays.toString(p2.getXYZ()));
//        System.out.println(Arrays.toString(p3.getXYZ()));
//        System.out.println(GlobePoint.greatCircleDistant(p1, p2, true));
//        System.out.println(GlobePoint.getPolarAngle(p1, p2,p3));
        
//        System.out.println(Arrays.toString(MathTool.crossProduct3(new double[]{0,0,1},new double[]{0,1,0})));
//        System.out.println(Arrays.toString(MathTool.crossProduct3(new double[]{0,0,1},new double[]{0,-1,0})));
        LocalDateTime t1=LocalDateTime.now();
        for(int i=0;i<6.5E6;i++){
            d1=GlobePoint.greatCircleDistant(p1, p3, true); //great circle distance between p1 and p3
            rho1=GlobePoint.getPolarAngle(p1, p2, p3);
            rho2=GlobePoint.getPolarAngle(p1, auxP, p3);
            if (rho2 <= 90) {
                rho = rho1;
            } else {
                rho = 360 - rho1;
            }
        }
        System.out.println("Single thread time used: "+Duration.between(t1, LocalDateTime.now()));
        System.out.println("test");
        LocalDateTime t2=LocalDateTime.now();
        final ExecutorService executor=Executors.newFixedThreadPool(20);
        final List<Future<?>> futures=new ArrayList<>();        
        for(int i=0;i<6.5E6;i++){
            Future<?> future=executor.submit(()->{
                double d11=GlobePoint.greatCircleDistant(p1, p3, true); //great circle distance between p1 and p3
                double rho11=GlobePoint.getPolarAngle(p1, p2, p3);
                double rho21=GlobePoint.getPolarAngle(p1, auxP, p3);
                double rho111=rho21<=90?rho11:360-rho11;
            });
            futures.add(future);
        }
        try{
            for(Future<?> future:futures){
                future.get();
            }
        }catch(InterruptedException|ExecutionException e){
            e.printStackTrace();
        }
        System.out.println("Parallel time used: "+Duration.between(t2, LocalDateTime.now()));
        System.exit(0);
    }
   
}
