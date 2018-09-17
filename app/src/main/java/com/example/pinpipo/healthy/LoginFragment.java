package com.example.pinpipo.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance(); //เราเรียกใช้ firebase ที่เรียกในการ login หรือ register
        final FirebaseUser mUser = mAuth.getCurrentUser(); //คือเมื่อ login ไว้แล้วแล้วจำหน้าไว้

        if (mUser != null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
        }


        Button LoginButton = getView().findViewById(R.id.LoginButton);
        TextView RegisterTV = getView().findViewById(R.id.LoginRegister);

        RegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("USER", "GOTO REGISTER");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userId = (EditText) getView().findViewById(R.id.LoginId);
                EditText _password = (EditText) getView().findViewById(R.id.LoginPassword);

                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();

                mAuth.signInWithEmailAndPassword(_userIdStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (mUser.isEmailVerified() == true){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
                            Log.d("USER","GOTO BMI");
                        }else{
                            Toast.makeText(
                                    getActivity(),"คุณยังไม่ได้ทำการยืนยัน email",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                getActivity(), e.getMessage(),//แม่ง show ข้อความให้เลย
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                });

//                if (_userIdStr.isEmpty() || _passwordStr.isEmpty()){
//                    Toast.makeText(
//                            getActivity(),"กรุณาระบุ user or password",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                    Log.d("USER","USER OR PASSWORD IS EMPTY");
//                } else if (_userIdStr.equals("admin") && _passwordStr.equals("admin")){
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
//                    Log.d("USER","GOTO BMI");
//                } else {
//                    Log.d("USER", "INVALID USER NAME OR PASSWORD");
//                }
            }
        });

    }

}