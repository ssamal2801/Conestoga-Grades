package com.swagatsamal.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.swagatsamal.DbClasses.DbConfigHelper;
import com.swagatsamal.DbClasses.StudentPOJO;
import com.swagatsamal.swagatsamalassignment2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GradeEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GradeEntryFragment extends Fragment {

    EditText nameText;
    EditText gradeText;
    EditText courseDurationText;
    EditText feesText;
    ListView progListView;
    Button saveButton;

    int indexSelected;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GradeEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GradeEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GradeEntryFragment newInstance(String param1, String param2) {
        GradeEntryFragment fragment = new GradeEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Method called every time this view loads on screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //create a view object to inflate the contents on to it.
        View view = inflater.inflate(R.layout.fragment_grades_entry, container, false);
        //All view variables initiated
        nameText = view.findViewById(R.id.name);
        gradeText = view.findViewById(R.id.grade);
        courseDurationText = view.findViewById(R.id.duration);
        feesText = view.findViewById(R.id.fees);
        progListView = view.findViewById(R.id.progListView);
        saveButton = view.findViewById(R.id.saveButton);

        StudentPOJO studentPOJO = new StudentPOJO();

        //The list of programs offered
        ArrayList<String> programs = new ArrayList<>();
        programs.add("PROG01");
        programs.add("PROG02");
        programs.add("PROG03");
        programs.add("PROG04");

        //List of programs sent back to the view for user to be able to select
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, programs);
        progListView.setAdapter(arrayAdapter);

        try {//every time the user clicks on any record of the list view, this listener will be invoked
        progListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Operations to do once user click on a record
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get the index of the cell clicked and assign the content to studentPOJO for DB operations
                studentPOJO.setProgramCode(String.valueOf(programs.get(i)));
                indexSelected = i;
                //inform user about the selection
                Toast.makeText(getContext(),studentPOJO.getProgramCode().toString()+" selected",Toast.LENGTH_SHORT).show();
                //get the index of item selected and change background color to Light Gray
                View selected = progListView.getChildAt(i);
                Log.i("INDEX SELECTED: ",""+i);
                selected.setBackgroundColor(Color.LTGRAY);//set color to grey
                //every time user selects a cell, iterate and change all other cell color to white.
                for(int j=0 ; j<programs.size() ; j++)
                {
                    if(j != i && progListView.getChildAt(j) != null)
                    {
                        //getting theme primary color and setting it to the unselected rows
                        //this alerts use about what option he/she has selected
                        TypedValue typedValue = new TypedValue();
                        getContext().getTheme().resolveAttribute(R.attr.colorOnPrimary, typedValue, true);
                        progListView.getChildAt(j).setBackgroundColor(typedValue.data);
                    }
                }
            }
        });
        } catch (Exception e){
            //Log error message if any
            Log.i("ERROR ",e.getMessage());
        }


        //method fired on click of SUBMIT button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set the data to studentPOJO object
                if(feesText.getText() != null) {
                    studentPOJO.setFullName(String.valueOf(nameText.getText()));
                    studentPOJO.setGrade(String.valueOf(gradeText.getText()));
                    studentPOJO.setFees(Double.parseDouble(String.valueOf(feesText.getText())));
                    studentPOJO.setDuration(String.valueOf(courseDurationText.getText()));
                }

                //"insertStudent" method call to save the studentPOJO object to database
                DbConfigHelper dbConfigHelper = new DbConfigHelper(view.getContext());
                if (studentPOJO != null) {
                    dbConfigHelper.insertStudent(studentPOJO);
                    //Alert user that record has been saved
                    Toast.makeText(view.getContext(), "Student details saved", Toast.LENGTH_SHORT).show();
                    //clear all the fields for user to enter next record
                    nameText.getText().clear();
                    gradeText.getText().clear();
                    feesText.getText().clear();
                    courseDurationText.getText().clear();
                    progListView.getChildAt(indexSelected).setBackgroundColor(Color.WHITE);

                }
            }
        });

        // Inflate this fragment to the parent
        return view;
    }



}