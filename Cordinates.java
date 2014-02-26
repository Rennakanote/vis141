package com.rennakanote.gpsdraw;

public class Cordinates {

    // private variables
    private int id;
    private float timeValue;
    private float latValue;
    private float lngValue;

    // Empty Constructor
    public Cordinates() { }

    // constructor
    public Cordinates(int id, float timeValue, float latValue, float lngValue)  {
        this.id = id;
        this.timeValue = timeValue;
        this.latValue = latValue;
        this.lngValue = lngValue;
    }

    public void setID(int id)   {
        this.id = id;
    }    
    
    public int getID()  {
        return this.id;
    }

    public void setTime(float timeValue)  {
    	this.timeValue = timeValue;
    }    
    
    public float getTime()  {
    	return this.timeValue;
    }
    
    public void setLat(float latValue)  {
    	this.latValue = latValue;
    }
    public float getLat()  {
        return this.latValue;
    }

    public void setLng(float lngValue)  {
        this.lngValue = lngValue;
    }
    
    public float getLng()  {
        return this.lngValue;
    }
}
