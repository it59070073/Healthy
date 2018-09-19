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
    }

    public void onClickBtn() {
        Button submit = getView().findViewById(R.id.WeightFormSave);
        final EditText weightFormDate = getView().findViewById(R.id.WeightFormDate);
        EditText weightFormWeight = getView().findViewById(R.id.WeightFormWeight);

        final String weightFormDateStr = weightFormDate.getText().toString();
        final String weightFormWeightStr = weightFormWeight.getText().toString();

        final String uid = firebaseAuth.getCurrentUser().getUid();

        final Weight weight = new Weight(weightFormDateStr, weightFormWeightStr);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weightFormDateStr.isEmpty() || weightFormWeightStr.isEmpty()) {
                    Log.d("WeightForm","กรุณากรอกให้ครบ");
                    Toast.makeText(getActivity(), "กรุณากรอกให้ครบ", Toast.LENGTH_SHORT).show();
                } else {
                      firebaseFirestore
                              .collection("myfitness")
                              .document(uid)
                              .collection("weight")
                              .document(weightFormDateStr)
                              .set(weight)
                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      Log.d("WeightForm","ข้อมูลเข้าเรียบร้อย");
                                      Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Log.d("WeightForm", "fail");
                                      Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                                  }
                      });
                }
            }
        });
    }
}