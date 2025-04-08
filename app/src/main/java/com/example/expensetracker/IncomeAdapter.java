package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private List<IncomeModel> incomeList;
    private List<IncomeModel> fullIncomeList;

    public IncomeAdapter(List<IncomeModel> incomeList) {
        this.incomeList = incomeList;
        this.fullIncomeList = new ArrayList<>(incomeList); // copy for filtering
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        IncomeModel income = incomeList.get(position);
        holder.title.setText(income.getTitle());
        holder.amount.setText("BDT " + income.getAmount());
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.incomeTitle);
            amount = itemView.findViewById(R.id.incomeAmount);
        }
    }

    // Search filtering logic
    public void filter(String text) {
        text = text.toLowerCase();
        incomeList.clear();

        if (text.isEmpty()) {
            incomeList.addAll(fullIncomeList);
        } else {
            for (IncomeModel item : fullIncomeList) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    incomeList.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }
}
