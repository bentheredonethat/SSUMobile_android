package com.app.ssumobile.ssumobile_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.calendarEvent;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 11/2/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {


    private ArrayList<calendarEvent> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView summary;
        public TextView location;
        public ViewHolder(View itemView, Context c) {
            super(itemView);
            summary = (TextView) itemView.findViewById(R.id.summary);
            location = (TextView) itemView.findViewById(R.id.location);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(ArrayList<calendarEvent> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters here

        ViewHolder vh = new ViewHolder(v, parent.getContext());
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.summary.setText(mDataset.get(position).getSUMMARY());
        holder.location.setText(mDataset.get(position).getLOCATION());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
