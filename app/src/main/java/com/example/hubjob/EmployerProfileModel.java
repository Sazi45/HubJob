package com.example.hubjob;

public class EmployerProfileModel {
    private String companyName;
    private String industry;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String suburb;
    private String postalCode;

    public EmployerProfileModel() {}

    public EmployerProfileModel(String companyName, String industry, String contactPerson,
                                String phoneNumber, String email, String address,
                                String city, String suburb, String postalCode) {
        this.companyName = companyName;
        this.industry = industry;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.city = city;
        this.suburb = suburb;
        this.postalCode = postalCode;
    }
}
