package com.example.employees.activity.editor;

import androidx.annotation.NonNull;

import com.example.employees.api.ApiClient;
import com.example.employees.api.ApiInterface;
import com.example.employees.model.employee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditorPresenter {
    private EditorView view;

    public EditorPresenter(EditorView view){
        this.view = view;
    }

    void saveEmployee(final String name, final int age, final int color, final String gender){
        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<employee> call = apiInterface.saveEmployee(name, age, color, gender);

        call.enqueue(new Callback<employee>() {
            @Override
            public void onResponse(Call<employee> call, Response<employee> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body()!=null){
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestSuccess(response.body().getMessage());
                    }
                    else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<employee> call, Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

    void updateEmployee(int id, String name, int age, int color) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<employee> call = apiInterface.updateEmployee(id, name, age, color);
        call.enqueue(new Callback<employee>(){

            @Override
            public void onResponse(@NonNull Call<employee> call, @NonNull Response<employee> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body()!=null){
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        view.onRequestSuccess(response.body().getMessage());
                    }
                    else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<employee> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

    void deleteEmployee(int id) {
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<employee> call = apiInterface.deleteEmployee(id);
        call.enqueue(new Callback<employee>() {

            @Override
            public void onResponse(@NonNull Call<employee> call, @NonNull Response<employee> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body()!=null){
                    Boolean success = response.body().getSuccess();
                    if (success){
                        view.onRequestSuccess(response.body().getMessage());
                    }
                    else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<employee> call, @NonNull Throwable t) {

            }
        });
    }

}
