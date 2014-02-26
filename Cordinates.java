package com.rennakanote.gpsdraw;

	import java.util.ArrayList;
	
	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteDatabase.CursorFactory;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.util.Log;

	public class DatabaseHandler  {

	// All static variables
	
    // Database name & version
    private static String DB_NAME = "Cordinates Manager";
    private static final int DB_VERSION = 1;

    // Coordinates Table name
    private static final String TABLE_CORDINATES = "Cordinates";

    // Coordinates Table Column titles
    private static final String KEY_ID = "id";
    private static final int 	ID_COL = 0; 
    private static final String KEY_TIME = "time";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    
    // murach's CREATE and DROP TABLE statements.  
    public static final String CREATE_CORDINATES_TABLE = 
    		 "CREATE TABLE " + TABLE_CORDINATES + " ( "			+ 
    		  KEY_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
    		  KEY_TIME + " TEXT, " + 
              KEY_LAT  + " TEXT, " + 
    		  KEY_LNG  + " TEXT);";
    
    public static final String DROP_CORDINATES_TABLE = 
    		"DROP TABLE IF EXISTS " + TABLE_CORDINATES;
 
    private static class DBHelper extends SQLiteOpenHelper   {

    	public DBHelper(Context context, String name, 
	    			CursorFactory factory, int version)  {
	    		super(context, name, factory, version);
		}
	
	    public void onCreate(SQLiteDatabase db)  {
	    	// create table
	        db.execSQL(CREATE_CORDINATES_TABLE);
	    }
	
	    // Upgrading database
	    public void onUpgrade(SQLiteDatabase db, 
	    		int oldVersion, int newVersion)  {
	        // Drop older table if existed
	        db.execSQL("DROP TABLE IF EXISTED" + TABLE_CORDINATES);
	        Log.println(Log.ASSERT, "TABLE_CORDINATES" , "Upgrading db from version " + oldVersion + "to " + newVersion);
	        
	        db.execSQL(DatabaseHandler.DROP_CORDINATES_TABLE);
	        onCreate(db);
	    }
    }
    // Creating Table

	// database and database helper objects
 	private SQLiteDatabase db;
	private DBHelper dbHelper;
		
	// constructor
	public DatabaseHandler(Context context)  {
		dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	// private methods
	private void openReadableDB()  {
		db = dbHelper.getReadableDatabase();
	}
	
	private void openWritableDB()  {
		db = dbHelper.getWritableDatabase();
	}
	
	private void closeDB()  {
		if(db != null)
			db.close();
	}
/*	
	// public methods
	public ArrayList<Cordinates> getCords()  {
		ArrayList<Cordinates> cordinates = new ArrayList<Cordinates>();
		openReadableDB();
		Cursor cursor = db.query(TABLE_CORDINATES, 
				null, null, null, null, null, null);
		while (cursor.moveToNext())  {
			Cordinates cordinate = new Cordinates();
			cordinate.setID(cursor.getInt(ID_COL));
			cordinate.setTime(cursor.getString(KEY_TIME));
			cordinate.setLat(cursor.getInt(KEY_LAT));
			cordinate.setLng(cursor.getInt(KEY_LNG));
			
			cordinates.add(cordinates);
		}
		if(cursor != null)
			cursor.close();
		closeDB();
		
		return cordinates;
	}

	public Cordinates getCord(String name)  {
		String where = KEY_ID + "= ?";
		String[] whereArgs = { name };
		
		openReadableDB();
		Cursor cursor = db.query(TABLE_CORDINATES, null,
				where, whereArgs, null, null, null);
		Cordinates cordinate = null;
		cursor.moveToFirst();
		cordinate = new Cordinates(cursor.getCord(KEY_ID),
								  cursor.getCords(KEY_TIME));
		if(cursor != null)
			cursor.close();
		this.closeDB();
		
		return cordinate;
	}

	private static Cordinates getCordinateFromCursor(Cursor cursor)  {
		if(cursor == null || cursor.getCount() == 0)  {
			return null;
		} else {  
			try  {
				Cordinates cordinate = new Cordinate()
						cursor.put(KEY_ID),
						cursor.put(KEY_TIME),
						cursor.put(KEY_LAT),
						cursor.put(KEY_LNG));
				return cordinate;
			}
			catch(Exception e)  {
				return null;
			}
		}
	}
	
	public double addCordinate(Cordinates cordinate)  {
    	ContentValues values = new ContentValues();
    	values.put(KEY_TIME, cordinate.getTime()); // Coordinates Time
        values.put(KEY_LAT, cordinate.getLat()); //  Coordinates Lat
        values.put(KEY_LNG, cordinate.getLng()); //  Coordinates Lng
        
        this.openWritableDB();
        double rowID = db.insert(TABLE_CORDINATES, null, values);
        
        return rowID;
	}
	
    public int updateCordinate(Cordinates cordinate)  {
        ContentValues value = new ContentValues();
        value.put(KEY_TIME, cordinate.getTime()); // get time
        value.put(KEY_LAT, cordinate.getLat());   // get latitude
        value.put(KEY_LNG, cordinate.getLng());   // get longitude

        String where = KEY_ID + "= ?";
        String[] whereArgs = {String.valueOf(cordinate.getID()) };
        
        // Updating row
    	this.openWritableDB();
    	db.update(TABLE_CORDINATES, value, where, whereArgs);
        return db.update(TABLE_CORDINATES, value, KEY_ID + " = ?",
                new String[] { String.valueOf(cordinate.getID())});
    }

    // Deleting a single Coordinate
    public void deleteCordinate(Cordinates cordinate)  {
        // 1.  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_TIME, cordinate.getTime()); // get time
        value.put(KEY_LAT, cordinate.getLat());   // get latitude
        value.put(KEY_LNG, cordinate.getLng());   // get longitude

        // 2.  delete
        db.delete(TABLE_CORDINATES, KEY_ID + "=?",
                new String[] {String.valueOf(cordinate.getID()) });

        // 3. Close
        db.close();
        Log.println(Log.ASSERT, "deleteCordinate()", cordinate.toString());
    }
    
//	Adding new Coordinate
    public double addCordinate(Cordinates cordinate)  {
        
        
        // 1. Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Create contentValues to add key "column" / value
        
        //for(int i = 0; insert.size(); i++)  {
        	ContentValues values = new ContentValues();
            values.put(KEY_TIME, cordinate.getTime()); // Coordinates Time
            values.put(KEY_LAT, cordinate.getLat()); //  Coordinates Lat
            values.put(KEY_LNG, cordinate.getLng()); //  Coordinates Lng            
            // 3. Inserting a Row
            double rowID = db.insert(TABLE_CORDINATES, null, values);	
        //}

        // 4. Close the database
        db.close(); 
        return rowID;
    }

    // Getting Single Cordinate Row
    Cordinates getCordinates(Cordinates cordinates)  {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. Build query
        Cursor cursor = db.query(TABLE_CORDINATES,           		   // a. table
                new String[] { KEY_TIME, KEY_ID, KEY_LAT, KEY_LNG },   // b. column names 
                KEY_ID + "=?",                               		   // c. selections 
                new String[] {String.valueOf(cordinates) },          		   // d. selection args  
                null,                                                  // e. group by
                null,                                                  // f. having
                null,                                        	       // g. order by
                null);                                                 // h. limit

        // 3.  if we get results, get the first one 
        if(cursor != null)
            cursor.moveToFirst();
        db.close();

        // 4.  build cordinate object
        Cordinates cordinate = new Cordinates();

        // Print to the log cat
        Log.println(Log.ASSERT,"getCordinates("+cordinates+")", cordinate.toString());
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
        Log.println(Log.ASSERT, "deleteCordinate()", cordinate.toString());
    }

    // Getting Coordinate total count
    public int getCordinatesCount()  {
        String countQuery = "SELECT * FROM " + TABLE_CORDINATES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        Log.println(Log.ASSERT, "getcCordinatesCount()", countQuery.toString());

        // return count
        return cursor.getCount();
    }
*/

	private SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return null;
	}
}
