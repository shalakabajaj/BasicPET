package com.example.expensetracker;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class Income extends Fragment {

    private RecyclerView recyclerView;
    private IncomeAdapter adapter;
    private List<IncomeModel> incomeList;
    private SearchView searchView;

    public Income() {
        // Required empty public constructor
    }

    public static Income newInstance(String param1, String param2) {
        Income fragment = new Income();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        recyclerView = view.findViewById(R.id.incomeRecyclerView);
        searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy list (replace with real data)
        incomeList = new ArrayList<>();
        incomeList.add(new IncomeModel("Salary", 10000, "2025-04-01", "Job", "Monthly salary"));
        incomeList.add(new IncomeModel("Freelance", 2500, "2025-04-04", "Freelance", "Logo design"));
        incomeList.add(new IncomeModel("Gift", 500, "2025-04-05", "Others", "Birthday gift"));

        adapter = new IncomeAdapter(incomeList);
        recyclerView.setAdapter(adapter);

        // Search filtering
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        return view;
    }
}
