package com.kmorales.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Adapter is responsible for displaying data from the model into recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.viewHolder>{

    public interface OnLongClicklistener{
        void onItemLongClick(int position);
    }
    List<String> items;
    OnLongClicklistener longClicklistener;

    public ItemsAdapter(List<String> items, OnLongClicklistener longClicklistener) {
        this.items=items;
        this.longClicklistener= longClicklistener;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //Use layout inflator to inflate a view
        boolean attachToRoot;
        View todoView=LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent, false);
    //wrap it inside view holder and return it
        return new viewHolder(todoView);
    }

    //responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //grab the item at the position
        String item= items.get(position);
        //Bind the item into the specified view holder
        holder.bind(item);

    }
    //tells the rv how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list

    class viewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=itemView.findViewById(android.R.id.text1);
        }

        //Update the view inside of The view holder  witH this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                //notify the listener which position was long pressed
                public boolean onLongClick(View view) {

                longClicklistener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
