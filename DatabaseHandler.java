package com.rennakanote.gpsdraw;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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
    private static final String KEY_TIME = "TIME";
    private static final String KEY_LAT = "LAT"; 
    private static final String KEY_LNG = "LNG";

    public DatabaseHandler(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Table
    @Override
    public void onCreate(SQLiteDatabase db)  {
        
    	String CREATE_CORDINATES_TABLE = "CREATE TABLE " + TABLE_CORDINATES + "("
                + KEY_ID + "INTEGER PRIMARY KEY, " 
    			+ KEY_TIME + "TEXT"
    			+ KEY_LAT  + " TEXT,"  
                + KEY_LNG + "TEXT);";
        // create table
        db.execSQL(CREATE_CORDINATES_TABLE);
    }

    // Upgrading database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTED" + TABLE_CORDINATES);

        // Create table again
        this.onCreate(db);
    }

    //Adding new Coordinate
    public void addCordinate(Cordinates cordinate)  {
        // Print to the log cat
        Log.println(Log.ASSERT, "addCordinate", cordinate.toString());
        // 1. Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Create contentValues to add key "column" / value
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, cordinate.getTime()); // Coordinates Time
        values.put(KEY_LAT, cordinate.getLat()); //  Coordinates Lat
        values.put(KEY_LNG, cordinate.getLng()); //  Coordinates Lng

        // 3. Inserting a Row
        db.insert(TABLE_CORDINATES, null, values);
        // 4. Close the database
        db.close(); // close database connection
    }

    // Getting Single Cordinate Row
    Cordinates getCordinates(int id)  {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. Build query
        Cursor cursor = db.query(TABLE_CORDINATES,           		   // a. table
                new String[] { KEY_TIME, KEY_ID, KEY_LAT, KEY_LNG },   // b. column names 
                KEY_ID + "=?",                               		   // c. selections 
                new String[] {String.valueOf(id) },          		   // d. selection args  
                null,                                                  // e. group by
                null,                                                  // f. having
                null,                                        	       // g. order by
                null);                                                 // h. limit

        // 3.  if we get results, get the first one 
        if(cursor != null)
            cursor.moveToFirst();

        // 4.  build cordinate object
        Cordinates cordinate = new Cordinates();

        // Print to the log cat
        Log.println(Log.ASSERT,"getCordinates("+id+")", cordinate.toString());
        // 5.  return cordinates
        return cordinate;
    }

    // Getting All Cordinates
    public List<Cordinates> getAllCordinates()  {
        List<Cordinates> cordinateList = new ArrayList<Cordinates>();
        // 1.  Build the query for Select All
        String selectQuery = "SELECT *  FROM * " + TABLE_CORDINATES;

        // 2.  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // 3.  Looping through all rows and adding to list
        if (cursor.moveToFirst())  {
            do {
                Cordinates cordinates = new Cordinates();
                cordinates.setID(Integer.parseInt(cursor.getString(0)));
                cordinates.setTime(cursor.getLong(1));
                cordinates.setLat(cursor.getLong(2));
                cordinates.setLng(cursor.getLong(3));

                // Adding Cordinate to list
                cordinateList.add(cordinates);
            } while (cursor.moveToNext());
        }

        Log.println(Log.ASSERT,"getAllCordinates()", cordinateList.toString());
        // return the cordinate list
        return cordinateList;
    }

    /* for every new row, update single cordinate row
     *      'Listen for cordinate values*
     * if !newRow == false NEW ROOWWWW!!
     * Create a new value object
     */
    public int updateCordinate(Cordinates cordinate)  {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(KEY_TIME, cordinate.getTime()); // get time
        value.put(KEY_LAT, cordinate.getLat());   // get latitude
        value.put(KEY_LNG, cordinate.getLng());   // get longitude

        // Updating row
        return db.update(TABLE_CORDINATES, value, KEY_ID + " = ?",
                new String[] { String.valueOf(cordinate.getID())});
    }

    // Deleting a single Coordinate
    public void deleteCordinate(Cordinates cordinate)  {
        // 1.  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2.  delete
        db.delete(TABLE_CORDINATES, KEY_ID + "=?",
                new String[] {String.valueOf(cordinate.getID()) });

        // 3. Close
        db.close();
        Log.println(Log.ASSERT, "deleteCordinate", cordinate.toString());
    }

    // Getting Cordinate total count
    public int getCordinatesCount()  {
        String countQuery = "SELECT * FROM " + TABLE_CORDINATES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        Log.println(Log.ASSERT, "getcCordinatesCount()", countQuery.toString());

        // return count
        return cursor.getCount();
    }
}
