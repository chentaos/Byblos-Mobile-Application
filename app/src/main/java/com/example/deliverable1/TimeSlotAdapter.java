package com.example.deliverable1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class TimeSlotAdapter extends ArrayAdapter<String> {

    private Activity context;
    private List<String> timeIntervals;

    public TimeSlotAdapter(Activity context, @NonNull List<String> objects) {
        super(context, R.layout.layout_time_interval_item, objects);
        this.context = context;
        this.timeIntervals = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_time_interval_item, null, true);

        TextView startTime = (TextView) listViewItem.findViewById(R.id.startTimeID);
        TextView endTime = (TextView) listViewItem.findViewById(R.id.endTimeID);

        String t = timeIntervals.get(position);
        startTime.setText(t.substring(0,5));
        endTime.setText(t.substring(5,10));

        return listViewItem;
    }
}
