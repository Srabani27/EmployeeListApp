package com.example.employeedetailsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.employeedetailsapp.ApiService;
import com.example.employeedetailsapp.R;
import com.example.employeedetailsapp.adapter.EmployeeAdapter;
import com.example.employeedetailsapp.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        // Check for internet connectivity
        if (isNetworkAvailable()) {
            // If internet is available, proceed with API call
            makeApiCall();
        } else {
            // If no internet, show a notification to the user
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeApiCall() {
        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Make API call to get the list of employees
        Call<List<Employee>> call = apiService.getEmployeeList();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> employeeList = response.body();
                    displayEmployeeList(employeeList);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch employee list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("MainActivity", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error fetching employee list", Toast.LENGTH_SHORT).show();
            }
        });
    }
        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            }
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
            return false;
        }
    private void displayEmployeeList(List<Employee> employeeList) {
        // Initialize and set up the RecyclerView adapter
        employeeAdapter = new EmployeeAdapter(employeeList, this);
        recyclerView.setAdapter(employeeAdapter);

        // Optional: If you want to customize the layout of the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
    }