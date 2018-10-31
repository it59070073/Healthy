package com.example.pinpipo.healthy.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pinpipo.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<Sleep> {

    private List<Sleep> sleepList;
    private Context context;

    public SleepAdapter(@NonNull Context context,
                        int resource,
                        @NonNull List<Sleep> objects) {
        super(context, resource, objects);
        this.context = context;
        this.sleepList = (ArrayList<Sleep>) objects;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View sleepItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_sleep_item,
                parent,
                false);

        TextView date = sleepItem.findViewById(R.id.date);
        TextView hour = sleepItem.findViewById(R.id.time);
        TextView resultTime = sleepItem.findViewById(R.id.hour);

        Sleep sleep = sleepList.get(position);

        date.setText(sleep.getDate());
        hour.setText(sleep.getTime());
        resultTime.setText(sleep.getResultTime());

        return sleepItem;
    }
}
