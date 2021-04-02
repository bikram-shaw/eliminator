package com.example.eliminator.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliminator.R;
import com.example.eliminator.modal.Transactions;
import com.example.eliminator.modal.UpcomingMatches;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    Context context;
    ArrayList<Transactions> transactions=new ArrayList<>();

    public TransactionsAdapter(Context context, ArrayList<Transactions> transactions)
    {
        this.context=context;
        this.transactions=transactions;
    }
    @NonNull
    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.transaction_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.ViewHolder holder, int position) {
        holder.txn_description.setText(transactions.get(position).getDescription());
        holder.txn_status.setText(transactions.get(position).getStatus());
        holder.txn_date.setText(transactions.get(position).getDate());
        if(transactions.get(position).getStatus().equals("credit")){
            holder.txn_status.setTextColor(Color.rgb(0, 133, 55));
            holder.txn_amount.setText("+ "+transactions.get(position).getAmount());
        }
        else {
            holder.txn_status.setTextColor(Color.rgb(181, 33, 0));
            holder.txn_amount.setText("- "+transactions.get(position).getAmount());
        }


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txn_description,txn_date,txn_status,txn_amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txn_description=itemView.findViewById(R.id.txn_description);
            txn_amount=itemView.findViewById(R.id.txn_amount);
            txn_date=itemView.findViewById(R.id.txn_date);
            txn_status=itemView.findViewById(R.id.txn_status);


        }
    }
}
