package com.example.employees.api;

import com.example.employees.model.employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("save.php")
    Call<employee> saveEmployee(
            @Field("name") String name,
            @Field("age") int age,
            @Field("color") int color,
            @Field("gender") String gender
    );

    @GET("employees.php")
    Call<List<employee>> getEmployees();

    @FormUrlEncoded
    @POST("update.php")
    Call<employee> updateEmployee(
            @Field("id") int id,
            @Field("name") String name,
            @Field("age") int age,
            @Field("color") int color
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<employee> deleteEmployee( @Field("id") int id );

}
