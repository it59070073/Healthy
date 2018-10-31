package com.example.pinpipo.healthy.weight;

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

public class WeightAdapter extends ArrayAdapter<Weight> {

    Context context;
    ArrayList<Weight> weights = new ArrayList<Weight>();

    public WeightAdapter(@Nullable Context context,
                         int resource,
                         @Nullable List<Weight> objects){
        super(context, resource, objects);
        this.context = context;
        this.weights = (ArrayList<Weight>) objects; //ให้วน list ใน object นี้ สร้าง adaptor ไว้แล้วแต่ยังไม่ได้ลงที่ text


    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {


        View weightItem = LayoutInflater.from(context)
                        .inflate(R.layout.fragment_weight_item,
                        parent,
                        false);

        TextView date = weightItem.findViewById(R.id.date);
        TextView weight = weightItem.findViewById(R.id.weight);
        TextView status = weightItem.findViewById(R.id.status);

        Weight row = weights.get(position);  //set ของข้อความ

        date.setText(row.getDate());
        weight.setText(row.getWeight());

        if (position == weights.size() - 1){
            status.setText("");
        }else {
            Double first = Double.parseDouble(weights.get(position).getWeight());
            Double second = Double.parseDouble(weights.get(position + 1).getWeight());

            if (first > second){
                status.setText("UP");
            }else if (second > first){
                status.setText("DOWN");
            }else {
                status.setText("");
            }
        }

        return weightItem; //ไม่มี void เลยต้อง return

    } //เอาข้อความอ่ะ เข้าไปใส่ใน fragment_weight_item นะจ้ะ
}