package com.app.ssumobile.ssumobile_android.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.activity.newsSingleStoryActivity;
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
        holder.Title.setText(mDataset.get(position).Title);
        holder.Published.setText(mDataset.get(position).Published);
        holder.Category.setText(mDataset.get(position).Category);


        // save which event so it can be used later
        holder.Title.setTag(mDataset.get(position));
        //holder.story = mDataset.get(position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView Title;
        public TextView Published;
        public TextView Category;
        newsStoryModel story;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.newstitle);
            Published = (TextView) itemView.findViewById(R.id.newspublished);
            Category = (TextView) itemView.findViewById(R.id.newscategory);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onClick(View v) {

        //TO DO: IMPLEMENT NEW ACTIVITY ON CLICK
        Activity current = (Activity) v.getContext();
        Intent myIntent = new Intent(current, newsSingleStoryActivity.class);

        newsStoryModel story = (newsStoryModel) v.findViewById(R.id.newstitle).getTag();
        myIntent.putExtra("Category", story.Category);
        myIntent.putExtra("Content", story.Content);
        myIntent.putExtra("ID", story.ID);
        myIntent.putExtra("ImageURL", story.ImageURL);
        myIntent.putExtra("Link", story.Link);
        myIntent.putExtra("Updated", story.Updated);
        myIntent.putExtra("Title", story.Title);
        myIntent.putExtra("Updated", story.Updated);
        myIntent.putExtra("Summary", story.Summary);


        current.startActivity(myIntent);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public newsCardAdapter(ArrayList<newsStoryModel> myDataset) {
        mDataset = myDataset;
    }
}
