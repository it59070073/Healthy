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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    @Nullable
    @Override

    public View onCreateView (@Nullable LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button RegisterButton = getView().findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = (EditText) getView().findViewById(R.id.RegisterEmail);
                String emailStr = email.getText().toString();

                EditText password = (EditText) getView().findViewById(R.id.RegisterPassword);
                String passwordStr = password.getText().toString();

                EditText rePassword = (EditText) getView().findViewById(R.id.RegisterRe_password);
                String rePasswordStr = rePassword.getText().toString();


//                EditText username = (EditText) getView().findViewById(R.id.username);
//                String usernameStr = username.getText().toString();
//
//                EditText name = (EditText) getView().findViewById(R.id.name);
//                String nameStr = name.getText().toString();
//
//                EditText age = (EditText) getView().findViewById(R.id.age);
//                String ageStr = age.getText().toString();

//                EditText password = (EditText) getView().findViewById(R.id.password);
//                String passwordStr = password.getText().toString();

//                if (usernameStr.isEmpty() || nameStr.isEmpty() || ageStr.isEmpty() || passwordStr.isEmpty()){
//                    Toast.makeText(
//                            getActivity(),"กรุณาระบุข้อมูลให้ครบถ้วน",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                    Log.d("REGISTER","FIELD NAME IS EMPTY");
//                }else if (usernameStr.equals("admin")){
//                    Toast.makeText(
//                            getActivity(),"user นี้มีอยู่ในระบบแล้ว",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                    Log.d("REGISTER","USER ALREADY EXIST");
//                }else{
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BmiFragment()).commit();
//                    Log.d("REGISTER","GOTO BMI");
//
//                }

                if (emailStr.isEmpty() || passwordStr.isEmpty() || rePasswordStr.isEmpty()){
                    Toast.makeText(
                            getActivity(),"กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("REGISTER", "FIELD NAME IS EMPTY");
                } else if (passwordStr.length() < 6  || (passwordStr.equals(rePasswordStr) == false)){
                    Toast.makeText(
                            getActivity(),"กรุณากรอกรหัสใหม่",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("REGISTER", "PASSWORD ไม่ดี");
                }else {
                    mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendVerifiedEmail(authResult.getUser());
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
                            Log.d("REGISTER","GOTO BMI");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "ERROR" + e.getMessage(), Toast.LENGTH_SHORT);

                        }
                    });

                }


            }
        });


    }

    private void sendVerifiedEmail(FirebaseUser user){
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"", Toast.LENGTH_SHORT);
            }
        });
    }


}