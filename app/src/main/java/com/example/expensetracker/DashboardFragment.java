package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ExpenseSqlite sqlite;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        sqlite = new ExpenseSqlite(requireContext());

        // Set up button click listeners
        binding.addExpense.setOnClickListener(v -> {
            addActivity.EXPENSE = true;
            startActivity(new Intent(getActivity(), addActivity.class));
        });

        binding.addIncome.setOnClickListener(v -> {
            addActivity.EXPENSE = false;
            startActivity(new Intent(getActivity(), addActivity.class));
        });

        binding.expenseShow.setOnClickListener(v -> {
            RecyclerViewActivity.REC_VIEW = true;
            startActivity(new Intent(getActivity(), RecyclerViewActivity.class));
        });

        binding.incomeShow.setOnClickListener(v -> {
            RecyclerViewActivity.REC_VIEW = false;
            startActivity(new Intent(getActivity(), RecyclerViewActivity.class));
        });

        showData();

        return binding.getRoot();
    }

    private void showData() {
        double expense = sqlite.showExpense();
        double income = sqlite.showIncome();

        binding.totalExpense.setText("BDT: " + expense);
        binding.totalIncome.setText("BDT: " + income);
        binding.mainBalance.setText("BDT: " + (income - expense));
    }

    @Override
    public void onResume() {
        super.onResume();
        showData(); // Refresh data when fragment becomes visible
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
