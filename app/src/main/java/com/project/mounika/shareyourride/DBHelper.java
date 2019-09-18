package com.project.mounika.shareyourride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DBHelper extends SQLiteOpenHelper
{
    public static String DataBaseName = "ShareYourRide";
    SQLiteDatabase DB;


    public DBHelper(Context x)
    {
        super(x, DataBaseName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE `User` (\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`Name`\tTEXT NOT NULL,\n" +
                "\t`PhoneNumber`\tTEXT NOT NULL,\n" +
                "\t`Email`\tTEXT NOT NULL,\n" +
                "\t`Password`\tTEXT NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE `DriverTrips` (\n" +

                "\t`Source`\tTEXT NOT NULL,\n" +
                "\t`Destination`\tTEXT NOT NULL,\n" +
                "\t`Email`\tTEXT NOT NULL,\n" +
                "\t`Name`\tTEXT NOT NULL,\n" +
                "\t`CarNo`\tTEXT NOT NULL,\n" +
                "\t`Date`\tTEXT NOT NULL,\n" +
                "\t`Time`\tTEXT NOT NULL,\n" +
                "\t`Vehicle`\tTEXT NOT NULL,\n" +
                "\t`Slots`\tTEXT NOT NULL,\n" +
                "\t`Status`\tTEXT NOT NULL,\n" +
                "\t`Price`\tTEXT NOT NULL,\n" +
                "\t`ID`\tINTEGER PRIMARY KEY AUTOINCREMENT\n" +
                ");\n");
        db.execSQL("CREATE TABLE `RiderTrips` (\n" +
                "\t`Source`\tTEXT NOT NULL,\n" +
                "\t`Destination`\tTEXT NOT NULL,\n" +
                "\t`Email`\tTEXT NOT NULL,\n" +
                "\t`Name`\tTEXT NOT NULL,\n" +
                "\t`CarNo`\tTEXT NOT NULL,\n" +
                "\t`Date`\tTEXT NOT NULL,\n" +
                "\t`Time`\tTEXT NOT NULL,\n" +
                "\t`Status`\tTEXT NOT NULL,\n" +
                "\t`Vehicle`\tTEXT NOT NULL,\n" +
                "\t`Slots`\tTEXT NOT NULL,\n" +
                "\t`Price`\tTEXT NOT NULL,\n" +
                "\t`ID`\tINTEGER NOT NULL\n" +
                ");\n");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public int NumberOfRows(String par)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int rows = (int) DatabaseUtils.queryNumEntries(db,par);
        return rows;
    }

    public void Insert(String par)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(par);
    }
    public void Insert2(String tableName, ContentValues con)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tableName, null, con);
    }

    public void Update(String par)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(par);
    }

    public void Update2(String tableName, ContentValues con, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(tableName, con, "id=?", new String[]{id + ""});
    }

    public void Delete(String par)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(par);
    }

    public String[][] Select(String par)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor r = db.rawQuery(par,null);
        r.moveToFirst();
        String arr[][] = new String[r.getCount()][r.getColumnCount()];

        for (int i = 0; i < r.getCount(); i++)
        {
            for (int j = 0; j < r.getColumnCount(); j++)
            {
                arr[i][j] = r.getString(j);
            }
            r.moveToNext();
        }
        r.close();

        return arr;

    }
}
