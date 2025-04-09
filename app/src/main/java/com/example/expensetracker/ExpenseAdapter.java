package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<ExpenseModel> expenseList;

    public ExpenseAdapter(List<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
    }

    public void setData(List<ExpenseModel> newList) {
        this.expenseList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseModel expense = expenseList.get(position);
        holder.expenseTitle.setText(expense.getTitle());
        holder.expenseAmount.setText("BDT " + expense.getAmount());
        holder.expenseDate.setText(expense.getDate());
        holder.expenseCategory.setText(expense.getCategory());
        holder.expenseNote.setText(expense.getNote());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView expenseTitle, expenseAmount, expenseDate, expenseCategory, expenseNote;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseTitle = itemView.findViewById(R.id.expenseTitle);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            expenseCategory = itemView.findViewById(R.id.expenseCategory);
            expenseNote = itemView.findViewById(R.id.expenseNote);
        }
    }
}
