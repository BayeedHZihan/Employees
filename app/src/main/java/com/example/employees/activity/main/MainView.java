package com.example.employees.activity.main;

import com.example.employees.model.employee;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<employee> employees);
    void onErrorLoading(String message);
}
