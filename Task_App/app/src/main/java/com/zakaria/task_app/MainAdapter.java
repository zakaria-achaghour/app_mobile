package com.zakaria.task_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Task> tasks;
    private ItemClickListener itemClickListener;

    public MainAdapter(Context context, List<Task> tasks, ItemClickListener itemClickListener) {
        this.context = context;
        this.tasks = tasks;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task,parent,false);
        return new RecyclerViewAdapter(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
         Task task = tasks.get(position);
         holder.tv_title.setText(task.getTitle());
         holder.tv_task.setText(task.getTask());
         holder.tv_date.setText(task.getDate());
         holder.card_item.setCardBackgroundColor(task.getColor());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_title, tv_task, tv_date;
        CardView card_item;
        ItemClickListener itemClickListener;

         RecyclerViewAdapter(@NonNull View itemView , ItemClickListener itemClickListener) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.title);
            tv_task = itemView.findViewById(R.id.task);
            tv_date = itemView.findViewById(R.id.date);
            card_item = itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
             itemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }
}
