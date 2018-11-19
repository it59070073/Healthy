package com.example.pinpipo.healthy.service;

import com.example.pinpipo.healthy.post.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentService {
    @GET("/posts/{id}/comments")
    Call<List<Comment>> getData(@Path("id") String id);
}
