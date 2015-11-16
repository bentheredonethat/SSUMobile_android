package com.app.ssumobile.ssumobile_android.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.activity.calendarSingleEvent;
import com.app.ssumobile.ssumobile_android.models.calendarEventModel;

import java.util.ArrayList;

/**
 * Created by ben on 11/2/15.
 */
public class calendarCardAdapter extends RecyclerView.Adapter<calendarCardAdapter.ViewHolder> implements View.OnClickListener{


    private ArrayList<calendarEventModel> mDataset;


    @Override
    public void onClick(View v) {

        String StartsOn = ((TextView) v.findViewById(R.id.StartsOn)).getText().toString();
        String Title = ((TextView) v.findViewById(R.id.Title)).getText().toString();

        Activity current = (Activity) v.getContext();
        Intent myIntent = new Intent(current, calendarSingleEvent.class);
        myIntent.putExtra("StartsOn", StartsOn);
        myIntent.putExtra("Title", Title);

        calendarEventModel currentEvent = mDataset.get((Integer) v.findViewById(R.id.Title).getTag());

        myIntent.putExtra("Description", currentEvent.getDescription());
        myIntent.putExtra("Deleted", currentEvent.getDeleted());
        myIntent.putExtra("Created", currentEvent.getCreated());
        myIntent.putExtra("Location", currentEvent.getLocation());
        myIntent.putExtra("Id", currentEvent.getId());
        myIntent.putExtra("EndsOn", currentEvent.getEndsOn());

        current.startActivity(myIntent);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView StartsOn;
        public TextView Title;

        Integer pos;


        public ViewHolder(View itemView) {
            super(itemView);
            StartsOn = (TextView) itemView.findViewById(R.id.StartsOn);
            Title = (TextView) itemView.findViewById(R.id.Title);
        }
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public calendarCardAdapter(ArrayList<calendarEventModel> myDataset) {
        mDataset = myDataset;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public calendarCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        holder.Title.setText(mDataset.get(position).getTitle());
        holder.StartsOn.setText(mDataset.get(position).getStartsOn());
        //holder.pos = position;

        // save reference to event in title
        holder.Title.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
