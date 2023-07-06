package com.zakaria.task_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("saveTask.php")
    Call<Task> saveTask(
            @Field("title") String title,
            @Field("task") String task,
            @Field("color") int color
    );

    @GET("getTasks.php")
    Call<List<Task>> getTasks();

    @FormUrlEncoded
    @POST("updateTask.php")
    Call<Task> updateTask(
            @Field("id") int id,
            @Field("title") String title,
            @Field("task") String task,
            @Field("color") int color
    );

    @FormUrlEncoded
    @POST("deleteTask.php")
    Call<Task> deleteTask( @Field("id") int id );
}
