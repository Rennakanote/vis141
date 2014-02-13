package com.rennakanote.VIS141Final;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper  {
	
	// All static variables
	// Database version
	private static final int DATABASE_VERSION = 1;
	
	// Database name
	private static final String DATABASE_NAME = "Cordinates Manager";
	
	// Coordinates Table name
	private static final String TABLE_CORDINATES = "cordinates";
	
	// Coordinates Table Column titles
	private static final String KEY_ID = "ID";
	private static final String KEY_LAT = "LAT"; 
	private static final String KEY_LNG = "LNG";
	
	public DatabaseHandler(Context context)  {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Creating Table
	@Override
	public void onCreate(SQLiteDatabase db)  {
		String CREATE_CORDINATES_TABLE = "CREATE TABLE " + TABLE_CORDINATES + "("
				+ KEY_ID + "INTEGER PRIMARY KEY, " + KEY_LAT + " TEXT,"  
				+ "TEXT," + KEY_LNG + ")" ;
	}
	
	// Upgrading database
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTED" + TABLE_CORDINATES);
		
		// Create table again
		onCreate(db);
	}
	
	//Adding new Coordinate
	void addCordinate(Cordinates cordinate)  {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_LAT, cordinate.getLat()); // Coordinates Lat
		values.put(KEY_LNG, cordinate.getLng()); // Coordinates Lng
		
		// Inserting a Row
		db.insert(TABLE_CORDINATES, null, values);
		db.close(); // close database connection
	}
	
	// Getting Single Cordinate Row
	Cordinates getCordinate(int id)  {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_CORDINATES, new String[] { KEY_ID, KEY_LAT, KEY_LNG }, KEY_ID + "=?", 
				new String[] {String.valueOf(id) }, null, null, null, null);
		if(cursor != null)
			cursor.moveToFirst();
		
		Cordinates cordinate = new Cordinates();
		return cordinate;
	}
	
	// Getting All Cordinates
	public List<Cordinates> getAllCordinates()  {
		List<Cordinates> cordinateList = new ArrayList<Cordinates>();
		// Select All Query
		String selectQuery = "SELECT *  FROM * " + TABLE_CORDINATES;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst())  {
			do {
				Cordinates cordinates = new Cordinates();
				cordinates.setID(Integer.parseInt(cursor.getString(0)));
				cordinates.setLat(cursor.getLong(1));
				cordinates.setLng(cursor.getLong(2));
				// Adding Cordinate to list
				cordinateList.add(cordinates);
			} while (cursor.moveToNext());
		}
		// return the cordinate list
		return cordinateList;
	}
	
	/* Updating single cordinate row
	 * Create a new value object
	 */
	public int updateCordinate(Cordinates cordinate)  {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues value = new ContentValues();
		value.put(KEY_LAT, cordinate.getLat());
		value.put(KEY_LNG, cordinate.getLng());
		
		// Updating row
		return db.update(TABLE_CORDINATES, value, KEY_ID + " = ?",
				new String[] { String.valueOf(cordinate.getID())});
	}
	
	// Deleting a single Coordinate
	public void deleteCordinate(Cordinates cordinate)  {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CORDINATES, KEY_ID + "=?",
				new String[] {String.valueOf(cordinate.getID()) });
		db.close();
	}
	
	// Getting Cordinate total count
	public int getCordinatesCount()  {
		String countQuery = "SELECT * FROM " + TABLE_CORDINATES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		
		// return count
		return cursor.getCount();
	}
}
