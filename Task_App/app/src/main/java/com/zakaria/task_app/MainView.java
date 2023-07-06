package com.zakaria.task_app;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Task> tasks);
    void onErrorLoading(String message);
}
