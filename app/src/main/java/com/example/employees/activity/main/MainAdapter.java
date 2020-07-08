package com.example.employees.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.employees.R;
import com.example.employees.model.employee;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<employee> employees;
    private ItemClickListener itemClickListener;

    public MainAdapter(Context context, List<employee> employees, ItemClickListener itemClickListener) {
        this.context = context;
        this.employees = employees;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee,
                parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        employee employee = employees.get(position);
        holder.tv_name.setText(employee.getName());
        holder.tv_age.setText(employee.getAge());
        holder.tv_gender.setText(employee.getGender());
        holder.card_item.setCardBackgroundColor(employee.getColor());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_name, tv_age, tv_gender;
        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name);
            tv_age = itemView.findViewById(R.id.age);
            tv_gender = itemView.findViewById(R.id.gender);
            card_item = itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
