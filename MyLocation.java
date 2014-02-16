package com.rennakanote.VIS141Final;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import com.rennakanote.VIS141Final.DatabaseHandler;

public class MyLocation extends Activity implements LocationListener  {
	  
	  private TextView latituteField;
	  private TextView longitudeField;
	  private LocationManager locationManager;
	  private String provider;
	  private float lat = 0;
	  private float lng = 0;	  
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.println(Log.ASSERT, "MyLocation", "App Started ~^*~^*~^*~^*~^*~^*~^*~^*~^*~^*~^*~^*");
		setContentView(R.layout.activity_main);
		latituteField = (TextView) findViewById(R.id.latTextView);
	    longitudeField = (TextView) findViewById(R.id.lngTextView);
	    
	    // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	    // Define the criteria how to select the location provide
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
	    
	    // Initialize the location fields
	    if (location != null) {
	      Log.println(Log.ASSERT, "MyLocation", "Provider " + provider + " has been selected");
	      onLocationChanged(location);

		    DatabaseHandler db = new DatabaseHandler(this);
		    
		    db.addCordinate(new Cordinates());
		    Log.println(Log.ASSERT, "MyLocation","Provider"+ "Test print line where stuff writes to db");

	    } else {
	      latituteField.setText("Location not available");
	      longitudeField.setText("Location not available");
	    }
	  }	    
	  
	  // Request updates at startup
	  @Override
	  protected void onResume() {
	    super.onResume();
 
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	    Log.println(Log.ASSERT, "This is the Location Updater", "update"); 
	  }
	  
	  
	  // Remove the location listener updates when Activity is paused
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	    Log.println(Log.ASSERT, "onPause() Method called", "Help");
	  }
	  
	  @Override
	  public void onLocationChanged(Location location) {
	    lat = (float) (location.getLatitude());
	    lng = (float) (location.getLongitude());
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	    Log.println(Log.ASSERT, "The Location has been changed:"+lat ,"Longitude" + lng);
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub
	  }

	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "GPS Enabled " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "GPS Disabled  " + provider,
	        Toast.LENGTH_SHORT).show();
	  }
}
