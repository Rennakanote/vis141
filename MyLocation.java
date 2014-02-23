package com.rennakanote.gpsdraw;

// import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import com.rennakanote.gpsdraw.DatabaseHandler;

public class MyLocation extends Activity implements LocationListener  {

    private Handler handler;
	private TextView latitudeField;
	private TextView longitudeField;
	private TextView timeField; 
    private double lat = 0;
    private double lng = 0;
    private double time = 0;
    DatabaseHandler db = new DatabaseHandler(this);
    StringBuilder sb = new StringBuilder();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.println(Log.ASSERT, "MyLocation.java", "App Started ~^*~^*~");

        // Handler will post to UI thread
        handler = new Handler(); 

        // Normal stuff
        latitudeField = (TextView) findViewById(R.id.latTextView);
	    longitudeField = (TextView) findViewById(R.id.lngTextView);
	    timeField = (TextView) findViewById(R.id.timeTextView);

	    LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
	    
	    Location loc = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		Log.println(Log.ASSERT, "getLastKnownLocation()", loc.getLatitude() + " " + loc.getLongitude());
		lat = loc.getLatitude();
		lng = loc.getLongitude();
		time = loc.getTime();
		latitudeField.setText(lat + "");
		longitudeField.setText(lng + "");
		timeField.setText(time + "");
	    // THIS IS STANDARD register the listener with the Location Manager to receive location updates
	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,this);
	    
	}
	
	public void onLocationChanged(Location loc)  {
		lat = loc.getLatitude();
		lng = loc.getLongitude();
		time = loc.getTime();
		handler.post(new Runnable()  {
			public void run()  {
				latitudeField.setText(lat + "");
				longitudeField.setText(lng + "");
				timeField.setText(time + "");
				Toast.makeText(getApplicationContext(),"Time:"+ time +"\n" + 
						"Latitude: "+lat+ "\n"+
						"Longitude: "+lng, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@SuppressLint("ShowToast")
	public void onProviderDisabled(String provider)  {
		Toast.makeText(getApplicationContext(), "GPS Disabled" + provider, Toast.LENGTH_SHORT);
		Log.println(Log.ASSERT, "onProviderDisabled()", "Provider Has Been Disabled.");
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		 // you could do things here, such as sense when a user has turned off the gps in setttings		
	}	
	
    
	public void onStatusChanged(String provider, int status, Bundle extras)  {
        /*Log.println(Log.ASSERT, "onStatusChanged()", "PROVIDER : " + provider 
                + " ~ " +"STATUS: " + status 
                + ". " + "EXTRAS? : " + extras 
                + ".");
                */        
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_location, menu);
        return true;        
    }
}
