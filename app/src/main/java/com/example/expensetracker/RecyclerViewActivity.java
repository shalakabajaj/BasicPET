package com.example.expensetracker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Currency;

public class RecyclerViewActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ExpenseAdapter adepter;

    ArrayList<ExpenseModel> arrayList = new ArrayList<>();

    ExpenseSqlite sqlite;
    public  static boolean REC_VIEW= true;

    TextView recyTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyTv = findViewById(R.id.recyTv);

        recyclerView = findViewById(R.id.recyclerView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sqlite = new ExpenseSqlite(this);

        loadData();
    }
    public void loadData() {
        Cursor cursor;

        if (REC_VIEW) {
            cursor = sqlite.showExpenseRecyclerView();
            recyTv.setText("Expense List");
        } else {
            cursor = sqlite.showIncomeRecyclerView();
            recyTv.setText("Income List");
        }

        arrayList.clear(); // Clear old data before loading new

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // Adjust these indexes to match your DB table columns
                String title = cursor.getString(1);       // Title
                double amount = cursor.getDouble(2);      // Amount
                String date = cursor.getString(3);        // Date
                String category = cursor.getString(4);    // Category
                String note = cursor.getString(5);        // Note

                arrayList.add(new ExpenseModel(title, amount, date, category, note));
            }

            adepter = new ExpenseAdapter(arrayList);
            recyclerView.setAdapter(adepter);
        }
    }

}