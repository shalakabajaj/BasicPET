package com.example.expensetracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<ExpenseModel> expenseList;
    private List<ExpenseModel> expenseListFull;
    private OnExpenseDeleteListener deleteListener;
    private OnExpenseEditListener editListener;

    // Interface for delete listener
    public interface OnExpenseDeleteListener {
        void onExpenseDeleted();
    }

    // ✅ Interface for edit click listener
    public interface OnExpenseEditListener {
        void onEditClicked(ExpenseModel expense, int position);
    }

    // ✅ Updated constructor
    public ExpenseAdapter(List<ExpenseModel> expenseList, OnExpenseEditListener editListener) {
        this.expenseList = expenseList;
        this.expenseListFull = new ArrayList<>(expenseList);
        this.editListener = editListener;
    }

    public void setOnExpenseDeleteListener(OnExpenseDeleteListener listener) {
        this.deleteListener = listener;
    }

    public void setData(List<ExpenseModel> newList) {
        this.expenseList = newList;
        this.expenseListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void updateExpense(ExpenseModel updatedExpense, int position) {
        expenseList.set(position, updatedExpense);
        expenseListFull.set(position, updatedExpense);
        notifyItemChanged(position);
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
        holder.expenseCategory.setText("Category: " + expense.getCategory());
        holder.expenseNote.setText(expense.getNote());

        // ✅ Edit click triggers listener callback
        holder.btnEdit.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEditClicked(expense, holder.getAdapterPosition());
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete this expense?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        int positionToRemove = holder.getAdapterPosition();
                        ExpenseModel toRemove = expenseList.get(positionToRemove);

                        expenseList.remove(positionToRemove);
                        ExpenseData.expenseList.remove(toRemove);
                        expenseListFull.remove(toRemove);

                        notifyItemRemoved(positionToRemove);

                        if (deleteListener != null) {
                            deleteListener.onExpenseDeleted();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public void filterList(String query) {
        if (query.isEmpty()) {
            expenseList = new ArrayList<>(expenseListFull);
        } else {
            List<ExpenseModel> filteredList = new ArrayList<>();
            for (ExpenseModel expense : expenseListFull) {
                if (expense.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        expense.getCategory().toLowerCase().contains(query.toLowerCase()) ||
                        expense.getNote().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(expense);
                }
            }
            expenseList = filteredList;
        }
        notifyDataSetChanged();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expenseTitle, expenseAmount, expenseDate, expenseCategory, expenseNote;
        ImageView btnEdit, btnDelete;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseTitle = itemView.findViewById(R.id.expense_title);
            expenseAmount = itemView.findViewById(R.id.expense_amount);
            expenseDate = itemView.findViewById(R.id.expense_date);
            expenseCategory = itemView.findViewById(R.id.expense_category);
            expenseNote = itemView.findViewById(R.id.expense_note);
            btnEdit = itemView.findViewById(R.id.btn_edit_expense);
            btnDelete = itemView.findViewById(R.id.btn_delete_expense);
        }
    }
}
