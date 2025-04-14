package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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

        // Initialize DB
        sqlite = new ExpenseSqlite(requireContext());

        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setupPieChart();
        barChart = binding.barChart;
        setupBarChart();

        // Button clicks
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView userIcon = view.findViewById(R.id.userIcon);
        userIcon.setOnClickListener(this::showPopupMenu);
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_add_acc) {
                Toast.makeText(getContext(), "Add Bank Account clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddBankAccountActivity.class);
                startActivity(intent);
                return true;

            } else if (id == R.id.menu_logout) {
                // Show confirmation dialog before logout
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            Toast.makeText(getContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                            SharedPreferences prefs = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.clear();
                            editor.apply();

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();

                return true;
            }

            return false;
        });

        popupMenu.show();
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

        float groupSpace = 0.2f;
        float barSpace = 0.05f;
        float barWidth = 0.35f;

        barData.setBarWidth(barWidth);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(months.length);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

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
        showData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
