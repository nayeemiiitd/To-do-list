package com.iiitd.to_do_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Nayeem on 11/4/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private String[] mList;
    DatabaseHelper myDb;

    public RecyclerViewAdapter(Context contexts, String[] list) {
        this.mContext = contexts;
        this.mList = list;
        myDb = new DatabaseHelper(contexts);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(mList[position]);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick)
                {
                    if(mList[position]!=null && !mList[position].equals("null")  && !mList[position].isEmpty())
                    {
                        MainActivity.delete(mContext,mList[position]);
                    }
                }
                else
                {
                    if(mList[position]!=null && !mList[position].equals("null")  && !mList[position].isEmpty()){

                        MainActivity.display(mContext,mList[position],position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView titleTextView;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.textView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}
