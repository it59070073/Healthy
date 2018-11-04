package com.example.pinpipo.healthy.sleep;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pinpipo.healthy.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SleepFormFragment extends Fragment{

    private SQLiteDatabase database;
    private CalculateHour calculateHour;
    private ContentValues preInsert;

    private Cursor query;
    private int state = 1;

    public SleepFormFragment() {
        preInsert = new ContentValues();
        calculateHour = new CalculateHour();

    }

    public View onCreateView (@Nullable LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleepform, container, false);
    }

    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        save();
        back();
        Bundle bundle = getArguments();

        if (bundle != null) {
            setValue(bundle.getString("date"));
        }
    }

    public void back(){
        Button back = getView().findViewById(R.id.sleepFormBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void save(){
        database = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null); //ต่อ database

        Button save = getView().findViewById(R.id.sleepFormSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText sleepFormDate = getView().findViewById(R.id.sleepFormDate);
                EditText sleepFormSleepTime = getView().findViewById(R.id.sleepFormSleepTime);
                EditText sleepFormWakeTime = getView().findViewById(R.id.sleepFormWakeTime);

                String date = sleepFormDate.getText().toString();
                String sleepTime = sleepFormSleepTime.getText().toString();
                String wakeTime = sleepFormWakeTime.getText().toString();
                String hour = calculateHour.calculate(sleepTime, wakeTime);

                preInsert.clear();

                if (date.isEmpty() || sleepTime.isEmpty() || wakeTime.isEmpty()){
                    Log.d("sleepForm","fail");
                    Toast.makeText(getActivity(),"กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }else{
                    if (state == 0) {
                        updateValue();
                        Toast.makeText(getActivity(), "Update complete", Toast.LENGTH_SHORT).show();
                        Log.d("SLEEP", "Update value complete");
                    }else{
                        preInsert.put("date", date);
                        preInsert.put("sleepTime", sleepTime);
                        preInsert.put("wakeTime", wakeTime);
                        preInsert.put("hour", hour);

                        database.insert("sleep", null, preInsert);
                        database.close();

                        sleepFormDate.getText().clear();
                        sleepFormSleepTime.getText().clear();
                        sleepFormWakeTime.getText().clear();

                        Toast.makeText(getActivity(), "Insert complete", Toast.LENGTH_SHORT).show();
                        Log.d("SLEEP", "Insert data to DB");

                    }

                }
            }
        });
    }

    //เรียกค่า
    private void setValue(String date) {
        database = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);

        EditText sleepFormDate = getView().findViewById(R.id.sleepFormDate);
        EditText sleepFormSleepTime = getView().findViewById(R.id.sleepFormSleepTime);
        EditText sleepFormWakeTime = getView().findViewById(R.id.sleepFormWakeTime);

        query = database.rawQuery("SELECT * FROM sleep WHERE date=" + "'" + date + "'", null);

        query.moveToNext();

        sleepFormDate.setText(query.getString(0));
        sleepFormSleepTime.setText(query.getString(1));
        sleepFormWakeTime.setText(query.getString(2));

        state = 0;

        query.close();
        database.close();
    }

    //update ค่าใหม่
    private void updateValue() {
        database = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);

        String sleepFormDate = ((EditText) getView().findViewById(R.id.sleepFormDate)).getText().toString();
        String sleepFormSleepTime = ((EditText) getView().findViewById(R.id.sleepFormSleepTime)).getText().toString();
        String sleepFormWakeTime = ((EditText) getView().findViewById(R.id.sleepFormWakeTime)).getText().toString();
        String hour = calculateHour.calculate(sleepFormSleepTime, sleepFormWakeTime);


        database.execSQL(String.format("UPDATE sleep " +
                "SET sleepTime='%s', wakeTime='%s', hour='%s'" +
                "WHERE date='%s'", sleepFormSleepTime, sleepFormWakeTime, hour, sleepFormDate));

        database.close();
    }



}