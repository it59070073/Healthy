package com.example.pinpipo.healthy.sleep;

import android.content.ContentValues;
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
        });
    }



}
