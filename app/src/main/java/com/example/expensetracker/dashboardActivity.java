package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dashboardActivity extends AppCompatActivity {

    private BarChart barChart;
    private List<String> xValues;

    TextView mainBalance, totalExpense, addExpense, showExpense, totalIncome, addIncome, showIncome;

    ExpenseSqlite sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Pie Chart
        PieChart pieChart = findViewById(R.id.chart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(800f, "Health"));
        entries.add(new PieEntry(400f, "Stationery"));
        entries.add(new PieEntry(600f, "Cosmetic"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Subjects");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Initialize Bar Chart
        barChart = findViewById(R.id.barChart);
        setupBarChart();

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sqlite = new ExpenseSqlite(dashboardActivity.this);

        // Initialize UI elements
        mainBalance = findViewById(R.id.mainBalance);
        totalExpense = findViewById(R.id.totalExpense);
        addExpense = findViewById(R.id.addExpense);
        showExpense = findViewById(R.id.expenseShow);
        totalIncome = findViewById(R.id.totalIncome);
        addIncome = findViewById(R.id.addIncome);
        showIncome = findViewById(R.id.incomeShow);

        // Button Click Listeners
        addExpense.setOnClickListener(v -> {
            addActivity.EXPENSE = true;
            startActivity(new Intent(dashboardActivity.this, addActivity.class));
        });

        addIncome.setOnClickListener(v -> {
            addActivity.EXPENSE = false;
            startActivity(new Intent(dashboardActivity.this, addActivity.class));
        });

        showExpense.setOnClickListener(v -> {
            RecyclerViewActivity.REC_VIEW = true;
            startActivity(new Intent(dashboardActivity.this, RecyclerViewActivity.class));
        });

        showIncome.setOnClickListener(v -> {
            RecyclerViewActivity.REC_VIEW = false;
            startActivity(new Intent(dashboardActivity.this, RecyclerViewActivity.class));
        });

        showData();
    }

    private void setupBarChart() {
        // Dummy data for testing
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        incomeEntries.add(new BarEntry(0, 1000f));
        incomeEntries.add(new BarEntry(1, 1500f));
        incomeEntries.add(new BarEntry(2, 500f));
        incomeEntries.add(new BarEntry(3, 2000f));
        incomeEntries.add(new BarEntry(4, 100f));
        incomeEntries.add(new BarEntry(5, 300f));
        incomeEntries.add(new BarEntry(6, 3000f));
        incomeEntries.add(new BarEntry(7, 1000f));
        incomeEntries.add(new BarEntry(8, 10000f));
        incomeEntries.add(new BarEntry(9, 4000f));
        incomeEntries.add(new BarEntry(10, 2500f));
        incomeEntries.add(new BarEntry(11, 1000f));



        ArrayList<BarEntry> expenseEntries = new ArrayList<>();
        expenseEntries.add(new BarEntry(0, 500f));
        expenseEntries.add(new BarEntry(1, 1200f));
        expenseEntries.add(new BarEntry(2, 800f));
        expenseEntries.add(new BarEntry(3, 1800f));
        expenseEntries.add(new BarEntry(4, 500f));
        expenseEntries.add(new BarEntry(5, 200f));
        expenseEntries.add(new BarEntry(6, 300f));
        expenseEntries.add(new BarEntry(7, 5400f));
        expenseEntries.add(new BarEntry(8, 550f));
        expenseEntries.add(new BarEntry(9, 220f));
        expenseEntries.add(new BarEntry(10, 300f));
        expenseEntries.add(new BarEntry(11, 500f));


        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Income");
        incomeDataSet.setColor(Color.BLUE);

        BarDataSet expenseDataSet = new BarDataSet(expenseEntries, "Expense");
        expenseDataSet.setColor(Color.RED);

        BarData barData = new BarData(incomeDataSet, expenseDataSet);
        barData.setBarWidth(0.3f); // Set bar width

        barChart.setData(barData);
        barChart.groupBars(0, 0.4f, 0.02f); // Grouping bars
        barChart.invalidate();

        // X-Axis configuration
        xValues = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);

        // Y-Axis configuration
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setAxisMaximum(300f);
        leftAxis.setLabelCount(6);

        barChart.getAxisRight().setEnabled(false);
    }

    public void showData() {
        totalExpense.setText("BDT: " + sqlite.showExpense());
        totalIncome.setText("BDT: " + sqlite.showIncome());

        double balance = sqlite.showIncome() - sqlite.showExpense();
        mainBalance.setText("BDT: " + balance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }
}
