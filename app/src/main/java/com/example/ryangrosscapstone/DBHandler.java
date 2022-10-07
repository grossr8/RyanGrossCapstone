package com.example.ryangrosscapstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "FishDataDB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "FishSize";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String WEIGHT_COL = "weight";

    // below variable for our course description column.
    private static final String DIAGONAL_LENGTH_COL = "diagonal_length";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);//TODO:remove later
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + WEIGHT_COL + " INTEGER,"
                + DIAGONAL_LENGTH_COL + " INTEGER)";

        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewFishSize(FishSizeClass fishSizeClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        ContentValues values = new ContentValues();
        values.put(NAME_COL, fishSizeClass.getFishName());
        values.put(WEIGHT_COL, fishSizeClass.getFishWeight());
        values.put(DIAGONAL_LENGTH_COL, fishSizeClass.getFishDiagonalLength());

        //db.execSQL("Delete from " + TABLE_NAME); //TODO: Remove Later
        // after adding all values we are passing
        // content values to our table.
        if(fishSizeClass.getFishId() == null) {
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // we have created a new method for reading all the courses.
    public ArrayList<FishSizeClass> readFishSizeFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorFishSize = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<FishSizeClass> fishModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorFishSize.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                try {
                    fishModalArrayList.add(new FishSizeClass(
                            cursorFishSize.getString(1),
                            cursorFishSize.getDouble(2),
                            cursorFishSize.getDouble(3),
                            cursorFishSize.getInt(0)));
                }catch (Exception ex)
                {
                    String message = ex.getMessage();
                }
            } while (cursorFishSize.moveToNext());
            // moving our cursor to next.
        }
        cursorFishSize.close();
        return fishModalArrayList;
    }
    public boolean tableNotEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int tableCount = cursor.getInt(0);
        if (tableCount > 0) {
            return true;
        } else {
            return false;
        }
    }
}
