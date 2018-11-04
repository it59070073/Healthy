package com.example.pinpipo.healthy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private static final String SQL_CREATE_DATABASE = "CREATE TABLE IF NOT EXISTS sleep" +
            "(date VARCHAR(255) PRIMARY KEY, " +
            "sleepTime VARCHAR(255), " +
            "wakeTime VARCHAR(255)," +
            "hour VARCHAR(255))";


    private void createDatabase() {
        SQLiteDatabase database = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        database.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDatabase();//ต้องสร้าง database
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .commit();
        }
    }


}