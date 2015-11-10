package com.app.ssumobile.ssumobile_android.adapters;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.activity.CalendarSingleDate;
import com.app.ssumobile.ssumobile_android.activity.calendarSingleEvent;
import com.app.ssumobile.ssumobile_android.models.calendarEvent;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by ben on 11/2/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements View.OnClickListener{


    private ArrayList<calendarEvent> mDataset;


    @Override
    public void onClick(View v) {
        String summary = ((TextView) v.findViewById(R.id.summary)).getText().toString();
        String location = ((TextView) v.findViewById(R.id.location)).getText().toString();
        String time = ((TextView) v.findViewById(R.id.time)).getText().toString();

        Activity current = (Activity) v.getContext();
        Intent myIntent = new Intent(current, calendarSingleEvent.class);
        myIntent.putExtra("summary", summary); //Optional parameters
        myIntent.putExtra("location", location);
        myIntent.putExtra("time", time);
        myIntent.putExtra("eventid",((String) v.findViewById(R.id.summary).getTag()));
        current.startActivity(myIntent);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView summary;
        public TextView location;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            summary = (TextView) itemView.findViewById(R.id.summary);
            location = (TextView) itemView.findViewById(R.id.location);
            time = (TextView) itemView.findViewById(R.id.time);
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
        final View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        v.setOnClickListener(this);
        // set the view's size, margins, paddings and layout parameters here
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.summary.setText(mDataset.get(position).getSUMMARY());
        holder.location.setText(mDataset.get(position).getLOCATION());
        holder.time.setText(mDataset.get(position).getStartTime());
        holder.summary.setTag(mDataset.get(position).getEventID());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
