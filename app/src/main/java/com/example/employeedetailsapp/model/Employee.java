package com.example.employeedetailsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Employee implements Parcelable {
    private String name;
    private int employeeId;
    private String email;
    private String phone;
    private Address address;
    private String companyName;
    private String website;

    // Constructor
    public Employee(String name, int employeeId, String email, String phone, Address address, String companyName, String website) {
        this.name = name;
        this.employeeId = employeeId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.companyName = companyName;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
// Getter and setter methods for other fields...

    // Getter and setter for the Address field
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Parcelable implementation
    protected Employee(Parcel in) {
        name = in.readString();
        employeeId = in.readInt();
        email = in.readString();
        phone = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        companyName = in.readString();
        website = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(employeeId);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeParcelable(address, flags);
        dest.writeString(companyName);
        dest.writeString(website);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Other potential methods...

    // For example, if you want to override equals and hashCode methods for proper comparison.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return employeeId == employee.employeeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    // Other methods as needed...
}

