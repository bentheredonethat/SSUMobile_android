package com.app.ssumobile.ssumobile_android.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.app.ssumobile.ssumobile_android.models.CalendarEventModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 10/22/15.
 */
public class CalendarEventListAdapter extends ArrayAdapter{

    public CalendarEventListAdapter(Context context, ArrayList<CalendarEventModel> events) {
        super(context, 0, events);
    }
    public CalendarEventListAdapter(Context context, int resource, List<CalendarEventModel> items) {
        super(context, resource, items);
    }
}
