package com.example.employees.activity.main;

import androidx.annotation.NonNull;

import com.example.employees.api.ApiClient;
import com.example.employees.api.ApiInterface;
import com.example.employees.model.employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    void getData(){
        view.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<employee>> call = apiInterface.getEmployees();
        call.enqueue(new Callback<List<employee>>() {
            @Override
            public void onResponse(@NonNull Call<List<employee>> call, @NonNull Response<List<employee>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body()!=null){
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<employee>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
