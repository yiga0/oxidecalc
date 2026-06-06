//Copyright (c) 2015, Nolan Peard, All rights reserved.

import java.lang.Math;

public class OxideTime extends TimeUtility{
    //Calculation Constants
    public final static double k = 8.6173 * Math.pow(10,-5); //Boltzman's Constant - Normalized to electron charge
    public final static double C1wet = 3.86 * Math.pow(10,2);
    public final static double C2wet111 = 1.63 * Math.pow(10,8);
    public final static double C1dry = 7.72 * Math.pow(10,2);
    public final static double C2dry111 = 6.23 * Math.pow(10,6);
    public final static double C2wet100 = C2wet111/1.68; 
    public final static double C2dry100 = C2dry111/1.68;
    public final static double C2wet110 = C2wet100 * 1.45;
    public final static double C2dry110 = C2dry100 * 1.45;
    public final static double E1wet = 0.78;
    public final static double E2wet = 2.05;
    public final static double E1dry = 1.23;
    public final static double E2dry = 2.00;
    
    //Object Members
    private double Energy1;
    private double Energy2;
    private double Constant1;
    private double Constant2;
    
    public OxideTime()
    {
        this(E1wet, E2wet, C1wet, C2wet100);
    }
    
    public OxideTime(double energy1, double energy2, double constant1, double constant2)
    {
        Energy1 = energy1;
        Energy2 = energy2;
        Constant1 = constant1;
        Constant2 = constant2;
    }
    
    public double getEnergy1()
    {
        return Energy1;
    }
    
    public double getEnergy2()
    {
        return Energy2;
    }
    
    public double getConstant1()
    {
        return Constant1;
    }
    
    public double getConstant2()
    {
        return Constant2;
    }
    
    public void setEnergy1(double energy1)
    {
        Energy1 = energy1;
    }
    
    public void setEnergy2(double energy2)
    {
        Energy2 = energy2;
    }
    
    public void setConstant1(double constant1)
    {
        Constant1 = constant1;
    }
    
    public void setConstant2(double constant2)
    {
        Constant2 = constant2;
    }
    
    //Temperature should be in Kelvin
    //Returns the time in string format after modifying the hours, minutes, seconds of the OxideTime object
    public OxideTime calculateTime(int T, double initialThick, double finalThick)
    {
        double B = Constant1 * Math.exp(-(Energy1/(k * T)));
        double BA = Constant2 * Math.exp(-(Energy2/(k * T)));
        double x = finalThick / 10000;
        double x0 = initialThick / 10000;
        
        double time = (Math.pow(x,2) - Math.pow(x0, 2))/B + (x - x0)/BA;
        double h = Math.floor(time);
        this.setHours((int)h);
        double time1 = 60 * (time - h);
        double m = Math.floor(time1);
        this.setMinutes((int)m);
        double s = Math.floor(60 * (time1 - m));
        this.setSeconds((int)s);
        
        return(this);
    }
    
}

