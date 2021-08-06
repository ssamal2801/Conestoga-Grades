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
//DbConfigHelper is the Database Helper Class
public class DbConfigHelper extends SQLiteOpenHelper {
    public DbConfigHelper(@Nullable Context context) {
        //Open DB 'conestoga', Or create it, if does not exist.
        super(context, "conestoga.db", null, 1);
    }

    //This method is triggered when app starts for the first time
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
            //Database object created
            SQLiteDatabase conestogaDb = this.getWritableDatabase();
            ContentValues contentValues =  new ContentValues();

            //saving a record in Db
            contentValues.put("fullName", student.getFullName());
            contentValues.put("programCode", student.getProgramCode());
            contentValues.put("grade", student.getGrade());
            contentValues.put("duration", student.getDuration());
            contentValues.put("fees" ,student.getFees());
            conestogaDb.insert("students", null, contentValues);

            //Log for developers to see the change
            Log.i("MESSAGE: ","Student inserted");
        }catch (Exception e)
        {
            //prints the error message if any
            Log.i("ERROR: ",e.getMessage());
        }
    }

    //View all student record
    public ArrayList<String> viewAllStudents()
    {
        ArrayList<String> allStudents = new ArrayList<>();//This list will save the record set that comes from DB
        StudentPOJO studentPOJO = new StudentPOJO();
        try {
            //DB object created
            SQLiteDatabase conestogaDb = this.getReadableDatabase();
            //Cursor object created to iterate through the record
            Cursor cursor = conestogaDb.rawQuery("SELECT * FROM students",null);

            //check if there is a result and parse it to a list
            if (cursor.moveToFirst())
            {
                do {//Assign cursor record to studentPOJO and send it to frontend as a list
                    studentPOJO.ID = cursor.getInt(0);
                    studentPOJO.fullName = cursor.getString(1);
                    studentPOJO.programCode = cursor.getString(2);
                    studentPOJO.grade = cursor.getString(3);
                    studentPOJO.duration = cursor.getString(4);
                    studentPOJO.fees = cursor.getDouble(5);
                    allStudents.add(studentPOJO.toString());
                }
                while (cursor.moveToNext());//move next function to iterate through the result set
            }

        }catch (Exception e)
        {
            //prints the error message if any
            Log.i("ERROR: ",e.getMessage());
        }
        return allStudents;//Return the final list of students
    }

    //Get student data based on menu selection
    public List<String> getStudentByMenu(StudentPOJO studentPOJO, String menuSelected) {
        List<String> resultSet = new ArrayList<>();//List to store result set from DB

        try{
            //check if user has selected search by ID option
            //if true, get record from database based on ID
            if(menuSelected.equals("id") && studentPOJO.getID() != 0)
            {
                //Array of arguments to be passed inside Select statement in Cursor object.
                String[] arr = {String.valueOf(studentPOJO.getID())};
                SQLiteDatabase conestogaDb = this.getReadableDatabase();
                //Select statement in cursor object to get record based on id.
                Cursor cursor = conestogaDb.rawQuery("SELECT * FROM students WHERE id=?",arr);

                //check if there is a result and parse it to a list
                if (cursor.moveToFirst())
                {
                    do {//Assign cursor record to studentPOJO and send it to frontend as a list
                        studentPOJO.ID = cursor.getInt(0);
                        studentPOJO.fullName = cursor.getString(1);
                        studentPOJO.programCode = cursor.getString(2);
                        studentPOJO.grade = cursor.getString(3);
                        studentPOJO.duration = cursor.getString(4);
                        studentPOJO.fees = cursor.getDouble(5);
                        resultSet.add(studentPOJO.toString());
                    }
                    while (cursor.moveToNext());//move next function to iterate through the result set
                }
                //If user did not select ID, check if user selected to search by program
            }else if (menuSelected.equals("prog") && studentPOJO.getProgramCode() != null){
                //Array of arguments to be passed inside Select statement in Cursor object.
                String[] arr = {studentPOJO.getProgramCode()};
                SQLiteDatabase conestogaDb = this.getReadableDatabase();
                //Select statement in a Cursor object to get records based on program code.
                Cursor cursor = conestogaDb.rawQuery("SELECT * FROM students where programCode=?",arr);

                //check if there is a result and parse it to a list
                if (cursor.moveToFirst())
                {
                    do {//Assign cursor record to studentPOJO and send it to frontend as a list
                        studentPOJO.ID = cursor.getInt(0);
                        studentPOJO.fullName = cursor.getString(1);
                        studentPOJO.programCode = cursor.getString(2);
                        studentPOJO.grade = cursor.getString(3);
                        studentPOJO.duration = cursor.getString(4);
                        studentPOJO.fees = cursor.getDouble(5);
                        resultSet.add(studentPOJO.toString());
                    }
                    while (cursor.moveToNext());//move next function to iterate through the result set
                }
            }

        }catch (Exception e)
        {
            //prints the error message if any
            Log.i("ERROR: ",e.getMessage());
        }
        
        return resultSet;
    }
}
