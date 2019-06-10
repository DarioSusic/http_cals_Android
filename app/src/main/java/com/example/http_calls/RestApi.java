package com.example.http_calls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApi {
    @GET("posts")
    public Call<List<Post>> getAllPosts();

    @GET("posts/{id}")
    public Call<Post> getPostById(@Path("id") int id);

    @POST("posts")
    public Call<Post> addNewPost(@Body Post post);

   // @PUT("posts/{id}")
    //public Call<Post> updatePost(@Path("id"))
}
