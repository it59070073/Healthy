package com.example.pinpipo.healthy.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pinpipo.healthy.MenuFragment;
import com.example.pinpipo.healthy.R;
import com.example.pinpipo.healthy.service.PostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class PostFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String URL = "https://jsonplaceholder.typicode.com";
    private PostAdapter postAdapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onClickBack();
        showList();
    }

    private void onClickBack() {
        Button btn = getView().findViewById(R.id.postBack);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void showList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostService service = retrofit.create(PostService.class);
        service.getData().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, retrofit2.Response<List<Post>> response) {
                postAdapter = new PostAdapter(getActivity(),
                        R.layout.fragment_post_item,
                        response.body());

                listView = getView().findViewById(R.id.post_list);
                listView.setAdapter(postAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Post p = (Post) parent.getItemAtPosition(position);

                        Bundle bundle = new Bundle();
                        bundle.putString("id", p.getId());
                        Log.d(TAG, p.getId());

                        CommentFragment commentFragment = new CommentFragment();
                        commentFragment.setArguments(bundle);

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, commentFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(getActivity(), "Some thing error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
