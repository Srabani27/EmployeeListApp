package com.example.employeedetailsapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.employeedetailsapp.R;
import com.example.employeedetailsapp.model.Address;
import com.example.employeedetailsapp.model.Employee;
import com.example.employeedetailsapp.model.Geo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Fetch employee details from the API
        new FetchEmployeeDetailsTask().execute();
    }

    private void displayEmployeeDetails(Employee employee) {
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);
        TextView companyNameTextView = findViewById(R.id.companyNameTextView);
        TextView websiteTextView = findViewById(R.id.websiteTextView);
        TextView empidtxt=findViewById(R.id.empidTextView);

        nameTextView.setText(employee.getName());
        emailTextView.setText(employee.getEmail());
        phoneTextView.setText(employee.getPhone());
        empidtxt.setText(String.valueOf(employee.getEmployeeId()));

        // Displaying address components separately
        Address address = employee.getAddress();
        if (address != null) {
            String formattedAddress = String.format("%s, %s, %s", address.getStreet(), address.getCity(), address.getZipcode());
            addressTextView.setText(formattedAddress);
        }

        companyNameTextView.setText(employee.getCompanyName());
        websiteTextView.setText(employee.getWebsite());


        Linkify.addLinks(emailTextView, Linkify.EMAIL_ADDRESSES);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Gmail composing activity
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + employee.getEmail()));
                startActivity(intent);
            }
        });

        // Make phone number clickable
        Linkify.addLinks(phoneTextView, Linkify.PHONE_NUMBERS);
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open dialer for a call
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + employee.getPhone()));
                startActivity(intent);
            }
        });
    }


    private class FetchEmployeeDetailsTask extends AsyncTask<Void, Void, Employee> {

        @Override
        protected Employee doInBackground(Void... voids) {
            try {
                // Create URL for the API endpoint
                URL url = new URL("https://jsonplaceholder.typicode.com/users/1");

                // Open connection to the URL
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    // Read data from the URL
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    // Read the response and convert it to a string
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    JSONObject jsonObject = new JSONObject(response.toString());
                    Address address = new Address(
                            jsonObject.getJSONObject("address").getString("street"),
                            jsonObject.getJSONObject("address").getString("suite"),
                            jsonObject.getJSONObject("address").getString("city"),
                            jsonObject.getJSONObject("address").getString("zipcode"),
                            new Geo(
                                    jsonObject.getJSONObject("address").getJSONObject("geo").getString("lat"),
                                    jsonObject.getJSONObject("address").getJSONObject("geo").getString("lng")
                            )
                    );

                    Employee employee = new Employee(
                            jsonObject.getString("name"),
                            jsonObject.getInt("id"), // Corrected key to "id"
                            jsonObject.getString("email"),
                            jsonObject.getString("phone"),
                            address,
                            jsonObject.getJSONObject("company").getString("name"),
                            jsonObject.getString("website")
                    );

                    return employee;
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Employee employee) {
            if (employee != null) {
                // Display employee details in the UI
                displayEmployeeDetails(employee);
            }
        }

    }
}
