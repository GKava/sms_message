package com.example.savemessage;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomLisrtAdapter extends RecyclerView.Adapter<CustomLisrtAdapter.ViewHolder> {

    private ArrayList<ModelItems> items = new ArrayList<>();
    private static final String TAG = "MY LOG ADAPTER ";
    private Context context;

    public void setClickListener(OnClickListener onClickListener) {
    }

    public interface OnClickListener {
        void onClick(ModelItems items);
    }

    private View.OnClickListener clickListener;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CustomLisrtAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        ModelItems item = items.get(position);
        holder.title.setText(item.getAuthor());
        holder.itemView.setOnClickListener(null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onClick(view);
                }
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        holder.itemView.setOnClickListener(null);
        super.onViewRecycled(holder);
    }

//    public void setClickListener(OnClickListener clickListener) {
//        this.clickListener = clickListener;
//    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view;

        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_left, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        }


        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).phoneUser ? 0 : 1;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {
        //   private TextView title; объявлен в начале
        private TextView title;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void bind(ModelItems modelItems) {
            Log.d(TAG, "bind");
            title.setText(modelItems.getAuthor() );
        }
    }




    public  void addMessage(ModelItems item) {
        Log.d(TAG, "addMessage");
        items.add(item);
        notifyItemChanged(items.size() - 1);
    }

    public  void deleteMessageAll() {
        Log.d(TAG, "deleteMessage");
        int pos = getItemCount();
        if (pos > 0) {
            for (int i = 0; i < pos; i++) {
                this.items.remove(0);
            }
            this.notifyItemRangeRemoved(0, pos);
        }
    }

    public void addAll(List<ModelItems> allMessage) {
      int pos = getItemCount();
       this.items.addAll(allMessage);
       notifyItemRangeInserted(pos, this.items.size());
    }
}
