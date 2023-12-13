package com.example.employeedetailsapp;

import com.example.employeedetailsapp.model.Employee;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/users")
    Call<List<Employee>> getEmployeeList();

    @GET("/users/{id}")
    Call<Employee> getEmployeeDetails(@Path("id") int userId);
}
