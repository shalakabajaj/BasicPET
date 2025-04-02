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
    ExpenseAdepter adepter;

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


    public void loadData(){


        Cursor cursor = null;

        if (REC_VIEW==true){

            cursor = sqlite.showExpenseRecyclerView();
            recyTv.setText("Expense List");


        }else {

            cursor = sqlite.showIncomeRecyclerView();
            recyTv.setText("Income List");
        }


        if (cursor!=null && cursor.getCount()>0){

            while (cursor.moveToNext()){


                int id = cursor.getInt(0);
                String buy = cursor.getString(1);
                String reason =  cursor.getString(2);

                arrayList.add(new ExpenseModel(id,buy,reason));

            }

            adepter = new ExpenseAdepter(arrayList,RecyclerViewActivity.this);
            recyclerView.setAdapter(adepter);


        }
    }
}