package com.zakaria.task_app;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }
    public void getData(){
        mainView.showLoading();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Task>> call = apiInterface.getTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(@NonNull Call<List<Task>> call,@NonNull Response<List<Task>> response) {
                mainView.hideLoading();
                if( response.isSuccessful() && response.body() != null){
                    mainView.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Task>> call,@NonNull Throwable t) {
               mainView.hideLoading();
               mainView.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }
}
