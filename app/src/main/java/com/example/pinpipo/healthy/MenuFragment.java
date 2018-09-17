package com.example.pinpipo.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    ArrayList<String> menu = new ArrayList<>();

    public MenuFragment(){ //เวลาย้อนกลับมันจะไม่เบิล
        menu.add("BMI");
        menu.add("Weight");
        menu.add("Setup");
        menu.add("Logout");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );

        ListView menulist = getView().findViewById(R.id.MenuList);
        menulist.setAdapter(menuAdapter);
        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MENU", "Click on menu = " + menu.get(i));

                if (menu.get(i).equals("BMI")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BmiFragment())
                            .addToBackStack(null)
                            .commit();
                }else if (menu.get(i).equals("Weight")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFragment())
                            .addToBackStack(null)
                            .commit();

                }else if (menu.get(i).equals("Setup")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFormFragment())
                            .addToBackStack(null)
                            .commit();

                }else if (menu.get(i).equals("Logout")){
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut(); //ป้องกันไม่ให้เกิด loop

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .addToBackStack(null)
                            .commit();

                }

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
}