package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ExpenseSqlite sqlite;
    private BarChart barChart;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Initialize database
        sqlite = new ExpenseSqlite(requireContext());

        // Optional: Full screen mode
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set up charts
        setupPieChart();
        barChart = binding.barChart;
        setupBarChart();

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

        return rootView;
    }

    private void showData() {
        double expense = sqlite.showExpense();
        double income = sqlite.showIncome();

        binding.totalExpense.setText("BDT: " + expense);
        binding.totalIncome.setText("BDT: " + income);
        binding.mainBalance.setText("BDT: " + (income - expense));
    }

    private void setupPieChart() {
        PieChart pieChart = binding.chart;
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(800f, "Health"));
        entries.add(new PieEntry(400f, "Stationery"));
        entries.add(new PieEntry(600f, "Cosmetic"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Categories");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(14f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void setupBarChart() {
        // Sample monthly data for Income and Expense (replace with actual DB values later)
        float[] incomeData = {1200, 1300, 1100, 1400, 1500, 1600, 1700, 1650, 1500, 1400, 1300, 1250};
        float[] expenseData = {1000, 900, 950, 1100, 1200, 1250, 1300, 1280, 1200, 1100, 1000, 950};
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        ArrayList<BarEntry> expenseEntries = new ArrayList<>();

        for (int i = 0; i < months.length; i++) {
            incomeEntries.add(new BarEntry(i, incomeData[i]));
            expenseEntries.add(new BarEntry(i, expenseData[i]));
        }

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Income");
        incomeDataSet.setColor(ColorTemplate.MATERIAL_COLORS[0]);

        BarDataSet expenseDataSet = new BarDataSet(expenseEntries, "Expense");
        expenseDataSet.setColor(ColorTemplate.MATERIAL_COLORS[1]);

        BarData barData = new BarData(incomeDataSet, expenseDataSet);

        // Customize spacing
        float groupSpace = 0.2f;
        float barSpace = 0.05f;
        float barWidth = 0.35f;

        barData.setBarWidth(barWidth);
        barChart.setData(barData);

        // X-axis setup
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(months.length);
        barChart.getXAxis().setPosition(com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setEnabled(false);

        barChart.groupBars(0, groupSpace, barSpace);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();
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
