package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExpenseAdapter adapter;

    ArrayList<ExpenseModel> arrayList = new ArrayList<>();
    ExpenseSqlite sqlite;
    public static boolean REC_VIEW = true;

    TextView recyTv;

    // Register the result launcher
    ActivityResultLauncher<Intent> editExpenseLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyTv = findViewById(R.id.recyTv);
        recyclerView = findViewById(R.id.recyclerView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sqlite = new ExpenseSqlite(this);

        // Register result callback
        editExpenseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ExpenseModel updatedExpense = (ExpenseModel) result.getData().getSerializableExtra("updated_expense");
                        int position = result.getData().getIntExtra("position", -1);

                        if (position != -1 && updatedExpense != null) {
                            arrayList.set(position, updatedExpense);
                            adapter.notifyItemChanged(position);
                        }
                    }
                });

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

        arrayList.clear();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(1);
                double amount = cursor.getDouble(2);
                String date = cursor.getString(3);
                String category = cursor.getString(4);
                String note = cursor.getString(5);

                arrayList.add(new ExpenseModel(title, amount, date, category, note));
            }

            adapter = new ExpenseAdapter(arrayList, (expense, position) -> {
                Intent intent = new Intent(RecyclerViewActivity.this, EditExpenseActivity.class);
                intent.putExtra("expense", expense);
                intent.putExtra("position", position);
                editExpenseLauncher.launch(intent);
            });

            recyclerView.setAdapter(adapter);
        }
    }
}
