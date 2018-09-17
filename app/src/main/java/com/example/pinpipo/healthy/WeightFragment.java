package com.example.pinpipo.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weight = new ArrayList<Weight>();

    public WeightFragment(){ //เวลาย้อนกลับมันจะไม่เบิล

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button addWeight = getView().findViewById(R.id.WeightFormSave);
        ListView weightList = getView().findViewById(R.id.weight_list);

        WeightAdapter menuAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_item,
                weight
        );
        weight.add(new Weight("1 Jan 2018", 60, "Up"));
        weightList.setAdapter(menuAdapter);



        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WEIGHT", "ADD WEIGHT");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


}