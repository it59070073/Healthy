package com.example.pinpipo.healthy.service;


import com.example.pinpipo.healthy.post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {
    @GET("posts")
    Call<List<Post>> getData();
}