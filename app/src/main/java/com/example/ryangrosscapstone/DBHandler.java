package com.example.ryangrosscapstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "FishDataDB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String FISH_SIZE_TABLE = "FishSize";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String WEIGHT_COL = "weight";
    private static final String DIAGONAL_LENGTH_COL = "diagonal_length";
    private static final String FISH_SIZE_BOX_SIZE = "fishsize_box_number";

    private static final String BOX_SIZE_TABLE = "BoxSize";
    private static final String BOX_SIZE_ID_COL = "id";
    private static final String BOX_SIZE_SIZE_COL = "boxSizeNumber";


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
        String query2 = "CREATE TABLE " + BOX_SIZE_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOX_SIZE_SIZE_COL + " INTEGER)";

        String query = "CREATE TABLE " + FISH_SIZE_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + WEIGHT_COL + " INTEGER,"
                + DIAGONAL_LENGTH_COL + " INTEGER,"
                + FISH_SIZE_BOX_SIZE + " INTEGER,"
                + " FOREIGN KEY ("+FISH_SIZE_BOX_SIZE+") REFERENCES " +BOX_SIZE_TABLE+"("+BOX_SIZE_ID_COL+"));";

        db.execSQL(query2);
        db.execSQL(query);

    }

    // this method is use to add new course to our sqlite database.
    public void addNewFishSize(FishSizeClass fishSizeClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        ContentValues values = new ContentValues();
        values.put(NAME_COL, fishSizeClass.getFishName().toLowerCase(Locale.ROOT));
        values.put(WEIGHT_COL, fishSizeClass.getFishWeight());
        values.put(DIAGONAL_LENGTH_COL, fishSizeClass.getFishDiagonalLength());
        values.put(FISH_SIZE_BOX_SIZE, fishSizeClass.getFishBoxSize());

        //db.execSQL("Delete from " + TABLE_NAME); //TODO: Remove Later
        // after adding all values we are passing
        // content values to our table.
        if(fishSizeClass.getFishId() == null) {
            db.insert(FISH_SIZE_TABLE, null, values);
        }
        db.close();
    }

    public void addNewBoxSize(BoxSize boxSizeClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        ContentValues values = new ContentValues();
        //values.put(BOX_SIZE_ID_COL, boxSizeClass.getBoxId());
        values.put(BOX_SIZE_SIZE_COL, boxSizeClass.getBoxSize());

        //db.execSQL("Delete from " + TABLE_NAME); //TODO: Remove Later
        // after adding all values we are passing
        // content values to our table.
        if(boxSizeClass.getBoxId() == null) {
            db.insert(BOX_SIZE_TABLE, null, values);
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + FISH_SIZE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOX_SIZE_TABLE);
        onCreate(db);
    }

    // we have created a new method for reading all the courses.
    public ArrayList<FishSizeClass> readFishSizeFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorFishSize = db.rawQuery("SELECT * FROM " + FISH_SIZE_TABLE, null);
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
                            cursorFishSize.getInt(0),
                            cursorFishSize.getInt(4)));
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

    public ArrayList<BoxSize> readBoxSizeFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorBoxSize = db.rawQuery("SELECT * FROM " + BOX_SIZE_TABLE, null);
        ArrayList<BoxSize> boxSizeModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorBoxSize.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                try {
                    boxSizeModalArrayList.add(new BoxSize(
                            cursorBoxSize.getInt(1),
                            cursorBoxSize.getInt(0)));
                }catch (Exception ex)
                {
                    String message = ex.getMessage();
                }
            } while (cursorBoxSize.moveToNext());
            // moving our cursor to next.
        }
        cursorBoxSize.close();
        return boxSizeModalArrayList;
    }

    public boolean tableNotEmpty(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(tableExists(db, tableName)) {
            String count = "SELECT count(*) FROM " + tableName;
            Cursor cursor = db.rawQuery(count, null);
            cursor.moveToFirst();
            int tableCount = cursor.getInt(0);
            if (tableCount > 0) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }

    }

    public boolean tableExists(SQLiteDatabase db, String table) {
        boolean result = false;
        String sql = "select count(*) xcount from sqlite_master where type='table' and name='"
                + table + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
            result = true;
        cursor.close();
        return result;
    }

    public void dropAllTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + FISH_SIZE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOX_SIZE_TABLE);

        db.close();
    }

    public void createTablesIfNotExists(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query2 = "CREATE TABLE IF NOT EXISTS " + BOX_SIZE_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOX_SIZE_SIZE_COL + " INTEGER)";

        String query = "CREATE TABLE IF NOT EXISTS " + FISH_SIZE_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + WEIGHT_COL + " INTEGER,"
                + DIAGONAL_LENGTH_COL + " INTEGER,"
                + FISH_SIZE_BOX_SIZE + " INTEGER,"
                + " FOREIGN KEY ("+FISH_SIZE_BOX_SIZE+") REFERENCES " +BOX_SIZE_TABLE+"("+BOX_SIZE_ID_COL+"));";

        db.execSQL(query2);
        db.execSQL(query);
        db.close();

    }

    public String getFishSizeTableName(){
        return FISH_SIZE_TABLE;
    }

    public String getBoxSizeTableName(){
        return BOX_SIZE_TABLE;
    }
}
