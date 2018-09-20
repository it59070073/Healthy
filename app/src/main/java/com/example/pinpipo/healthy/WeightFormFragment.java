package com.example.pinpipo.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightFormFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore; //เราจะใช้ firestore
    private FirebaseAuth firebaseAuth; //จัดการกับ user

    public WeightFormFragment() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weightform, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onClickBtn();
        back();
    }

    public void onClickBtn() {
        Button submit = getView().findViewById(R.id.WeightFormSave);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weightFormDate = getView().findViewById(R.id.WeightFormDate);
                EditText weightFormWeight = getView().findViewById(R.id.WeightFormWeight);

                String weightFormDateStr = weightFormDate.getText().toString();
                String weightFormWeightStr = weightFormWeight.getText().toString();

                String uid = firebaseAuth.getCurrentUser().getUid();

                Weight weight = new Weight(weightFormDateStr, weightFormWeightStr);

                if (weightFormDateStr.isEmpty() || weightFormWeightStr.isEmpty()){
                    Log.d("WeightForm","fail");
                    Toast.makeText(getActivity(),"กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseFirestore
                            .collection("myfitness")
                            .document(uid)
                            .collection("weight")
                            .document(weightFormDateStr)
                            .set(weight)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("WeightForm","done");
                                    Toast.makeText(getActivity(),"done", Toast.LENGTH_SHORT).show();

                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_view, new WeightFragment())
                                            .addToBackStack(null)
                                            .commit();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("WeightForm","fail");
                            Toast.makeText(getActivity(),"fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void back(){
        Button back = getView().findViewById(R.id.WeightFormBack);

        back.setOnClickListener(new View.OnClickListener() {
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
}