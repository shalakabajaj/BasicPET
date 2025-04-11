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
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<ExpenseModel> expenseList;
    private OnExpenseDeleteListener deleteListener;

    public interface OnExpenseDeleteListener {
        void onExpenseDeleted();
    }

    public ExpenseAdapter(List<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
    }

    public void setOnExpenseDeleteListener(OnExpenseDeleteListener listener) {
        this.deleteListener = listener;
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
        holder.expenseCategory.setText("Category: " + expense.getCategory());
        holder.expenseNote.setText(expense.getNote());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditExpenseActivity.class);
            intent.putExtra("expense", expense); // 🔁 pass whole object
            v.getContext().startActivity(intent);
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
