package com.swagatsamal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.swagatsamal.DbClasses.DbConfig;
import com.swagatsamal.DbClasses.StudentPOJO;
import com.swagatsamal.swagatsamalassignment2.AdapterToArrayAdapter;
import com.swagatsamal.swagatsamalassignment2.MainActivity;
import com.swagatsamal.swagatsamalassignment2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewGradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewGradesFragment extends Fragment {

    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewGradesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewGradesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewGradesFragment newInstance(String param1, String param2) {
        ViewGradesFragment fragment = new ViewGradesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_grade, container, false);
        recyclerView = view.findViewById(R.id.gradeListView);

        List<String> resultList = new ArrayList<>();
        DbConfig dbConfig = new DbConfig(this.getContext());

        resultList = dbConfig.viewAllStudents();
        AdapterToArrayAdapter adapterToArrayAdapter = new AdapterToArrayAdapter(view.getContext(), resultList);

        if (resultList != null) {
            recyclerView.setAdapter(adapterToArrayAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        else
        {
            Toast.makeText(view.getContext(),"No records found",Toast.LENGTH_SHORT).show();
        }
        // Inflate the layout for this fragment
        return view;
    }
}