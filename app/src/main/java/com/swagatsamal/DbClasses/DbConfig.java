package com.swagatsamal.DbClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import com.swagatsamal.swagatsamalassignment2.MainActivity;

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
        try {
            SQLiteDatabase conestogaDb = this.getWritableDatabase();
            ContentValues contentValues =  new ContentValues();

            contentValues.put("fullName", student.getFullName());
            contentValues.put("programCode", student.getProgramCode());
            contentValues.put("grade", student.getGrade());
            contentValues.put("duration", student.getDuration());
            contentValues.put("fees" ,student.getFees());
            conestogaDb.insert("students", null, contentValues);
            Log.i("MESSAGE: ","Student inserted");
        }catch (Exception e)
        {
            Log.i("ERROR: ",e.getMessage());
        }
    }

    //View all student record
    public ArrayList<String> viewAllStudents()
    {
        ArrayList<String> allStudents = new ArrayList<>();
        StudentPOJO studentPOJO = new StudentPOJO();
        try {
            SQLiteDatabase conestogaDb = this.getReadableDatabase();
            Cursor cursor = conestogaDb.rawQuery("SELECT * FROM students",null);

            //check if there is a result and parse it to a list
            if (cursor.moveToFirst())
            {
                do {
                    studentPOJO.ID = cursor.getInt(0);
                    studentPOJO.fullName = cursor.getString(1);
                    studentPOJO.programCode = cursor.getString(2);
                    studentPOJO.grade = cursor.getString(3);
                    studentPOJO.duration = cursor.getString(4);
                    studentPOJO.fees = cursor.getDouble(5);
                    allStudents.add(studentPOJO.toString());
                }
                while (cursor.moveToNext());
            }

        }catch (Exception e)
        {
            Log.i("ERROR: ",e.getMessage());
        }


        return allStudents;
    }
}
