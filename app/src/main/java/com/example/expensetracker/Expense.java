package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Expense extends Fragment {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private Spinner monthSpinner;
    private TextView totalExpenseTextView, monthlyExpenseTextView;
    private Button btnAddExpense;

    public Expense() {
        // Required empty public constructor
    }

    public static Expense newInstance(String param1, String param2) {
        Expense fragment = new Expense();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        recyclerView = view.findViewById(R.id.expenseRecyclerView);
        monthSpinner = view.findViewById(R.id.monthSpinner);
        totalExpenseTextView = view.findViewById(R.id.totalExpense);
        monthlyExpenseTextView = view.findViewById(R.id.monthlyExpense);
        btnAddExpense = view.findViewById(R.id.btnAddExpense);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        expenseAdapter = new ExpenseAdapter(new ArrayList<>());
        recyclerView.setAdapter(expenseAdapter);

        // Setup month spinner
        String[] months = new DateFormatSymbols().getMonths();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, Arrays.asList(months).subList(0, 12));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddExpenseActivity.class);
            startActivity(intent);
        });

        updateUI();
        return view;
    }

    private void updateUI() {
        List<ExpenseModel> filteredList = filterExpensesForSelectedMonth();
        expenseAdapter.setData(filteredList);
        updateSummaryCard(filteredList);
    }

    private void updateSummaryCard(List<ExpenseModel> filteredList) {
        double total = 0;
        double thisMonth = 0;

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String selectedMonth = monthSpinner.getSelectedItem().toString();

        for (ExpenseModel expense : ExpenseData.expenseList) {
            total += expense.getAmount();

            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expense.getDate());
                String month = monthFormat.format(date);
                if (month.equalsIgnoreCase(selectedMonth)) {
                    thisMonth += expense.getAmount();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        totalExpenseTextView.setText("Total Expense: BDT " + total);
        monthlyExpenseTextView.setText("This Month: BDT " + thisMonth);
    }

    private List<ExpenseModel> filterExpensesForSelectedMonth() {
        List<ExpenseModel> filtered = new ArrayList<>();
        String selectedMonth = monthSpinner.getSelectedItem().toString();

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

        for (ExpenseModel expense : ExpenseData.expenseList) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expense.getDate());
                String monthName = monthFormat.format(date);
                if (monthName.equalsIgnoreCase(selectedMonth)) {
                    filtered.add(expense);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return filtered;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(); // Refresh data on return
    }
}
