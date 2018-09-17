package com.example.pinpipo.healthy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BmiFragment extends Fragment{

    @Nullable
    @Override

    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button bmiBtn = getView().findViewById(R.id.BmiButton);


        bmiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText BmiHeight = (EditText) getView().findViewById(R.id.BmiHeight);
                String heightInt = BmiHeight.getText().toString();

                EditText BmiWeight = (EditText) getView().findViewById(R.id.BmiWeight);
                String weightInt = BmiWeight.getText().toString();

                if (heightInt.isEmpty() || weightInt.isEmpty()){
                    Toast.makeText(
                            getActivity(),"กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("BMI","FIELD NAME IS EMPTY");
                }else {
                    Log.d("BMI", "BMI is value");
                    Float heightF = Float.parseFloat(heightInt)/100;
                    Float weightF = Float.parseFloat(weightInt);
                    Float text = weightF/(heightF*heightF);

                    ((TextView) getView().findViewById(R.id.text)).setText(String.valueOf(text));


                }

            }
        });
    }
}