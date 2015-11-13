package com.app.ssumobile.ssumobile_android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.newsStoryModel;

import java.util.ArrayList;

/**
 * Created by ben on 11/12/15.
 */
public class newsCardAdapter  extends RecyclerView.Adapter<newsCardAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList<newsStoryModel> mDataset;

    @Override
    public newsCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.newscardview, parent, false);
        v.setOnClickListener(this);
        // set the view's size, margins, paddings and layout parameters here
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String x = mDataset.get(position).title;
        holder.title.setText(x);
        holder.publishing_date.setText(mDataset.get(position).publish_date);
        holder.description.setText(mDataset.get(position).description);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView publishing_date;
        public TextView description;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            publishing_date = (TextView) itemView.findViewById(R.id.publish_date);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onClick(View v) {
        String title = ((TextView) v.findViewById(R.id.title)).getText().toString();
        String publish_date = ((TextView) v.findViewById(R.id.publish_date)).getText().toString();
        String description = ((TextView) v.findViewById(R.id.description)).getText().toString();

        //TO DO: IMPLEMENT NEW ACTIVITY ON CLICK

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public newsCardAdapter(ArrayList<newsStoryModel> myDataset) {
        mDataset = myDataset;
    }
}
