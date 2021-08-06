package com.swagatsamal.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.swagatsamal.DbClasses.DbConfig;
import com.swagatsamal.DbClasses.StudentPOJO;
import com.swagatsamal.swagatsamalassignment2.AdapterConversion;
import com.swagatsamal.swagatsamalassignment2.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    //User interface control variables declared
    EditText idEditText;
    RadioGroup optionRadioGroup;
    RadioButton idRadio;
    RadioButton progRadio;
    ListView progListView;
    RecyclerView resultRecyclerView;
    View view;
    Button searchButton;

    //Default option: id
    String menuSelected;

    //StudentPOJO object created
    StudentPOJO studentPOJO;

    //DB config object declared
    DbConfig dbConfig;

    //Arraylist to hold all offered programs
    ArrayList<String> programs = new ArrayList<>();//List is updated inside onCreate()
    //This list will store the result that comes from database.
    List<String> resultList = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The list of programs offered
        programs.add("PROG01");
        programs.add("PROG02");
        programs.add("PROG03");
        programs.add("PROG04");
        studentPOJO = new StudentPOJO();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Every time the view loads on screen, this function is called.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View variable initiated to access the elements that are placed inside it.
        view = inflater.inflate(R.layout.fragment_search, container, false);

        //User interface control variables initialized
        idEditText = view.findViewById(R.id.idEditText);
        optionRadioGroup = view.findViewById(R.id.optionGroup);
        idRadio = view.findViewById(R.id.idRadio);
        progRadio = view.findViewById(R.id.progRadio);
        progListView = view.findViewById(R.id.progListSelection);
        resultRecyclerView = view.findViewById(R.id.resultRecyclerView);
        searchButton = view.findViewById(R.id.searchButton);

        //Adding a line below each record for better visibility.
        resultRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        //Database configured with default constructor
        dbConfig = new DbConfig(this.getContext());

        //Radio button click triggers this method to get inout data
        idRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewTwo) {
                onRadioClicked(viewTwo);
            }
        });
        progRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewTwo) {
                onRadioClicked(viewTwo);
            }
        });

        //Search button click triggers this function
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewThree) {

                if(!idEditText.getText().toString().equals("")) {
                    studentPOJO.setID(Integer.parseInt(idEditText.getText().toString()));
                }
                    resultList = dbConfig.getStudentByMenu(studentPOJO, menuSelected);
                    if (resultList.size() == 0) {
                        Toast.makeText(getContext(),"No records found",Toast.LENGTH_SHORT).show();
                        resultList.add("\t Student doesn't exist. \n \tHint: You can add this record.");
                    }
                    AdapterConversion adapterConversion = new AdapterConversion(view.getContext(), resultList);
                    if (resultList != null) {
                        resultRecyclerView.setAdapter(adapterConversion);
                        resultRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    //Radio button click triggers this method.
    public void onRadioClicked(View viewTwo) {

        // Check which radio button was clicked
//-------------------------------SWITCH START----------------------------
        switch(optionRadioGroup.getCheckedRadioButtonId()) {
            case R.id.idRadio:
                Toast.makeText(getContext(),"Search by ID", Toast.LENGTH_SHORT).show();
                idEditText.getText().clear();
                idEditText.setHint("Enter your ID here");
                idEditText.setEnabled(true);
                progListView.setAlpha(0);
                progListView.setAdapter(null);
                resultRecyclerView.setAdapter(null);
                menuSelected = "id";
                if(idEditText.getText().toString().equals("") || idEditText.getText() == null) {
                    Toast.makeText(getContext(),"Please enter a valid ID", Toast.LENGTH_SHORT).show();
                }
                    break;
            case R.id.progRadio:
                Toast.makeText(getContext(),"Search by program", Toast.LENGTH_SHORT).show();
                menuSelected = "prog";
                idEditText.getText().clear();
                idEditText.setHint("Select a program below:");
                idEditText.setEnabled(false);
                progListView.setAlpha(1);
                resultRecyclerView.setAdapter(null);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, programs);
                progListView.setAdapter(arrayAdapter);
                try {
                    progListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            studentPOJO.setProgramCode(String.valueOf(programs.get(i)));
                            Toast.makeText(getContext(),studentPOJO.getProgramCode().toString()+" selected",Toast.LENGTH_SHORT).show();
                            View selected = progListView.getChildAt(i);
                            Log.i("INDEX SELECTED: ",""+i);
                            selected.setBackgroundColor(Color.LTGRAY);
                            for(int j=0 ; j<programs.size() ; j++)
                            {
                                if(j != i && progListView.getChildAt(j) != null)
                                {
                                    progListView.getChildAt(j).setBackgroundColor(Color.WHITE);
                                }
                            }
                        }
                    });
                } catch (Exception e){
                    Log.i("ERROR ",e.getMessage());
                }
                break;
        }
//--------------------------------------SWITCH END---------------------------
    }

}