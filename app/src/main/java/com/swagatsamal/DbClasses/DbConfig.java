package com.swagatsamal.DbClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DbConfig extends SQLiteOpenHelper {
    public DbConfig(@Nullable Context context) {
        //Open DB 'conestoga', Or create it, if does not exist.
        super(context, "conestoga.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase conestogaDatabase) {
        //Open Table 'students', Or create if does not exist.
        String createTable = "CREATE TABLE IF NOT EXISTS students(id INTEGER PRIMARY KEY AUTOINCREMENT, fullName VARCHAR, programCode VARCHAR, grade VARCHAR, duration VARCHAR, fees DOUBLE)";
        conestogaDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Insert a new student record
    public void insertStudent(StudentPOJO student)
    {
        SQLiteDatabase conestogaDb = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();

        contentValues.put("fullName", student.getFullName());
        contentValues.put("programCode", student.getProgramCode());
        contentValues.put("grade", student.getGrade());
        contentValues.put("duration", student.getDuration());
        contentValues.put("fees" ,student.getFees());
        conestogaDb.insert("students", null, contentValues);
        Log.i("MESSAGE: ","Student inserted");
    }
}
