package com.example.pinpipo.healthy.sleep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.pinpipo.healthy.MenuFragment;
import com.example.pinpipo.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class SleepFragment extends Fragment {

    private static final String SQL_LISTALL_SLEEP = "SELECT * FROM sleep";
    private SQLiteDatabase database;
    private Cursor query;

    private List<Sleep> sleepList;
    private SleepAdapter sleepAdapter;
    private ListView listView;

    public SleepFragment() {
        sleepList = new ArrayList<>();
    }

    public View onCreateView (@Nullable LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        addWeight();
        show();
        back();
    }

    public void addWeight(){
        Button add = getView().findViewById(R.id.sleepAdd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void back(){
        Button add = getView().findViewById(R.id.sleepฺBack);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void show(){
        String date;
        String sleeptime;
        String wakeuptime;
        String hour;

        String text;

        database = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        sleepAdapter = new SleepAdapter(
                getActivity(),
                R.layout.fragment_sleep_item,
                sleepList
        );

        listView = getView().findViewById(R.id.sleep_list);

        listView.setAdapter(sleepAdapter);

        sleepAdapter.clear();

        query = database.rawQuery(SQL_LISTALL_SLEEP, null);

        while(query.moveToNext()) {
            date = query.getString(0);
            sleeptime = query.getString(1);
            wakeuptime = query.getString(2);
            hour = query.getString(3);

            text = sleeptime + " - " + wakeuptime;

            sleepList.add(new Sleep(date, text, hour));
        }

        query.close();
        database.close();
        sleepAdapter.notifyDataSetChanged();

    }
}
