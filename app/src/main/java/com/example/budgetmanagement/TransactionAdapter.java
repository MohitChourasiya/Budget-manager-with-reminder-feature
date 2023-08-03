package com.example.budgetmanagement;

import static com.example.budgetmanagement.R.color.card_expense;
import static com.example.budgetmanagement.R.color.red;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.collection.LLRBNode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TransactionAdapter  extends RecyclerView.Adapter<TransactionAdapter.myViewHolder>{

    Context context;
    ArrayList<TransactionModel> transactionModelArrayList;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> transactionModelArrayList) {
        this.context = context;
        this.transactionModelArrayList = transactionModelArrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

        return new myViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TransactionModel model=transactionModelArrayList.get(position);
         String priority=model.getType();
         if (priority.equals(("Expense"))){
            /* holder.bg.setBackgroundResource(R.color.card_expense);*/
             holder.amount.setTextColor(Color.parseColor("#FFEF1616"));
             holder.add.setTextColor(Color.parseColor("#FFEF1616"));
             holder.add.setText("-Debit");

         }else{
            /* holder.bg.setBackgroundResource(R.color.card_income);*/

             holder.amount.setTextColor(Color.parseColor("#FF33BC38"));
             holder.add.setText("+Credit");
             holder.add.setTextColor(Color.parseColor("#FF33BC38"));

         }

        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());
        holder.note.setText(model.getNote());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt=new Intent(context,UpdateActivity.class);
                intt.putExtra("id",transactionModelArrayList.get(position).getId());
                intt.putExtra("amount",transactionModelArrayList.get(position).getAmount());
                intt.putExtra("note",transactionModelArrayList.get(position).getNote());
                intt.putExtra("type",transactionModelArrayList.get(position).getType());
                context.startActivities(new Intent[]{intt});
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionModelArrayList.size() ;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView note,amount,date,add;
        CardView bg;
        View priority;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.noteRecycl);
           amount = itemView.findViewById(R.id.amountRecycl);
            date = itemView.findViewById(R.id.dateRecycl);
            priority = itemView.findViewById(R.id.priority);
            bg = itemView.findViewById(R.id.cardViewRecyclerItem);
            add = itemView.findViewById(R.id.added);

        }
    }
}
