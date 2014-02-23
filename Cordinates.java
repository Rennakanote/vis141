package com.rennakanote.gpsdraw;

public class Cordinates {

    // private variables
    int _id;
    float _timeValue;
    float _latValue;
    float _lngValue;

    // Empty Constructor
    public Cordinates() { 

    }

    // constructor
    public Cordinates(int id, float timeValue, float latValue, float lngValue)  {
        this._id = id;
        this._timeValue = timeValue;
        this._latValue = latValue;
        this._lngValue = lngValue;
    }
    /* GETTERS for id, latitude, longitude
     * Will try to add the import for time later...
     */

    // getting ID
    public int getID()  {
        return this._id;
    }
    // getting time
    public float getTime()  {
    	return this._timeValue;
    }
    // getting latitude
    public float getLat()  {
        return this._latValue;
    }
    // getting longitude
    public float getLng()  {
        return this._lngValue;
    }

    /* SETTERS for id, latitude, longitude
     * Will try to add the import for time later...
     */ 
    public void setID(int id)   {
        this._id = id;
    }
    public void setTime(long timeValue)  {
    	this._timeValue = timeValue;
    }
    // setting latitude
    public void setLat(long latValue)  {
        this._latValue = latValue;
    }

    // setting longitude
    public void setLng(long lngValue)  {
        this._lngValue = lngValue;
    }   
}
