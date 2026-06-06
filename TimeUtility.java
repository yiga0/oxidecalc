//Copyright (c) 2015, Nolan Peard, All rights reserved.

public class TimeUtility{
    private int hours;
    private int minutes;
    private int seconds;
    private int days;
    
    public TimeUtility()
    {
        this(0, 0, 0, 0);
    }
    
    public TimeUtility(int h, int m, int s)
    {
        this(h, m, s, 0);
    }
    
    public TimeUtility(int h, int m, int s, int d)
    {
        hours = h;
        minutes = m;
        seconds = s;
        days = d;
    }
    
    public int getHours()
    {
        return hours;
    }
    
    public int getMinutes()
    {
        return minutes;
    }
    
    public int getSeconds()
    {
        return seconds;
    }
    
    public int getDays()
    {
        return days;
    }
    
    public void setHours(int h)
    {
        hours = h;
    }
    
    public void setMinutes(int m)
    {
        minutes = m;
    }
    
    public void setSeconds(int s)
    {
        seconds = s;
    }
    
    public void setDays(int d)
    {
        days = d;
    }
    
    public String toString()
    {
        String formattedTime = new String();
        formattedTime += hours;
        formattedTime += ":";
        if(minutes < 10){
            formattedTime += "0";
        }
        formattedTime += minutes;
        formattedTime += ":";
        if(seconds < 10){
            formattedTime += "0";
        }
        formattedTime += seconds;
        
        return(formattedTime);
    }
    
    //Static/implicit, no variable modification
    //Adds the data members without rolling over hours
    //Does roll over seconds and minutes
    public TimeUtility add(TimeUtility time)
    {
        int h, m, s, d;
        
        h = ((time.getHours() + getHours()) + (time.getMinutes() + getMinutes() + (time.getSeconds() + getSeconds()) / 60) / 60);
        m = (time.getMinutes() + getMinutes() + (time.getSeconds() + getSeconds()) / 60) % 60;
        s = (time.getSeconds() + getSeconds()) % 60;
        d = (time.getDays() + getDays()) + (h / 24);
        
        return(new TimeUtility(h, m , s, d));
    }
    
    //  //Static/implicit, no variable modification
    //  //Returns the timeofDay for the invoking member (Run Time) with the given Start Time
    //  public TimeUtility timeOfDay(TimeUtility start)
    //  {
    //     TimeUtility temp = this.add(start);
    //
    //     temp.setHours(temp.getHours() % 24);
    //
    //     return temp;
    //  }
}

